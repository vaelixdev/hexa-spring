package fr.vaelix.esportclash.esportclash.player.infrastructure.spring;

import fr.vaelix.esportclash.esportclash.player.application.ports.PlayerRepository;
import fr.vaelix.esportclash.esportclash.player.application.usecases.CreatePlayerCommandHandler;
import fr.vaelix.esportclash.esportclash.player.application.usecases.DeletePlayerCommandHandler;
import fr.vaelix.esportclash.esportclash.player.application.usecases.GetPlayerByIdCommandHandler;
import fr.vaelix.esportclash.esportclash.player.application.usecases.RenamePlayerCommandHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayerUseCaseConfiguration {
    @Bean
    public CreatePlayerCommandHandler createPlayerUseCase(PlayerRepository playerRepository) {
        return new CreatePlayerCommandHandler(playerRepository);
    }

    @Bean
    public RenamePlayerCommandHandler renamePlayerUseCase(PlayerRepository playerRepository) {
        return new RenamePlayerCommandHandler(playerRepository);
    }

    @Bean
    public DeletePlayerCommandHandler deletePlayerUseCase(PlayerRepository playerRepository) {
        return new DeletePlayerCommandHandler(playerRepository);
    }

    @Bean
    public GetPlayerByIdCommandHandler getPlayerByIdUseCase(PlayerRepository playerRepository) {
        return new GetPlayerByIdCommandHandler(playerRepository);
    }
}
