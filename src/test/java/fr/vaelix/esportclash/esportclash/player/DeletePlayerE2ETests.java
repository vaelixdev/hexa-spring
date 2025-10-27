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
public class DeletePlayerE2ETests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void shouldDeletePlayer() throws Exception {
        var existingPlayer = new Player("123", "player");
        playerRepository.save(existingPlayer);

        mockMvc.perform(MockMvcRequestBuilders.delete(
                                "/players/" + existingPlayer.getId()
                        )
        ).andExpect(MockMvcResultMatchers.status().isNoContent());

        var playerQuery = playerRepository.findById(existingPlayer.getId());
        Assert.assertTrue(playerQuery.isEmpty());
    }

    @Test
    public void whenPlayerDoesNotExist_shouldFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/players/garbage")
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
