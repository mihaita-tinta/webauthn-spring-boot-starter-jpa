package io.github.webauthn.jpa;

import io.github.webauthn.domain.WebAuthnCredentialsRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class SpringDataWebAuthnCredentialsRepositoryAdapter implements WebAuthnCredentialsRepository<JpaWebAuthnCredentials> {

    private final WebAuthnCredentialsSpringDataRepository webAuthnCredentialsRepository;


    public SpringDataWebAuthnCredentialsRepositoryAdapter(WebAuthnCredentialsSpringDataRepository webAuthnCredentialsRepository) {
        this.webAuthnCredentialsRepository = webAuthnCredentialsRepository;
    }

    @Override
    public List<JpaWebAuthnCredentials> findAllByAppUserId(Long userId) {
        return webAuthnCredentialsRepository.findAllByAppUserId(userId);
    }

    @Override
    public Optional<JpaWebAuthnCredentials> findByCredentialIdAndAppUserId(byte[] credentialId, Long userId) {
        return webAuthnCredentialsRepository.findByCredentialIdAndAppUserId(credentialId, userId);
    }

    @Override
    public List<JpaWebAuthnCredentials> findByCredentialId(byte[] credentialId) {
        return webAuthnCredentialsRepository.findByCredentialId(credentialId);
    }

    @Override
    public JpaWebAuthnCredentials save(JpaWebAuthnCredentials credentials) {
        return webAuthnCredentialsRepository.save(credentials);
    }

    @Override
    public void deleteByAppUserId(Long appUserId) {
        webAuthnCredentialsRepository.deleteByAppUserId(appUserId);
    }

    @Override
    public void deleteById(Long id) {
        this.webAuthnCredentialsRepository.deleteById(id);
    }

    @Override
    public JpaWebAuthnCredentials save(byte[] credentialId, Long appUserId, Long count, byte[] publicKeyCose, String userAgent) {
        return webAuthnCredentialsRepository.save(new JpaWebAuthnCredentials(credentialId,
                appUserId, count, publicKeyCose, userAgent
        ));
    }

}
