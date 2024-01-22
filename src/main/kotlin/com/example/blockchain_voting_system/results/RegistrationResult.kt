package com.example.blockchain_voting_system.results

import org.springframework.http.ResponseEntity


enum class RegistrationResult{
    EMAIL_IS_NOT_VALID,
    EMAIL_IS_ALREADY_REGISTERED,
    EMAIL_DOES_NOT_HAVE_RIGHTS_TO_VOTE,
    OK
}

fun RegistrationResult.toResponseEntity() : ResponseEntity<String> {
    return when(this){
        RegistrationResult.OK -> ResponseEntity.ok().build()
        RegistrationResult.EMAIL_IS_NOT_VALID -> ResponseEntity.status(403).body("Email is not valid.")
        RegistrationResult.EMAIL_DOES_NOT_HAVE_RIGHTS_TO_VOTE -> ResponseEntity.status(403).body("Email does not have rights to vote.")
        RegistrationResult.EMAIL_IS_ALREADY_REGISTERED -> ResponseEntity.status(403).body("This email is already registered.")
    }
}