package com.example.blockchain_voting_system.entities

import jakarta.persistence.*

@Entity
@Table(name = "email_to_vote")
data class EmailToVote(
        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        val id: Int?= null,
        @ManyToOne
        @JoinColumn(name = "id_user", referencedColumnName = "id")
        val client: Client?= null,
        @ManyToOne
        @JoinColumn(name="id_voting", referencedColumnName = "id")
        val voting: Voting?= null,
)