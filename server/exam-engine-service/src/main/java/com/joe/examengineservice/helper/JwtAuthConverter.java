package com.joe.examengineservice.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> authorities =
                Stream
                        .concat(
                                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                                extractRealmRoles(jwt).stream()
                        )
                        .collect(Collectors.toSet());

        return new JwtAuthenticationToken(jwt, authorities, jwt.getClaim("preferred_name"));
    }

    private Collection<? extends GrantedAuthority> extractRealmRoles(Jwt jwt) {
        if(jwt.getClaim("realm_access") == null) {
            return Set.of();
        }

        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        Collection<String> realmRoles = (Collection<String>) realmAccess.get("roles");

        log.info("Realm roles: " + realmRoles.toString());

        return realmRoles
                .stream()
                .map(realmRole -> new SimpleGrantedAuthority("ROLE_" + realmRole))
                .collect(Collectors.toSet());
    }
}
