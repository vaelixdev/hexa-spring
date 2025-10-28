package fr.vaelix.esportclash.esportclash.auth.application.ports;

import fr.vaelix.esportclash.esportclash.auth.domain.model.User;
import fr.vaelix.esportclash.esportclash.core.infrastructure.persistence.BaseRepository;

public interface UserRepository extends BaseRepository<User> {
    boolean isEmailAddressAvailable(String emailAddress);
}