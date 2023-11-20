package com.example.blockchain_voting_system

import com.example.blockchain_voting_system.entities.Client
import com.example.blockchain_voting_system.entities.EmailToVote
import com.example.blockchain_voting_system.entities.RightsToVote
import com.example.blockchain_voting_system.entities.Voting
import org.hibernate.cfg.Configuration
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DatabaseTest{

    @Test
    fun add_to_rights() {
        val configuration = Configuration()
        val sessionFactory = configuration
                .addAnnotatedClass(Client::class.java)
                .addAnnotatedClass(EmailToVote::class.java)
                .addAnnotatedClass(RightsToVote::class.java)
                .addAnnotatedClass(Voting::class.java)
                .buildSessionFactory()
        val session = sessionFactory.openSession()
        session.beginTransaction()
        val ent = RightsToVote(email =  "drze@gmail.com")
        session.merge(ent)
        val foundClient = session.find(RightsToVote::class.java, ent.id)
        session.refresh(foundClient)
        assertTrue(foundClient.email == "drze@gmail.com")
    }
}