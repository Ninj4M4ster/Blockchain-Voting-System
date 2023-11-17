package com.example.blockchain_voting_system.data

data class VoteData(
    val email: String,
    val candidateId: Int,
    val publicKey: String,
    val privateKey: String
)