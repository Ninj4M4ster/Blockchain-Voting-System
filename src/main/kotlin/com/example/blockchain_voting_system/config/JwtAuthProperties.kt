package com.example.blockchain_voting_system.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.validation.annotation.Validated

@Configuration
@ConfigurationProperties(prefix = "jwt.auth.converter")
class JwtAuthProperties {
    var principalAttribute: String? = null
}