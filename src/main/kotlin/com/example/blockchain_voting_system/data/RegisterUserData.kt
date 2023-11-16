package com.example.blockchain_voting_system.data

data class RegisterUserData(
    val email: String,
    val password: String,
    val repeatedPassword: String
)