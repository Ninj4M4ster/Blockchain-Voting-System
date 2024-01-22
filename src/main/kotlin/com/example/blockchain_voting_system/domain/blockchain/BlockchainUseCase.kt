package com.example.blockchain_voting_system.domain.blockchain

import com.example.blockchain_voting_system.data.ResultsData
import com.example.blockchain_voting_system.data.VoteData
import com.example.blockchain_voting_system.entities.Candidate

class BlockchainUseCase{

    fun vote(voteData: VoteData): BlockchainUseCaseResult{
        return if(!canUserVote(voteData.email)){
            BlockchainUseCaseResult.USER_NOT_ALLOWED_TO_VOTE
        } else{
            sendVoteToBlockchainSystem()
            BlockchainUseCaseResult.VOTE_SENT
        }
    }

    fun getResults() : List<ResultsData>{
        return listOf(
            ResultsData(1, "Tom", 100),
            ResultsData(2,"Kathy", 150),
            ResultsData(3,"Sam", 200),
            ResultsData(4,"Adam", 50),
            ResultsData(5,"Sam",1000)
        )
    }

    private fun canUserVote(email: String) : Boolean {
        //TODO: check vote rights
        return false
    }

    private fun sendVoteToBlockchainSystem(){
        //TODO: send to blockchain
    }

    fun getCandidateNameForSpecifiedId(candidates: List<Candidate>, id: Int) : String{
        for (candidate in candidates) {
            if(candidate.id == id){
                return candidate.name ?: "Name not specified"
            }
        }
        return "Name not specified"
    }

}