package io.github.webauthn.jpa;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.yubico.webauthn.data.ByteArray;
import io.github.webauthn.BytesUtil;
import io.github.webauthn.WebAuthnSpringDataAutoConfiguration;
import io.github.webauthn.domain.WebAuthnCredentialsRepository;
import io.github.webauthn.domain.WebAuthnUserRepository;
import io.github.webauthn.dto.RegistrationStartRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        //classes = {SpringMvcTestConfig.class, JsonConfig.class, WebAuthnSpringDataAutoConfiguration.class},
        properties = {
                "webauthn.relyingPartyId=localhost",
                "webauthn.relyingPartyName=localhost",
                "webauthn.registrationNewUsers.enabled=true",
                "webauthn.relyingPartyOrigins=http://localhost:8080"
        })
@AutoConfigureMockMvc
@Transactional
public class WebAuthnRegistrationStartStrategyTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    WebAuthnUserRepository<JpaWebAuthnUser> webAuthnUserRepository;
    @Autowired
    WebAuthnCredentialsRepository<JpaWebAuthnCredentials> credentialsRepository;

    @Test
    public void testNewUser() throws Exception {

        RegistrationStartRequest request = new RegistrationStartRequest();
        request.setUsername("newjunit");
        this.mockMvc.perform(
                        post("/registration/start")
                                .accept(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertTrue(webAuthnUserRepository.findByUsername("newjunit").isPresent());
    }

    @Test
    public void testAddDevice() throws Exception {
        byte[] bytes = "token-123".getBytes();
        String registrationAddToken = Base64.getEncoder().encodeToString(bytes);

        JpaWebAuthnUser user = new JpaWebAuthnUser();
        user.setUsername("junit");
        user.setAddToken(bytes);
        user.setRegistrationAddStart(LocalDateTime.now().minusMinutes(1));
        webAuthnUserRepository.save(user);

        RegistrationStartRequest request = new RegistrationStartRequest();
        request.setRegistrationAddToken(registrationAddToken);
        this.mockMvc.perform(
                        post("/registration/start")
                                .accept(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.registrationId").exists())
                .andExpect(jsonPath("$.publicKeyCredentialCreationOptions.rp.id").value("localhost"))
                .andExpect(jsonPath("$.publicKeyCredentialCreationOptions.user.name").value(user.getUsername()))
                .andExpect(jsonPath("$.publicKeyCredentialCreationOptions.user.id").exists());

        assertArrayEquals(bytes, webAuthnUserRepository.findById(user.getId()).get().getAddToken());
    }

    @Test
    public void testRecoveryToken() throws Exception {
        byte[] bytes = "token-123".getBytes();
        String token = Base64.getEncoder().encodeToString(bytes);

        JpaWebAuthnUser user = new JpaWebAuthnUser();
        user.setUsername("junit");
        user.setRecoveryToken(bytes);
        webAuthnUserRepository.save(user);

        RegistrationStartRequest request = new RegistrationStartRequest();
        request.setRecoveryToken(token);
        this.mockMvc.perform(
                        post("/registration/start")
                                .accept(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.registrationId").exists())
                .andExpect(jsonPath("$.publicKeyCredentialCreationOptions.rp.id").value("localhost"))
                .andExpect(jsonPath("$.publicKeyCredentialCreationOptions.user.name").value(user.getUsername()))
                .andExpect(jsonPath("$.publicKeyCredentialCreationOptions.user.id").value(new ByteArray(BytesUtil.longToBytes(user.getId())).getBase64Url()))
        ;
    }
}
