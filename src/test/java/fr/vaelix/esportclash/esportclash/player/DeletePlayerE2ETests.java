package fr.vaelix.esportclash.esportclash.player;

import fr.vaelix.esportclash.esportclash.IntegrationTests;
import fr.vaelix.esportclash.esportclash.player.application.ports.PlayerRepository;
import fr.vaelix.esportclash.esportclash.player.domain.model.Player;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class DeletePlayerE2ETests extends IntegrationTests {
    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void shouldDeletePlayer() throws Exception {
        var existingPlayer = new Player("123", "player");
        playerRepository.save(existingPlayer);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/players/" + existingPlayer.getId())
                .header("Authorization", createJwt())
        ).andExpect(MockMvcResultMatchers.status().isNoContent());

        var playerQuery = playerRepository.findById(existingPlayer.getId());
        Assert.assertTrue(playerQuery.isEmpty());
    }

    @Test
    public void whenPlayerDoesNotExist_shouldFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/players/garbage")
                .header("Authorization", createJwt())
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
