package com.example.blockchain_voting_system.service

import com.example.blockchain_voting_system.entities.Client
import com.example.blockchain_voting_system.entities.EmailToVote
import com.example.blockchain_voting_system.entities.RightsToVote
import com.example.blockchain_voting_system.entities.Voting
import org.hibernate.cfg.Configuration

class DatabaseService private constructor() {
    private val sessionFactory = Configuration()
            .addAnnotatedClass(Client::class.java)
            .addAnnotatedClass(EmailToVote::class.java)
            .addAnnotatedClass(RightsToVote::class.java)
            .addAnnotatedClass(Voting::class.java)
            .buildSessionFactory()
    companion object {
        private val instance = DatabaseService()
        fun getService() : DatabaseService {
            return instance
        }
    }

    fun checkLoginCredentials(email: String, password: String) : Boolean {
        val session = sessionFactory.openSession()
        session.beginTransaction()
        val query = session.createQuery("from RightsToVote where email = ?1", RightsToVote::class.java)
        query.setParameter(1, email)
        val foundEntry = query.list()
        session.close()
        return foundEntry.size == 1 && foundEntry[0].client != null && foundEntry[0].client?.password == password
    }
}