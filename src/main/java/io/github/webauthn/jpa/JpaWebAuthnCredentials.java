package io.github.webauthn.jpa;

import io.github.webauthn.domain.WebAuthnCredentials;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class JpaWebAuthnCredentials implements WebAuthnCredentials {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private byte[] credentialId;
    private Long appUserId;
    private Long count;
    @Lob
    private byte[] publicKeyCose;
    private String userAgent;

    public JpaWebAuthnCredentials(byte[] credentialId, Long appUserId, Long count, byte[] publicKeyCose, String userAgent) {
        this.credentialId = credentialId;
        this.appUserId = appUserId;
        this.count = count;
        this.publicKeyCose = publicKeyCose;
        this.userAgent = userAgent;
    }

    public JpaWebAuthnCredentials() {

    }

    @Override
    public byte[] getCredentialId() {
        return credentialId;
    }

    @Override
    public void setCredentialId(byte[] credentialId) {
        this.credentialId = credentialId;
    }

    @Override
    public Long getAppUserId() {
        return appUserId;
    }

    @Override
    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    @Override
    public Long getCount() {
        return count;
    }

    @Override
    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public byte[] getPublicKeyCose() {
        return publicKeyCose;
    }

    @Override
    public void setPublicKeyCose(byte[] publicKeyCose) {
        this.publicKeyCose = publicKeyCose;
    }

    @Override
    public String getUserAgent() {
        return userAgent;
    }

    @Override
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JpaWebAuthnCredentials that = (JpaWebAuthnCredentials) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "WebAuthnCredentials{" +
                "id=" + id +
                ", credentialId=" + Arrays.toString(credentialId) +
                ", appUserId=" + appUserId +
                '}';
    }
}
