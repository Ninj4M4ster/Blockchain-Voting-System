package com.example.blockchain_voting_system.service

import com.example.blockchain_voting_system.data.RegisterUserData
import com.example.blockchain_voting_system.data.ResultsData
import com.example.blockchain_voting_system.data.UserData
import com.example.blockchain_voting_system.data.VoteData
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

    fun authenticateUser(userData: UserData) : String {

        val k = authenticationUseCase.login(userData.email, userData.password) ?: ""
        println(k)
        return k

//        println("User $userData trying to authenticate")
//        return when(authenticationUseCase.authenticateUser(userData)){
//            AuthenticationUseCaseResult.EMAIL_IS_NOT_VALID -> ResponseEntity.status(401).build()
//            AuthenticationUseCaseResult.AUTHENTICATION_FAILED -> ResponseEntity.status(401).build()
//            AuthenticationUseCaseResult.OK -> ResponseEntity.ok().build()
//        }
    }

    fun registerUser(registerUserData: RegisterUserData) : ResponseEntity<Unit> {
        println("User $registerUserData trying to register")
        val k = registrationUseCase.registerUser(registerUserData)
        println(k)
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