package com.example.blockchain_voting_system.data

data class VoteData(
    val candidateId: Int,
    val publicKey: String,
    val privateKeySignature: String
)