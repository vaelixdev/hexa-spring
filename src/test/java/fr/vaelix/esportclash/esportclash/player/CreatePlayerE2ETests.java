package fr.vaelix.esportclash.esportclash.player;

import fr.vaelix.esportclash.esportclash.PostgresSQLTestConfiguration;
import fr.vaelix.esportclash.esportclash.player.application.ports.PlayerRepository;
import fr.vaelix.esportclash.esportclash.player.domain.viewmodel.IdResponse;
import fr.vaelix.esportclash.esportclash.player.infrastructure.spring.CreatePlayerDTO;
import org.junit.Assert;
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
public class CreatePlayerE2ETests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void shouldCreatePlayer() throws Exception {
        var dto = new CreatePlayerDTO("player");

        var result = mockMvc.perform(MockMvcRequestBuilders.post("/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(dto))
        ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        var idResponse = objectMapper.readValue(result.getResponse().getContentAsString(), IdResponse.class);

        var player = playerRepository.findById(idResponse.getId()).get();

        Assert.assertNotNull(player);
        Assert.assertEquals(player.getName(), dto.getName());
    }
}
