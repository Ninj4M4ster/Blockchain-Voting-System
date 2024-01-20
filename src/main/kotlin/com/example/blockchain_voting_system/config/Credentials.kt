package com.example.blockchain_voting_system.config

import org.keycloak.representations.idm.CredentialRepresentation

object Credentials {
    fun createPasswordCredentials(password: String) : CredentialRepresentation {
        val passwordCredentials = CredentialRepresentation()
        passwordCredentials.isTemporary = false
        passwordCredentials.type = CredentialRepresentation.PASSWORD
        passwordCredentials.value = password
        return passwordCredentials
    }
}