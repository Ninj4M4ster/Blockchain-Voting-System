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
    fun authenticateUser(@RequestBody payload: UserData) : ResponseEntity<String> = ResponseEntity.ok(voteService.authenticateUser(payload))

    @CrossOrigin
    @PostMapping("/register")
    fun registerUser(@RequestBody payload: RegisterUserData) : ResponseEntity<Unit> = voteService.registerUser(payload)

    @CrossOrigin
    @PostMapping("/candidate")
    fun addCandidate(@RequestBody payload: CandidateData) : ResponseEntity<Unit> = voteService.addCandidate(payload)

    @CrossOrigin
    @PostMapping("/vote")
    fun vote(@RequestBody payload: VoteData) : ResponseEntity<String> = voteService.sendVote(payload)

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
    @GetMapping("/authenticated")
    fun isUserAuthenticated() : ResponseEntity<Unit> = voteService.isUserAuthenticated()

    @ExceptionHandler(WebClientResponseException.Unauthorized::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleUnauthorizedException(ex: WebClientResponseException.Unauthorized): ResponseEntity<String> = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.message)

}