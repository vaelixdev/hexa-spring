package fr.vaelix.esportclash.esportclash.player;

import fr.vaelix.esportclash.esportclash.PostgresSQLTestConfiguration;
import fr.vaelix.esportclash.esportclash.player.application.ports.PlayerRepository;
import fr.vaelix.esportclash.esportclash.player.domain.model.Player;
import fr.vaelix.esportclash.esportclash.player.domain.viewmodel.IdResponse;
import fr.vaelix.esportclash.esportclash.player.domain.viewmodel.PlayerViewModel;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Import(PostgresSQLTestConfiguration.class)
@Transactional
public class GetPlayerByIdE2ETests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void shouldGetPlayerById() throws Exception {
        var player = new Player("123", "player");
        playerRepository.save(player);

        var result = mockMvc.perform(MockMvcRequestBuilders.get("/players/" + player.getId())
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
