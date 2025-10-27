package fr.vaelix.esportclash.esportclash.player.infrastructure.spring;

import an.awesome.pipelinr.Pipeline;
import fr.vaelix.esportclash.esportclash.player.application.usecases.CreatePlayerCommand;
import fr.vaelix.esportclash.esportclash.player.application.usecases.DeletePlayerCommand;
import fr.vaelix.esportclash.esportclash.player.application.usecases.GetPlayerByIdCommand;
import fr.vaelix.esportclash.esportclash.player.application.usecases.RenamePlayerCommand;
import fr.vaelix.esportclash.esportclash.player.domain.viewmodel.IdResponse;
import fr.vaelix.esportclash.esportclash.player.domain.viewmodel.PlayerViewModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/players")
@Transactional
public class PlayerController {
    private final Pipeline pipeline;

    public PlayerController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerViewModel> getPlayerByid(@PathVariable String id) {
        var result = this.pipeline.send(new GetPlayerByIdCommand(id));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<IdResponse> createPlayer(@RequestBody CreatePlayerDTO dto) {
        var result = this.pipeline.send(new CreatePlayerCommand(dto.getName()));
        return new ResponseEntity<>(new IdResponse(result.getId()), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<Void> changePlayerName(
            @RequestBody RenamePlayerDTO dto,
            @PathVariable String id
    ) {
        this.pipeline.send(new RenamePlayerCommand(id, dto.getName()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable String id) {
        this.pipeline.send(new DeletePlayerCommand(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
