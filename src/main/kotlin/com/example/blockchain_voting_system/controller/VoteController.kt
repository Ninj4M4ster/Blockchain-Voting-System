package com.example.blockchain_voting_system.controller

import com.example.blockchain_voting_system.data.RegisterUserData
import com.example.blockchain_voting_system.data.ResultsData
import com.example.blockchain_voting_system.data.UserData
import com.example.blockchain_voting_system.data.VoteData
import com.example.blockchain_voting_system.service.VoteService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClientResponseException

@RestController
class VoteController(private val voteService: VoteService){

    @CrossOrigin
    @PostMapping("/login")
    fun authenticateUser(@RequestBody payload: UserData) : ResponseEntity<String>{
        return ResponseEntity.ok(voteService.authenticateUser(payload))
    }

    @CrossOrigin
    @PostMapping("/register")
    fun registerUser(@RequestBody payload: RegisterUserData) : ResponseEntity<Unit>{
        val k = voteService.registerUser(payload)
        println(k)
        return k
    }
    @CrossOrigin
    @PostMapping("/vote")
    fun vote(@RequestBody payload: VoteData) : ResponseEntity<Unit> = voteService.sendVote(payload)

    @CrossOrigin
    @GetMapping("/results")
    fun getResults() : ResponseEntity<List<ResultsData>> = voteService.getResults()

    @ExceptionHandler(WebClientResponseException.Unauthorized::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleUnauthorizedException(ex: WebClientResponseException.Unauthorized): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.message)
    }

}