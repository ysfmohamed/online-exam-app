package com.joe.authservice.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.joe.authservice.dtos.LoginDto;
import com.joe.authservice.dtos.SignupDto;
import com.joe.authservice.helpers.Credential;
import com.joe.authservice.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.Response;
import java.util.Collections;

@Service
@Slf4j
public class KeycloakService {
    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.grant-type}")
    private String grantType;

    @Value("${keycloak.token-endpoint}")
    private String tokenEndpoint;

    @Value("${keycloak.userinfo-endpoint}")
    private String userInfoEndpoint;
    private final KeycloakConfig keycloakConfig;
    private final UserRepository userRepository;
    private final UserService userService;

    public KeycloakService(KeycloakConfig keycloakConfig, UserRepository userRepository, UserService userService) {
        this.keycloakConfig = keycloakConfig;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public ResponseEntity<JsonNode> login(LoginDto user) {
        RestTemplate rest = new RestTemplate();

        HttpHeaders httpHeaders = buildHttpHeaders();
        MultiValueMap<String, String> map = buildHttpBody(user);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, httpHeaders);

        ResponseEntity<JsonNode> response = rest.postForEntity(tokenEndpoint, httpEntity, JsonNode.class);

        return response;
    }

    private HttpHeaders buildHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return httpHeaders;
    }

    private MultiValueMap<String, String> buildHttpBody(LoginDto user) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("grant_type", grantType);
        map.add("client_id", clientId);
        map.add("username", user.getUsername());
        map.add("password", user.getPassword());
        map.add("scope", "openid");

        return map;
    }
    public String addUser(SignupDto userDto) {
        String newlyCreatedUserId = "";

        CredentialRepresentation credential = Credential.createPasswordCredentials(userDto.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);

        Response response = createUser(user);
        newlyCreatedUserId = getUserId(response);

        RoleRepresentation roleRepresentation = createRoleRepresentation(userDto.getRole());
        addRepresentationToUser(newlyCreatedUserId, roleRepresentation);

        userService.addUser(newlyCreatedUserId, userDto);

        return newlyCreatedUserId;
    }

    private Response createUser(UserRepresentation user) {
        UsersResource usersResource = keycloakConfig.getInstance().realm(realm).users();
        Response response = usersResource.create(user);

        return response;
    }
    private RoleRepresentation createRoleRepresentation(String role) {
        return keycloakConfig.getInstance().realm(realm).roles().get(role).toRepresentation();
    }

    private void addRepresentationToUser(String userId, RoleRepresentation role) {
        keycloakConfig.getInstance().realm(realm).users().get(userId).roles().realmLevel().add(Collections.singletonList(role));
    }

    private String getUserId(Response response) {
        return CreatedResponseUtil.getCreatedId(response);
    }

    public void logout(String userId) {
        log.info("(from logout) user id: " + userId);
        keycloakConfig
                .getInstance()
                .realm(realm)
                .users()
                .get(userId).logout();
    }
}
