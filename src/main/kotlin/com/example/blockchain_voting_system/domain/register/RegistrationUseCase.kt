package com.example.blockchain_voting_system.domain.register

import com.example.blockchain_voting_system.data.RegisterUserData
import com.example.blockchain_voting_system.service.DatabaseService

class RegistrationUseCase{

    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    private val dbService = DatabaseService.getService()

    fun registerUser(registerUserData: RegisterUserData): RegistrationUseCaseResult {
        return if(!isEmailValid(registerUserData.email)){
            RegistrationUseCaseResult.EMAIL_IS_NOT_VALID
        } else if(checkIfEmailIsAlreadyRegistered(registerUserData.email)){
            RegistrationUseCaseResult.EMAIL_IS_ALREADY_REGISTERED
        } else{
            val result = registerUserInTheDatabase(registerUserData.email, registerUserData.password)
            if (result){
                RegistrationUseCaseResult.OK
            } else {
                RegistrationUseCaseResult.EMAIL_DOES_NOT_HAVE_RIGHTS_TO_VOTE
            }
        }
    }

    private fun isEmailValid(email: String) = email.matches(emailRegex.toRegex())

    private fun checkIfPasswordsMatch(password: String, repeatedPassword: String) = password == repeatedPassword

    private fun checkIfEmailIsAlreadyRegistered(email: String): Boolean{
        return dbService.isEmailRegistered(email)
    }

    private fun registerUserInTheDatabase(email: String, password: String): Boolean = dbService.registerUser(email, password)
}