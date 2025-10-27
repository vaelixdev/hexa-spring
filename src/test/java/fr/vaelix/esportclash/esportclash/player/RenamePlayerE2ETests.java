package fr.vaelix.esportclash.esportclash.player;

import fr.vaelix.esportclash.esportclash.PostgresSQLTestConfiguration;
import fr.vaelix.esportclash.esportclash.player.application.ports.PlayerRepository;
import fr.vaelix.esportclash.esportclash.player.domain.model.Player;
import fr.vaelix.esportclash.esportclash.player.infrastructure.spring.RenamePlayerDTO;
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
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Import(PostgresSQLTestConfiguration.class)
@Transactional
public class RenamePlayerE2ETests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void shouldRenamePlayer() throws Exception {
        var existingPlayer = new Player("123", "player");
        playerRepository.save(existingPlayer);

        var dto = new RenamePlayerDTO("new name");

        mockMvc.perform(MockMvcRequestBuilders.patch(
                    "/players/" + existingPlayer.getId() + "/name"
                                )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(dto))
                ).andExpect(MockMvcResultMatchers.status().isOk());

        var player = playerRepository.findById(existingPlayer.getId()).get();

        Assert.assertNotNull(player);
        Assert.assertEquals(player.getName(), dto.getName());
    }

    @Test
    public void whenPlayerDoesNotExist_shouldFail() throws Exception {
        var dto = new RenamePlayerDTO("new name");

        mockMvc.perform(MockMvcRequestBuilders.patch("/players/garbage/name")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(dto))
                ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
