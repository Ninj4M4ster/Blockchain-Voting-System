package com.example.blockchain_voting_system.domain.authentication

import com.example.blockchain_voting_system.data.UserData
import com.example.blockchain_voting_system.service.DatabaseService
import org.springframework.context.annotation.Bean

class AuthenticationUseCase{

    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    private val dbService = DatabaseService.getService()

    @Bean
    fun authenticateUser(userData: UserData): AuthenticationUseCaseResult {
        return if(!isEmailValid(userData.email)){
            AuthenticationUseCaseResult.EMAIL_IS_NOT_VALID
        } else if(!checkUserCredentials(userData.email, userData.password)){
            AuthenticationUseCaseResult.AUTHENTICATION_FAILED
        } else{
            AuthenticationUseCaseResult.OK
        }
    }

    private fun isEmailValid(email: String) = email.matches(emailRegex.toRegex())

    private fun checkUserCredentials(email: String, password: String): Boolean{
        //TODO: OAuth2 itd
        return dbService.checkLoginCredentials(email, password)
    }

}