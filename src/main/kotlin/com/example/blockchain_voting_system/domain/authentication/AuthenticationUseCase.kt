package com.example.blockchain_voting_system.domain.authentication

import com.example.blockchain_voting_system.config.Credentials
import com.example.blockchain_voting_system.config.KeycloakConfig
import com.example.blockchain_voting_system.data.UserData
import com.example.blockchain_voting_system.data.UserRequest
import com.example.blockchain_voting_system.results.AuthenticationUseCaseResult
import com.example.blockchain_voting_system.service.DatabaseService
import jakarta.transaction.Transactional
import jakarta.ws.rs.core.Response
import org.keycloak.admin.client.resource.RealmResource
import org.keycloak.admin.client.resource.UsersResource
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

class AuthenticationUseCase{

    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    val dbService = DatabaseService.getService()
    private var realmName: String = "myrealm"

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

    @Transactional
    fun createUser(userRequest: UserRequest) : Response {
        val credential: CredentialRepresentation = Credentials.createPasswordCredentials(userRequest.password!!)
        val user = UserRepresentation()
        user.username = userRequest.email
        user.email = userRequest.email
        user.credentials = listOf(credential)
        user.isEmailVerified = true
        user.isEnabled = true
        val realmResource: RealmResource = KeycloakConfig.getInstance().realm(realmName)
        val usersResource: UsersResource = realmResource.users()
        val response = usersResource.create(user)

//        if (response.status == 201) {
//            val patientRealmRole: RoleRepresentation = realmResource.roles().get("patient").toRepresentation()
//            val userId = CreatedResponseUtil.getCreatedId(response)
//            val userResource: UserResource = usersResource.get(userId)
//            userResource.roles().realmLevel().add(listOf(patientRealmRole))
//        }
        return response
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