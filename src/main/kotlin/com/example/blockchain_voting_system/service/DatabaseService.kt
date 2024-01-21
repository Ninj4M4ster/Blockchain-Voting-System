package com.example.blockchain_voting_system.service

import com.example.blockchain_voting_system.entities.Client
import com.example.blockchain_voting_system.entities.EmailToVote
import com.example.blockchain_voting_system.entities.RightsToVote
import com.example.blockchain_voting_system.entities.Voting
import org.hibernate.cfg.Configuration

/**
 * Database service. It handles login and registration of users.
 */
class DatabaseService private constructor() {
    private val sessionFactory = Configuration()
            .addAnnotatedClass(Client::class.java)
            .addAnnotatedClass(EmailToVote::class.java)
            .addAnnotatedClass(RightsToVote::class.java)
            .addAnnotatedClass(Voting::class.java)
            .buildSessionFactory()

    /**
     * Companion object for static access to database service.
     */
    companion object {
        private val instance = DatabaseService()
        fun getService() : DatabaseService {
            return instance
        }
    }

    /**
     * Check if user is registered.
     *
     * @param email User email
     * @param password User password
     * @return Can the user be logged in?
     */
    fun checkLoginCredentials(email: String, password: String) : Boolean {
        val foundEntry = getRightsBasedOnEmail(email)
        return foundEntry?.client != null && foundEntry.client.password == password
    }

    /**
     * Check if given email is already registered
     *
     * @param email User email
     * @return Is there a user already registered with given email?
     */
    fun isEmailRegistered(email: String) : Boolean {
        val foundEntry = getRightsBasedOnEmail(email)
        return foundEntry?.client != null
    }

    /**
     * Register user using given email and password.
     *
     * @param email User email
     * @param password User password
     * @return Was the user registered?
     */
    fun registerUser(email: String, password: String) : Boolean {
        val foundEntry = getRightsBasedOnEmail(email) ?: return false
        val session = sessionFactory.openSession()
        val transaction = session.beginTransaction()
        val client = Client(password = password, idRightToVote = foundEntry)
        session.refresh(foundEntry)
        session.merge(client)
        transaction.commit()
        session.close()
        return true
    }

    /**
     * Get voting rights from database based on given email
     *
     * @param email User email
     * @return List of rights to vote with given email
     */
    private fun getRightsBasedOnEmail(email: String) : RightsToVote? {
        val session = sessionFactory.openSession()
        session.beginTransaction()
        val query = session.createQuery(
                "from RightsToVote where email = ?1",
                RightsToVote::class.java)
        query.setParameter(1, email)
        val foundEntry = query.list()
        session.close()
        if(foundEntry.isEmpty())
            return null
        return foundEntry[0]
    }

    /**
     * Add email with rights to vote to database
     *
     * @param email user email
     * @return True if email was added, false if email was already in the database.
     */
    public fun addVotesRight(email: String) : Boolean {
        val session = sessionFactory.openSession()
        val transaction = session.beginTransaction()
        val query = session.createQuery(
                "from RightsToVote where email = ?1",
                RightsToVote::class.java)
        query.setParameter(1, email)
        val foundEntry = query.list()
        if(foundEntry.isEmpty()) {
            val newRight = RightsToVote(email = email)
            session.merge(newRight)
            transaction.commit()
            session.close()
            return true
        }
        session.close()
        return false
    }
}