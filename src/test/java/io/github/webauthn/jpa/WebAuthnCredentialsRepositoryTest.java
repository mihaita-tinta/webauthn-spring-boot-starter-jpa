package io.github.webauthn.jpa;


import io.github.webauthn.domain.WebAuthnUser;
import io.github.webauthn.jpa.JpaWebAuthnCredentials;
import io.github.webauthn.jpa.JpaWebAuthnUser;
import io.github.webauthn.jpa.WebAuthnCredentialsSpringDataRepository;
import io.github.webauthn.jpa.WebAuthnUserSpringDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class WebAuthnCredentialsRepositoryTest {

    @Autowired
    WebAuthnUserSpringDataRepository webAuthnUserRepository;
    @Autowired
    WebAuthnCredentialsSpringDataRepository credentialsRepository;

    @Test
    public void test() {

        JpaWebAuthnUser user = new JpaWebAuthnUser();
        user.setUsername("junit");
        WebAuthnUser saved = webAuthnUserRepository.save(user);

        JpaWebAuthnCredentials credentials = new JpaWebAuthnCredentials();
        credentials.setAppUserId(saved.getId());
        credentials.setCredentialId(Base64.getDecoder().decode("ARgxyHfw5N83gRMl2M7vHhqkQmtHwDJ8QCciM4uWlyGivpTf00b8TIvy6BEpBAZVCA9J5w"));
        credentials.setPublicKeyCose(Base64.getDecoder().decode("pQECAyYgASFYIEayvcdalRrrCPEidpoYbZdHmNsDeIyYBoVJ6HnwmUq4IlggV4V9TNhyHSGQxDTr4+TUWWP60edcpQlybrwOlIrxacU="));
        credentials.setCount(1L);
        credentialsRepository.save(credentials);

        assertEquals(credentials, credentialsRepository.findAllByAppUserId(saved.getId()).get(0));
    }

}
