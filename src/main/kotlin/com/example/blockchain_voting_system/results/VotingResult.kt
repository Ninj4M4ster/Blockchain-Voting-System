package com.example.blockchain_voting_system.results

import org.springframework.http.ResponseEntity

enum class VotingResult {
    VOTE_SENT_SUCCESSFULLY,
    CANDIDATE_ID_DOES_NOT_EXIST,
    USER_DOES_NOT_HAVE_RIGHTS_TO_VOTE,
    USER_HAS_ALREADY_VOTED,
    SIGNATURE_DOES_NOT_MATCH_THE_KEY
}

fun VotingResult.toResponseEntity() : ResponseEntity<String> {
    return when(this){
        VotingResult.VOTE_SENT_SUCCESSFULLY -> ResponseEntity.status(200).build()
        VotingResult.CANDIDATE_ID_DOES_NOT_EXIST -> ResponseEntity.status(404).body("Candidate id does not exist")
        VotingResult.USER_DOES_NOT_HAVE_RIGHTS_TO_VOTE -> ResponseEntity.status(403).body("You don't have permissions to vote")
        VotingResult.USER_HAS_ALREADY_VOTED -> ResponseEntity.status(403).body("You have already voted")
        VotingResult.SIGNATURE_DOES_NOT_MATCH_THE_KEY -> ResponseEntity.status(403).body("Public key and signature do not match")
    }
}