package fr.vaelix.esportclash.esportclash.player;

import fr.vaelix.esportclash.esportclash.IntegrationTests;
import fr.vaelix.esportclash.esportclash.player.application.ports.PlayerRepository;
import fr.vaelix.esportclash.esportclash.player.domain.model.Player;
import fr.vaelix.esportclash.esportclash.player.infrastructure.spring.RenamePlayerDTO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class RenamePlayerE2ETests extends IntegrationTests {
    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void shouldRenamePlayer() throws Exception {
        var existingPlayer = new Player("123", "player");
        playerRepository.save(existingPlayer);

        var dto = new RenamePlayerDTO("new name");

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/players/" + existingPlayer.getId() + "/name")
                        .header("Authorization", createJwt())
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

        mockMvc.perform(MockMvcRequestBuilders
                                .patch("/players/garbage/name")
                                .header("Authorization", createJwt())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(dto))
                ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
