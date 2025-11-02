package fr.vaelix.esportclash.esportclash.team.e2e;

import fr.vaelix.esportclash.esportclash.IntegrationTests;
import fr.vaelix.esportclash.esportclash.team.application.ports.TeamRepository;
import fr.vaelix.esportclash.esportclash.team.domain.model.Team;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class DeleteTeamE2ETests extends IntegrationTests {
    @Autowired
    private TeamRepository teamRepository;

    Team team = new Team("123", "team");

    @BeforeEach
    public void setUp() {
        teamRepository.clear();
        teamRepository.save(team);
    }

    @Test
    void shouldDeleteTeam() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/teams/" + team.getId())
                        .header("Authorization", createJwt()))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();

        var teamQuery = teamRepository.findById(team.getId());

        Assert.assertFalse(teamQuery.isPresent());
    }
}
