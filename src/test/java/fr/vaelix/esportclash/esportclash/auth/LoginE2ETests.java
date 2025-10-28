package fr.vaelix.esportclash.esportclash.auth;

import fr.vaelix.esportclash.esportclash.PostgresSQLTestConfiguration;
import fr.vaelix.esportclash.esportclash.auth.application.ports.UserRepository;
import fr.vaelix.esportclash.esportclash.auth.application.services.passwordhasher.PasswordHasher;
import fr.vaelix.esportclash.esportclash.auth.domain.model.User;
import fr.vaelix.esportclash.esportclash.auth.domain.viewmodel.LoggedInUserViewModel;
import fr.vaelix.esportclash.esportclash.auth.infrastructure.spring.LoginDTO;
import fr.vaelix.esportclash.esportclash.auth.infrastructure.spring.RegisterDTO;
import fr.vaelix.esportclash.esportclash.player.domain.viewmodel.IdResponse;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Import(PostgresSQLTestConfiguration.class)
public class LoginE2ETests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordHasher passwordHasher;

    @BeforeEach
    public void setup() {
        userRepository.clear();
        var user = new User(
                "123",
                "contact@example.fr",
                passwordHasher.hash("azerty")

        );
        userRepository.save(user);
    }

    @Test
    public void shouldLogTheUserIn() throws Exception {
        var dto = new LoginDTO("contact@example.fr", "azerty");

        var result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(dto))
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var loggedInUser = objectMapper.readValue(result.getResponse().getContentAsString(), LoggedInUserViewModel.class);

        Assert.assertEquals("123", loggedInUser.getId());
        Assert.assertEquals("contact@example.fr", loggedInUser.getEmailAddress());
    }
}
