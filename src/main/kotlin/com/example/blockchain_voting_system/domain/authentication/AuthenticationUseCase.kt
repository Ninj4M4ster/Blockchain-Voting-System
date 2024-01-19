package com.example.blockchain_voting_system.domain.authentication

import com.example.blockchain_voting_system.config.KeycloakConfig
import com.example.blockchain_voting_system.data.UserData
import com.example.blockchain_voting_system.service.DatabaseService
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

class AuthenticationUseCase{

    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    private val dbService = DatabaseService.getService()

    private val realmName: String = "myrealm"

    fun login(login: String, password: String): String? {
        val webClient: WebClient = WebClient
            .builder()
            .baseUrl(KeycloakConfig.serverUrl)
            .build()
        val endPointUrl: String = buildString {
            append(KeycloakConfig.serverUrl)
            append("/realms/")
            append(realmName)
            append("/protocol/openid-connect/token")
        }
        val requestBody: BodyInserters.FormInserter<String> = BodyInserters
            .fromFormData("grant_type", "password")
            .with("client_id", "myclient")
            .with("client_secret", "AryzBoAZbILq2PPlDrBbVmoY60f0etfE")
            .with("username", login)
            .with("password", password)
        return webClient
                .post()
                .uri(endPointUrl)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(requestBody)
                .retrieve()
                .bodyToMono<String>()
                .block()

    }

    @Bean
    fun authenticateUser(userData: UserData): AuthenticationUseCaseResult {

        return if(!isEmailValid(userData.email)){
            AuthenticationUseCaseResult.EMAIL_IS_NOT_VALID
        } else if(!checkUserCredentials(userData.email, userData.password)){
            AuthenticationUseCaseResult.AUTHENTICATION_FAILED
        } else{
            AuthenticationUseCaseResult.OK
        }
    }

    private fun isEmailValid(email: String) = email.matches(emailRegex.toRegex())

    private fun checkUserCredentials(email: String, password: String): Boolean{
        return dbService.checkLoginCredentials(email, password)
    }

}