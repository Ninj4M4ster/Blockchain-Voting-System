package com.example.blockchain_voting_system.entities

import jakarta.persistence.*

@Entity
@Table(name = "candidates")
data class Candidate(
        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        val id: Int?= null,
        @Column(name = "name", nullable = false)
        val name: String?= null,
        @Column(name = "description", nullable = false)
        val description: String?= null
)
