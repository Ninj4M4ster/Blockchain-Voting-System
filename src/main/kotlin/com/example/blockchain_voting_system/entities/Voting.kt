package com.example.blockchain_voting_system.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "votings")
data class Voting(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int?= null,
        @Column(name = "voting_key", nullable = false)
        val votingKey: String?= null
)