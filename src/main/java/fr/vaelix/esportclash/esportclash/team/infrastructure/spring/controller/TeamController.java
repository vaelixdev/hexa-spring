package fr.vaelix.esportclash.esportclash.team.infrastructure.spring.controller;

import an.awesome.pipelinr.Pipeline;
import fr.vaelix.esportclash.esportclash.player.domain.viewmodel.IdResponse;
import fr.vaelix.esportclash.esportclash.team.application.usecases.*;
import fr.vaelix.esportclash.esportclash.team.domain.viewmodel.TeamViewModel;
import fr.vaelix.esportclash.esportclash.team.infrastructure.spring.dto.AddPlayerToTeamDTO;
import fr.vaelix.esportclash.esportclash.team.infrastructure.spring.dto.CreateTeamDTO;
import fr.vaelix.esportclash.esportclash.team.infrastructure.spring.dto.RemovePlayerFromTeamDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams")
@Transactional
public class TeamController {
    private final Pipeline pipeline;

    public TeamController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamViewModel> getTeamById(@PathVariable String id) {
        var result = this.pipeline.send(new GetTeamByIdCommand(id));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<IdResponse> createTeam(@RequestBody CreateTeamDTO dto) {
        var result = this.pipeline.send(new CreateTeamCommand(dto.getName()));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/add-player-to-team")
    public ResponseEntity<Void> addPlayerToTeam(@RequestBody AddPlayerToTeamDTO dto) {
        var result = this.pipeline.send(new AddPlayerToTeamCommand(
                dto.getTeamId(),
                dto.getPlayerId(),
                dto.getRole()
        ));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/remove-player-from-team")
    public ResponseEntity<Void> removePlayerFromTeam(@RequestBody RemovePlayerFromTeamDTO dto) {
        var result = this.pipeline.send(new RemovePlayerFromTeamCommand(
                dto.getTeamId(),
                dto.getPlayerId()
        ));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<IdResponse> deleteTeam(@PathVariable String id) {
        this.pipeline.send(new DeleteTeamCommand(id));
        return ResponseEntity.noContent().build();
    }
}
