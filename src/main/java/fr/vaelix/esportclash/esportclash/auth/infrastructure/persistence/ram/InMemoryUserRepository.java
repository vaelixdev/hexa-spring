package fr.vaelix.esportclash.esportclash.auth.infrastructure.persistence.ram;

import fr.vaelix.esportclash.esportclash.auth.application.ports.UserRepository;
import fr.vaelix.esportclash.esportclash.auth.domain.model.User;
import fr.vaelix.esportclash.esportclash.core.infrastructure.persistence.ram.InMemoryBaseRepository;

public class InMemoryUserRepository extends InMemoryBaseRepository<User> implements UserRepository {
    @Override
    public boolean isEmailAddressAvailable(String emailAddress) {
        return entities
                .values()
                .stream()
                .noneMatch(user -> user.getEmailAddress().equals(emailAddress));
    }
}
