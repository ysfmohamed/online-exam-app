package com.joe.authservice.helpers;

import org.keycloak.representations.idm.CredentialRepresentation;

public class Credential {
    public static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredential = new CredentialRepresentation();
        passwordCredential.setTemporary(false);
        passwordCredential.setType(CredentialRepresentation.PASSWORD);
        passwordCredential.setValue(password);

        return passwordCredential;
    }
}
