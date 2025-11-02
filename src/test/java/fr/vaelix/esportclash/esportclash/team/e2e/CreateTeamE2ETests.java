package fr.vaelix.esportclash.esportclash.team.e2e;

import fr.vaelix.esportclash.esportclash.IntegrationTests;
import fr.vaelix.esportclash.esportclash.player.domain.viewmodel.IdResponse;
import fr.vaelix.esportclash.esportclash.team.application.infrastructure.spring.dto.CreateTeamDTO;
import fr.vaelix.esportclash.esportclash.team.application.ports.TeamRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class CreateTeamE2ETests extends IntegrationTests {
    @Autowired
    private TeamRepository teamRepository;

    @Test
    void shouldCreateTeam() throws Exception {
        var dto = new CreateTeamDTO("team");

        var result = mockMvc
                .perform(MockMvcRequestBuilders.post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header("Authorization", createJwt()))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        var idResponse = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                IdResponse.class
        );

        var team = teamRepository.findById(idResponse.getId()).get();

        Assert.assertNotNull(team);
        Assert.assertEquals(dto.getName(), team.getName());
    }
}
