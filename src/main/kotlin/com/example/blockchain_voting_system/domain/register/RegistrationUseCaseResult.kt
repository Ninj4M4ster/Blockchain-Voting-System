package com.example.blockchain_voting_system.domain.register


enum class RegistrationUseCaseResult{
    EMAIL_IS_NOT_VALID,
    EMAIL_IS_ALREADY_REGISTERED,
    PASSWORD_DONT_MATCH,
    EMAIL_DOES_NOT_HAVE_RIGHTS_TO_VOTE,
    OK
}