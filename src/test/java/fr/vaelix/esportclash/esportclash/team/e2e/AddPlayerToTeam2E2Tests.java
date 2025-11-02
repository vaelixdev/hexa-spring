package fr.vaelix.esportclash.esportclash.team.e2e;

import fr.vaelix.esportclash.esportclash.IntegrationTests;
import fr.vaelix.esportclash.esportclash.player.application.ports.PlayerRepository;
import fr.vaelix.esportclash.esportclash.player.domain.model.Player;
import fr.vaelix.esportclash.esportclash.player.domain.viewmodel.IdResponse;
import fr.vaelix.esportclash.esportclash.team.application.ports.TeamRepository;
import fr.vaelix.esportclash.esportclash.team.domain.model.Role;
import fr.vaelix.esportclash.esportclash.team.domain.model.Team;
import fr.vaelix.esportclash.esportclash.team.infrastructure.spring.dto.AddPlayerToTeamDTO;
import fr.vaelix.esportclash.esportclash.team.infrastructure.spring.dto.CreateTeamDTO;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class AddPlayerToTeam2E2Tests extends IntegrationTests {
    Team team;
    Player player;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    public void setUp() {
        teamRepository.clear();
        playerRepository.clear();

        team = new Team("123", "team");
        player = new Player("123", "player");

        teamRepository.save(team);
        playerRepository.save(player);
    }

    @Test
    void shouldAddPlayerToTeam() throws Exception {
        var dto = new AddPlayerToTeamDTO(
                player.getId(),
                team.getId(),
                "TOP"
        );

       mockMvc
                .perform(MockMvcRequestBuilders.post("/teams/add-player-to-team")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header("Authorization", createJwt()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var updatedTeam = teamRepository.findById(team.getId()).get();

        Assert.assertTrue(updatedTeam.hasMember(player.getId(), Role.TOP));
    }
}
