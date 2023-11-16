package com.example.blockchain_voting_system.controller

import com.example.blockchain_voting_system.data.RegisterUserData
import com.example.blockchain_voting_system.data.UserData
import com.example.blockchain_voting_system.service.VoteService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

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
}