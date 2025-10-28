package fr.vaelix.esportclash.esportclash.auth;

import fr.vaelix.esportclash.esportclash.PostgresSQLTestConfiguration;
import fr.vaelix.esportclash.esportclash.auth.application.ports.UserRepository;
import fr.vaelix.esportclash.esportclash.auth.domain.model.User;
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
public class RegisterE2ETests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        userRepository.clear();
    }

    @Test
    public void shouldCreateUser() throws Exception {
        var dto = new RegisterDTO("contact@example.fr", "azerty");

        var result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(dto))
                ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        var idResponse = objectMapper.readValue(result.getResponse().getContentAsString(), IdResponse.class);

        var user = userRepository.findById(idResponse.getId()).get();

        Assert.assertNotNull(user);
        Assert.assertEquals(user.getEmailAddress(), dto.getEmailAddress());
    }

    @Test
    public void whenEmailAddressIsUnavailaible_shouldThrow() throws Exception {
        var existingUser = new User(
                "123",
                "contact@example.fr",
                "azerty"
        );
        userRepository.save(existingUser);

        var dto = new RegisterDTO(existingUser.getEmailAddress(), existingUser.getPasswordHash());

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(dto))
                ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
