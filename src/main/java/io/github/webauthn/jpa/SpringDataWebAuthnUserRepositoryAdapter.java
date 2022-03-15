package io.github.webauthn.jpa;

import io.github.webauthn.domain.WebAuthnUserRepository;
import io.github.webauthn.dto.RegistrationStartRequest;

import java.time.LocalDateTime;
import java.util.Optional;

public class SpringDataWebAuthnUserRepositoryAdapter implements WebAuthnUserRepository<JpaWebAuthnUser> {

    private final WebAuthnUserSpringDataRepository webAuthnUserSpringDataRepository;

    public SpringDataWebAuthnUserRepositoryAdapter(WebAuthnUserSpringDataRepository webAuthnUserSpringDataRepository) {
        this.webAuthnUserSpringDataRepository = webAuthnUserSpringDataRepository;
    }

    @Override
    public JpaWebAuthnUser save(JpaWebAuthnUser user) {
        return webAuthnUserSpringDataRepository.save(user);
    }

    @Override
    public Optional<JpaWebAuthnUser> findById(Long id) {
        return webAuthnUserSpringDataRepository.findById(id);
    }

    @Override
    public Optional<JpaWebAuthnUser> findByUsername(String username) {
        return webAuthnUserSpringDataRepository.findByUsername(username);
    }

    @Override
    public Optional<JpaWebAuthnUser> findByAddTokenAndRegistrationAddStartAfter(byte[] token, LocalDateTime after) {
        return webAuthnUserSpringDataRepository.findByAddTokenAndRegistrationAddStartAfter(token, after);
    }

    @Override
    public Optional<JpaWebAuthnUser> findByRecoveryToken(byte[] token) {
        return webAuthnUserSpringDataRepository.findByRecoveryToken(token);
    }

    @Override
    public void deleteById(Long id) {
        webAuthnUserSpringDataRepository.deleteById(id);
    }

    @Override
    public JpaWebAuthnUser newUser(RegistrationStartRequest startRequest) {
        JpaWebAuthnUser u = new JpaWebAuthnUser();
        u.setUsername(startRequest.getUsername());
        return u;
    }
}
