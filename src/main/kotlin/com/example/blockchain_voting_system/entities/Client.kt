package com.example.blockchain_voting_system.entities

import jakarta.persistence.*

@Entity
@Table(name="clients")
data class Client (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        val id: Int?= null,

        @Column(name = "password", nullable = false)
        val password: String?= null,

        @OneToOne(cascade = [(CascadeType.ALL)], optional = false)
        @JoinColumn(name="id_right_to_vote")
        val idRightToVote: RightsToVote?= null,
)