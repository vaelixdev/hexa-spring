package fr.vaelix.esportclash.esportclash.core.infrastructure.spring;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Notification;
import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Pipelinr;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PipelineConfiguration {

    @Bean
    Pipeline pipeline(
            ObjectProvider<Command.Handler> commandsHandlers,
            ObjectProvider<Command.Middleware> middlewares,
            ObjectProvider<Notification.Handler> notificationHandlers
    ) {
        return new Pipelinr()
                .with(() -> commandsHandlers.orderedStream())
                .with(() -> middlewares.orderedStream())
                .with(() -> notificationHandlers.orderedStream());
    }
}
