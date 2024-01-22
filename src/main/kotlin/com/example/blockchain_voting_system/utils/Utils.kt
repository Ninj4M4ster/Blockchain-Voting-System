package com.example.blockchain_voting_system.utils

import com.example.blockchain_voting_system.entities.Candidate

fun String.blockchainStringToBoolean() = this == "True" || this == "true"

fun getCandidateNameForSpecifiedId(candidates: List<Candidate>, id: Int) : String{
    for (candidate in candidates) {
        if(candidate.id == id){
            return candidate.name ?: "Name not specified"
        }
    }
    return "Name not specified"
}

fun checkIfIdExists(candidates: List<Candidate>, id: Int) : Boolean{
    candidates.forEach{
        if (it.id == id){
            return true
        }
    }
    return false
}