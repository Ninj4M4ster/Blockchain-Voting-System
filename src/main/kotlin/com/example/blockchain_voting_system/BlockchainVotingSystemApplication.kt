package com.example.blockchain_voting_system

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class BlockchainVotingSystemApplication{

}

fun main(args: Array<String>) {
	runApplication<BlockchainVotingSystemApplication>(*args)
}
