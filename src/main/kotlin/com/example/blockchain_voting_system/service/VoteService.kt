package com.example.blockchain_voting_system.service

import BlockChainConnection
import com.example.blockchain_voting_system.data.*
import com.example.blockchain_voting_system.domain.authentication.AuthenticationUseCase
import com.example.blockchain_voting_system.domain.blockchain.BlockchainUseCase
import com.example.blockchain_voting_system.domain.blockchain.BlockchainUseCaseResult
import com.example.blockchain_voting_system.domain.register.RegistrationUseCase
import com.example.blockchain_voting_system.domain.register.RegistrationUseCaseResult
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
class VoteService() {

    private val authenticationUseCase = AuthenticationUseCase()
    private val registrationUseCase = RegistrationUseCase()
    private val blockchainUseCase = BlockchainUseCase()
    private val blockChainConnection = BlockChainConnection()

    private val votesToAdd = mutableListOf<VoteData>()
    private val MAX_VOTES_LIST_SIZE = 1

    fun addRightsToVote(voteRightsData: VoteRightsData): ResponseEntity<Unit> {
        println("Elo dodajemy")
        // Add rights to vote in the database
        return if(authenticationUseCase.dbService.addVotesRight(voteRightsData.email)){
            ResponseEntity.ok().build()
        } else{
            ResponseEntity.status(409).build()
        }
    }

    fun authenticateUser(userData: UserData): String {
        return authenticationUseCase.login(userData.email, userData.password) ?: ""
    }

    // Keycloak will throw 401 if user not authenticated
    fun isUserAuthenticated(): ResponseEntity<Unit> = ResponseEntity.ok().build()

    fun registerUser(registerUserData: RegisterUserData): ResponseEntity<Unit> {

        println("Elo")

        // Register user in Keycloak
        val response = authenticationUseCase.createUser(UserRequest(registerUserData.email, registerUserData.password))
        blockChainConnection.addVoter(registerUserData.publicKey)
        // Register user in database

        return when (registrationUseCase.registerUser(registerUserData)) {
            RegistrationUseCaseResult.EMAIL_IS_NOT_VALID -> ResponseEntity.status(403).build()
            RegistrationUseCaseResult.PASSWORD_DONT_MATCH -> ResponseEntity.status(403).build()
            RegistrationUseCaseResult.EMAIL_IS_ALREADY_REGISTERED -> ResponseEntity.status(409).build()
            RegistrationUseCaseResult.EMAIL_DOES_NOT_HAVE_RIGHTS_TO_VOTE -> ResponseEntity.status(409).build()
            RegistrationUseCaseResult.OK -> ResponseEntity.ok().build()
        }
    }

    fun String.toBoolean() = this == "True" || this == "true"

    fun sendVote(voteData: VoteData): ResponseEntity<Unit> {

        println("Elo wysylamy glos")

        // Check if candidate with voteData.id exists

        // Send signature to blockchain

        println("Voters before: ${blockChainConnection.getVoters()}")
        println("Results before: ${blockChainConnection.getResults()}")

        val hasVoted = (blockChainConnection.hasVoted(voteData.publicKey)).toBoolean()
        val canVote =  (blockChainConnection.canVote(voteData.publicKey)).toBoolean()

        println("Has voted $hasVoted can Vote $canVote")

        if (!hasVoted && canVote) {

            //TODO zapytaj Janka ktora + handle error: zły podpis
            val addSignatureResult = blockChainConnection.castVote(voteData.publicKey, voteData.privateKey)
            println("Add signature result $addSignatureResult")

            votesToAdd.add(voteData)
            votesToAdd.shuffle()

            if(votesToAdd.size == MAX_VOTES_LIST_SIZE){
                println("Adding to blockchain")
                votesToAdd.forEach {
                    blockChainConnection.finalizeVote(it.candidateId.toString(), "")
                }
                votesToAdd.clear()
            }
        }

        println("Voters after: ${blockChainConnection.getVoters()}")
        println("Results after: ${blockChainConnection.getResults()}")

        return ResponseEntity.ok().build()
    }


    fun getUserFromDecodedToken(token: String): String {
        //TODO: parse JSON + try/catch error
        val parts = token.split(".")
        val decoder: Base64.Decoder = Base64.getUrlDecoder()
        return String(decoder.decode(parts[1]))
    }

    fun canUserVote(token: String): ResponseEntity<Boolean> {
        //TODO: Decode token, check in database and send result

        //User can vote: 200 ok
        return ResponseEntity.ok().build()

        //User can't vote: 403 Forbidden
        //return ResponseEntity.status(403).build()
    }

    fun areResultsPublished(): ResponseEntity<Unit> {
        //TODO: Check if results are published

        //Results published: 200 ok
        return ResponseEntity.ok().build()

        //Results not published: 403 Forbidden
        //return ResponseEntity.status(403).build()
    }

    fun getResults(): ResponseEntity<List<ResultsData>> {
        println("Get results")
        val results = blockChainConnection.getResults()?.map{
            ResultsData(it.first.toInt(), "Example", it.second)
        }

        return ResponseEntity.ok().body(results)
    }
}