package io.github.webauthn.jpa;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface WebAuthnUserSpringDataRepository extends CrudRepository<JpaWebAuthnUser, Long> {

    Optional<JpaWebAuthnUser> findByUsername(String username);

    Optional<JpaWebAuthnUser> findByAddTokenAndRegistrationAddStartAfter(byte[] token, LocalDateTime after);

    Optional<JpaWebAuthnUser> findByRecoveryToken(byte[] token);
}
