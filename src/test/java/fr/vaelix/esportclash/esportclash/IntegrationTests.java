package fr.vaelix.esportclash.esportclash;

import fr.vaelix.esportclash.esportclash.auth.application.ports.UserRepository;
import fr.vaelix.esportclash.esportclash.auth.application.services.jwtservice.JwtService;
import fr.vaelix.esportclash.esportclash.auth.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Import(PostgresSQLTestConfiguration.class)
@Transactional
public class IntegrationTests {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected JwtService jwtService;

    protected String createJwt() {
        var user = userRepository.findByEmailAddress("contact@example.fr").orElse(null);
        if (user == null) {
            user = new User("123", "contact@example.fr", "");
            userRepository.save(user);
        }
        return "Bearer " + jwtService.tokenize(user);
    }
}
