package com.example.blockchain_voting_system.config

import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtClaimNames
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.stereotype.Component
import java.util.stream.Collectors
import java.util.stream.Stream

@Component
class JwtAuthConverter(private val jwtAuthProperties: JwtAuthProperties) :
    Converter<Jwt, AbstractAuthenticationToken> {
    private val jwtGrantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter()

    override fun convert(source: Jwt): AbstractAuthenticationToken {
        val authorities: Collection<GrantedAuthority> = Stream.concat(
            jwtGrantedAuthoritiesConverter.convert(source)!!.stream(),
            extractRealmRoles(source).stream()
        ).collect(Collectors.toSet())
        return JwtAuthenticationToken(source, authorities, getPrincipalClaimName(source))
    }

    private fun getPrincipalClaimName(source: Jwt): String {
        var claimName = JwtClaimNames.SUB
        if (jwtAuthProperties.principalAttribute != null) {
            claimName = jwtAuthProperties.principalAttribute!!
        }
        return source.getClaim(claimName)
    }

    private fun extractRealmRoles(source: Jwt): Collection<GrantedAuthority> {
        val realmAccess = source.getClaim<Map<String, Any>>("realm_access")
        val realmRoles = realmAccess["roles"] as Collection<String>?
        return realmRoles!!.stream()
            .map { role: String ->
                SimpleGrantedAuthority(
                    "ROLE_$role"
                )
            }
            .collect(Collectors.toSet())
    }
}