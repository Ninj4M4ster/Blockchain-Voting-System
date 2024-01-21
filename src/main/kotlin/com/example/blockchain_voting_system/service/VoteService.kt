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
class VoteService(){

    private val authenticationUseCase = AuthenticationUseCase()
    private val registrationUseCase = RegistrationUseCase()
    private val blockchainUseCase = BlockchainUseCase()
    private val blockChainConnection = BlockChainConnection()

    fun addRightsToVote(voteRightsData: VoteRightsData) : ResponseEntity<Unit>{
        // Add rights to vote in blockchain
        println("Dziala dodawanie")
        return ResponseEntity.ok().build()


        // If succeded, add rights to vote in database
    }

    fun authenticateUser(userData: UserData) : String {
        return authenticationUseCase.login(userData.email, userData.password) ?: ""
    }

    // Keycloak will throw 401 if user not authenticated
    fun isUserAuthenticated() : ResponseEntity<Unit> = ResponseEntity.ok().build()

    fun registerUser(registerUserData: RegisterUserData) : ResponseEntity<Unit> {

        println("Elo")

        // Register user in Keycloak
        val response = authenticationUseCase.createUser(UserRequest(registerUserData.email, registerUserData.password))

        // Register user in database
        val k = registrationUseCase.registerUser(registerUserData)

        return when(k){
            RegistrationUseCaseResult.EMAIL_IS_NOT_VALID-> ResponseEntity.status(403).build()
            RegistrationUseCaseResult.PASSWORD_DONT_MATCH -> ResponseEntity.status(403).build()
            RegistrationUseCaseResult.EMAIL_IS_ALREADY_REGISTERED -> ResponseEntity.status(409).build()
            RegistrationUseCaseResult.OK -> ResponseEntity.ok().build()
        }
    }

    fun sendVote(voteData: VoteData) : ResponseEntity<Unit> {
        return when(blockchainUseCase.vote(voteData)){
            BlockchainUseCaseResult.USER_NOT_ALLOWED_TO_VOTE -> ResponseEntity.status(403).build()
            BlockchainUseCaseResult.VOTE_SENT -> ResponseEntity.ok().build()
        }
    }

    fun getUserFromDecodedToken(token: String): String {
        //TODO: parse JSON + try/catch error
        val parts = token.split(".")
        val decoder: Base64.Decoder = Base64.getUrlDecoder()
        return String(decoder.decode(parts[1]))
    }

    fun canUserVote(token: String) : ResponseEntity<Boolean> {
        //TODO: Decode token, check in database and send result

        //User can vote: 200 ok
        return ResponseEntity.ok().build()

        //User can't vote: 403 Forbidden
        //return ResponseEntity.status(403).build()
    }

    fun areResultsPublished(): ResponseEntity<Unit>{
        //TODO: Check if results are published

        //Results published: 200 ok
        return ResponseEntity.ok().build()

        //Results not published: 403 Forbidden
        //return ResponseEntity.status(403).build()
    }

    fun getResults() : ResponseEntity<List<ResultsData>> {
        println("Get results")
        return ResponseEntity.ok().body(blockchainUseCase.getResults())
    }
}