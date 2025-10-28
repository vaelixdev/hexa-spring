package fr.vaelix.esportclash.esportclash.player;

import fr.vaelix.esportclash.esportclash.IntegrationTests;
import fr.vaelix.esportclash.esportclash.player.application.ports.PlayerRepository;
import fr.vaelix.esportclash.esportclash.player.domain.model.Player;
import fr.vaelix.esportclash.esportclash.player.domain.viewmodel.PlayerViewModel;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class GetPlayerByIdE2ETests extends IntegrationTests {
    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void shouldGetPlayerById() throws Exception {
        var jwt = createJwt();

        var player = new Player("123", "player");
        playerRepository.save(player);

        var result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/players/" + player.getId())
                        .header("Authorization", jwt)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var viewModel = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                PlayerViewModel.class
        );

        Assert.assertEquals(player.getId(), viewModel.getId());
        Assert.assertEquals(player.getName(), viewModel.getName());
    }
}
