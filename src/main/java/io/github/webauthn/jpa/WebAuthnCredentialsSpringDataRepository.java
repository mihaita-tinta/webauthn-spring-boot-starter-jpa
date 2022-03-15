package io.github.webauthn.jpa;


import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface WebAuthnCredentialsSpringDataRepository extends CrudRepository<JpaWebAuthnCredentials, Long> {

    List<JpaWebAuthnCredentials> findAllByAppUserId(Long userId);

    Optional<JpaWebAuthnCredentials> findByCredentialIdAndAppUserId(byte[] credentialId, Long userId);

    List<JpaWebAuthnCredentials> findByCredentialId(byte[] credentialId);

    JpaWebAuthnCredentials save(JpaWebAuthnCredentials credentials);

    void deleteByAppUserId(Long appUserId);

    void deleteById(Long id);
}
