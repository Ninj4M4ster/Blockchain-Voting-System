package com.example.blockchain_voting_system.domain.register

import com.example.blockchain_voting_system.data.RegisterUserData
import com.example.blockchain_voting_system.service.DatabaseService

class RegistrationUseCase{

    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    private val dbService = DatabaseService.getService()

    fun registerUser(registerUserData: RegisterUserData): RegistrationUseCaseResult {
        return if(!isEmailValid(registerUserData.email)){
            RegistrationUseCaseResult.EMAIL_IS_NOT_VALID
        } else if(!checkIfPasswordsMatch(registerUserData.password, registerUserData.repeatedPassword)){
            RegistrationUseCaseResult.PASSWORD_DONT_MATCH
        } else if(checkIfEmailIsAlreadyRegistered(registerUserData.email)){
            RegistrationUseCaseResult.EMAIL_IS_ALREADY_REGISTERED
        } else{
            registerUserInTheDatabase(registerUserData.email, registerUserData.password)
            RegistrationUseCaseResult.OK
        }
    }

    private fun isEmailValid(email: String) = email.matches(emailRegex.toRegex())

    private fun checkIfPasswordsMatch(password: String, repeatedPassword: String) = password == repeatedPassword

    private fun checkIfEmailIsAlreadyRegistered(email: String): Boolean{
        return dbService.isEmailRegistered(email)
    }

    private fun registerUserInTheDatabase(email: String, password: String){
        //TODO: register user
        dbService.registerUser(email, password)
    }
}