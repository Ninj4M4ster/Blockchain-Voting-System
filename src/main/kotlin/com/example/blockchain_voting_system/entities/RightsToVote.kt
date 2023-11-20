package com.example.blockchain_voting_system.entities

import jakarta.persistence.*

@Entity
@Table(name="rights_to_vote")
data class RightsToVote(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?=null,
        @OneToOne
        @JoinColumn(name = "id")
        val client: Client?= null,
        @Column(name = "email", unique = true, nullable = false)
        val email: String?= null,

)