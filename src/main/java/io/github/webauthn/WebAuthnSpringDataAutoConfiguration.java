package io.github.webauthn;


import io.github.webauthn.domain.WebAuthnCredentialsRepository;
import io.github.webauthn.jpa.WebAuthnCredentialsSpringDataRepository;
import io.github.webauthn.domain.WebAuthnUserRepository;
import io.github.webauthn.jpa.WebAuthnUserSpringDataRepository;
import io.github.webauthn.jpa.SpringDataWebAuthnCredentialsRepositoryAdapter;
import io.github.webauthn.jpa.SpringDataWebAuthnUserRepositoryAdapter;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.Repository;

@Configuration
@ConditionalOnClass(Repository.class)
@EnableJpaRepositories
@EntityScan("io.github.webauthn.jpa")
@AutoConfigureAfter(HibernateJpaAutoConfiguration.class)
@AutoConfigureBefore(WebAuthnInMemoryAutoConfiguration.class)
public class WebAuthnSpringDataAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(WebAuthnUserRepository.class)
    public WebAuthnUserRepository webAuthnUserJpaRepository(WebAuthnUserSpringDataRepository repo) {
        return new SpringDataWebAuthnUserRepositoryAdapter(repo);
    }

    @Bean
    @ConditionalOnMissingBean(WebAuthnCredentialsRepository.class)
    public WebAuthnCredentialsRepository webAuthnCredentialsJpaRepository(WebAuthnCredentialsSpringDataRepository repo) {
        return new SpringDataWebAuthnCredentialsRepositoryAdapter(repo);
    }
}
