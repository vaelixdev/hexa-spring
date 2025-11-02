package fr.vaelix.esportclash.esportclash.auth.infrastructure.spring;

import an.awesome.pipelinr.Pipeline;
import fr.vaelix.esportclash.esportclash.auth.application.usecases.LoginCommand;
import fr.vaelix.esportclash.esportclash.auth.application.usecases.RegisterCommand;
import fr.vaelix.esportclash.esportclash.auth.domain.viewmodel.LoggedInUserViewModel;
import fr.vaelix.esportclash.esportclash.player.domain.viewmodel.IdResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Transactional
public class AuthController {
    private final Pipeline pipeline;

    public AuthController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @PostMapping("/register")
    public ResponseEntity<IdResponse> register(
            @Valid @RequestBody RegisterDTO dto
    ) {
        return new ResponseEntity<>(pipeline.send(new RegisterCommand(
                dto.getEmailAddress(),
                dto.getPassword()
        )), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoggedInUserViewModel> register(
            @Valid @RequestBody LoginDTO dto
    ) {
        return ResponseEntity.ok(pipeline.send(new LoginCommand(
                dto.getEmailAddress(),
                dto.getPassword()
        )));
    }
}
