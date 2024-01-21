package com.example.blockchain_voting_system.controller

import com.example.blockchain_voting_system.data.*
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
        println("Authenticate")
        return ResponseEntity.ok(voteService.authenticateUser(payload))
    }

    @CrossOrigin
    @PostMapping("/register")
    fun registerUser(@RequestBody payload: RegisterUserData) : ResponseEntity<Unit>{
        println("Register")
        val response = voteService.registerUser(payload)
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

    @CrossOrigin
    @PostMapping("/canUserVote")
    fun canUserVote(@RequestBody payload: CanUserVoteData) : ResponseEntity<Boolean> = voteService.canUserVote(payload.token)

    @CrossOrigin
    @PostMapping("/voterights")
    fun addRightsToVote(@RequestBody payload: VoteRightsData) : ResponseEntity<Unit> = voteService.addRightsToVote(payload)

    @CrossOrigin
    @GetMapping("/results/published")
    fun areResultsPublished() : ResponseEntity<Unit> = voteService.areResultsPublished()

    @CrossOrigin
    @GetMapping("/authenticated")
    fun isUserAuthenticated() : ResponseEntity<Unit> = voteService.isUserAuthenticated()

    @ExceptionHandler(WebClientResponseException.Unauthorized::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleUnauthorizedException(ex: WebClientResponseException.Unauthorized): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.message)
    }



}