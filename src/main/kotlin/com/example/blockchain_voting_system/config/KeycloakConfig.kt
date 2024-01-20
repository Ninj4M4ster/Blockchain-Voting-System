package com.example.blockchain_voting_system.config

import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder

object KeycloakConfig {
    var keycloak: Keycloak? = null
    val serverUrl: String = "http://localhost:9000"
    val realm: String = "master"
    val clientId: String = "admin-cli"
    val clientSecret: String = "ZClmGp9fmR7oF2aE3JclzeVNYuy7jIVk"
    val adminUserName: String = "admin"
    val password: String = "password"

    fun getInstance(): Keycloak {
        if (keycloak == null) {
            keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .username(adminUserName)
                .password(password)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .resteasyClient(ResteasyClientBuilderImpl()
                    .connectionPoolSize(10)
                    .build())
                .build()
        }
        return keycloak!!
    }
}