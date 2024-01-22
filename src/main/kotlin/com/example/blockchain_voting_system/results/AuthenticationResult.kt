package com.example.blockchain_voting_system.results

import org.springframework.http.ResponseEntity

enum class AuthenticationResult{
    EMAIL_IS_NOT_VALID,
    AUTHENTICATION_FAILED,
    OK
}

fun AuthenticationResult.toResponseEntity() : ResponseEntity<String> {
    return when(this){
        AuthenticationResult.OK-> ResponseEntity.ok().build()
        AuthenticationResult.EMAIL_IS_NOT_VALID -> ResponseEntity.status(403).body("Email is not valid.")
        AuthenticationResult.AUTHENTICATION_FAILED -> ResponseEntity.status(403).body("Unknown error has occured. Please try again later.")
    }
}