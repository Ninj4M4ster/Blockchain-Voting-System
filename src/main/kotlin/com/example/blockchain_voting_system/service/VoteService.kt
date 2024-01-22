package com.example.blockchain_voting_system.service

import BlockChainConnection
import com.example.blockchain_voting_system.results.VotingResult
import com.example.blockchain_voting_system.data.*
import com.example.blockchain_voting_system.domain.authentication.AuthenticationUseCase
import com.example.blockchain_voting_system.domain.register.RegistrationUseCase
import com.example.blockchain_voting_system.entities.Candidate
import com.example.blockchain_voting_system.results.toResponseEntity
import com.example.blockchain_voting_system.utils.blockchainStringToBoolean
import com.example.blockchain_voting_system.utils.checkIfIdExists
import com.example.blockchain_voting_system.utils.getCandidateNameForSpecifiedId
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
class VoteService() {

    private val authenticationUseCase = AuthenticationUseCase()
    private val registrationUseCase = RegistrationUseCase()
    private val blockChainConnection = BlockChainConnection()

    private val votesToAdd = mutableListOf<VoteData>()
    private val MAX_VOTES_LIST_SIZE = 3

    fun addCandidate(candidateData: CandidateData): ResponseEntity<Unit> {
        authenticationUseCase.dbService.addCandidate(candidateData.candidateName, candidateData.candidateDescription)
        return ResponseEntity.ok().build()
    }

    fun getCandidates(): ResponseEntity<List<Candidate>> {
        return ResponseEntity.ok(authenticationUseCase.dbService.getCandidates())
    }

    fun addRightsToVote(voteRightsData: VoteRightsData): ResponseEntity<Unit> {
        println("Elo dodajemy")
        // Add rights to vote in the database
        return if (authenticationUseCase.dbService.addVotesRight(voteRightsData.email)) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.status(409).build()
        }
    }

    fun authenticateUser(userData: UserData): String {
        return authenticationUseCase.login(userData.email, userData.password) ?: ""
    }

    // Keycloak will throw 401 if user not authenticated
    fun isUserAuthenticated(): ResponseEntity<Unit> = ResponseEntity.ok().build()

    fun registerUser(registerUserData: RegisterUserData): ResponseEntity<String> {

        // Register user in Keycloak
        val response = authenticationUseCase.createUser(UserRequest(registerUserData.email, registerUserData.password))
        blockChainConnection.addVoter(registerUserData.publicKey)
        // Register user in database

        return registrationUseCase.registerUser(registerUserData).toResponseEntity()
    }

    fun sendVote(voteData: VoteData): ResponseEntity<String> {

        // Check that the user has entered an existing id
        if (!checkIfIdExists(authenticationUseCase.dbService.getCandidates(), voteData.candidateId)) {
            return VotingResult.CANDIDATE_ID_DOES_NOT_EXIST.toResponseEntity()
        }

        val hasVoted = (blockChainConnection.hasVoted(voteData.publicKey)).blockchainStringToBoolean()

        // Check to see if the user has already voted
        if (hasVoted) {
            return VotingResult.USER_HAS_ALREADY_VOTED.toResponseEntity()
        }

        val canVote = (blockChainConnection.canVote(voteData.publicKey)).blockchainStringToBoolean()

        // Check if the user has the right to vote
        if (!canVote) {
            return VotingResult.USER_DOES_NOT_HAVE_RIGHTS_TO_VOTE.toResponseEntity()
        }

        // If the user is able to vote and has submitted the correct data, then his vote is cast

        val castVoteResult = blockChainConnection.castVote(voteData.publicKey, voteData.privateKeySignature).blockchainStringToBoolean()

        // If the casting is successful, then the vote is added to the buffer.

        return if(!castVoteResult){
            VotingResult.SIGNATURE_DOES_NOT_MATCH_THE_KEY.toResponseEntity()
        } else {
            addCastedVoteToList(voteData)
            VotingResult.VOTE_SENT_SUCCESSFULLY.toResponseEntity()
        }
    }

    fun addCastedVoteToList(voteData: VoteData){
        votesToAdd.add(voteData)
        votesToAdd.shuffle()
        if (votesToAdd.size == MAX_VOTES_LIST_SIZE) {
            println("Finalizing votes:")
            votesToAdd.forEach {
                blockChainConnection.finalizeVote(it.candidateId.toString(), "")
            }
            votesToAdd.clear()
            println("Voters after synchronization: ${blockChainConnection.getVoters()}")
            println("Results after synchronization: ${blockChainConnection.getResults()}")
        }
    }

    fun getUserFromDecodedToken(token: String): String {
        //TODO: parse JSON + try/catch error
        //Used only if public key is stored in database
        val parts = token.split(".")
        val decoder: Base64.Decoder = Base64.getUrlDecoder()
        return String(decoder.decode(parts[1]))
    }

    fun canUserVote(token: String): ResponseEntity<Boolean> {
        //TODO: Decode token, check in database and send result
        //Implemantation makes sense only if public key is stored in database

        //User can vote: 200 ok
        return ResponseEntity.ok().build()

        //User can't vote: 403 Forbidden
        //return ResponseEntity.status(403).build()
    }

    fun getResults(): ResponseEntity<List<ResultsData>> {
        println("get results");
        val candidates = authenticationUseCase.dbService.getCandidates()
        val results = blockChainConnection.getResults()?.map {
            ResultsData(
                it.first.toInt(),
                getCandidateNameForSpecifiedId(
                    candidates,
                    it.first.toInt()
                ),
                it.second
            )
        }
        return ResponseEntity.ok().body(results)
    }
}