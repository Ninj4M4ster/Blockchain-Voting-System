package com.example.blockchain_voting_system.controller

import com.example.blockchain_voting_system.data.RegisterUserData
import com.example.blockchain_voting_system.data.ResultsData
import com.example.blockchain_voting_system.data.UserData
import com.example.blockchain_voting_system.data.VoteData
import com.example.blockchain_voting_system.service.VoteService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class VoteController(private val voteService: VoteService){

    @CrossOrigin
    @PostMapping("/login")
    fun authenticateUser(@RequestBody payload: UserData) : ResponseEntity<Unit>{
        return voteService.authenticateUser(payload)
    }

    @CrossOrigin
    @PostMapping("/register")
    fun registerUser(@RequestBody payload: RegisterUserData) : ResponseEntity<Unit> = voteService.registerUser(payload)

    @CrossOrigin
    @PostMapping("/vote")
    fun vote(@RequestBody payload: VoteData) : ResponseEntity<Unit> = voteService.sendVote(payload)

    @CrossOrigin
    @GetMapping("/results")
    fun getResults() : ResponseEntity<List<ResultsData>> = voteService.getResults()

}