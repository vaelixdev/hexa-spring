package fr.vaelix.esportclash.esportclash.auth.infrastructure.persistence.jpa;

import fr.vaelix.esportclash.esportclash.auth.domain.model.User;
import org.springframework.data.repository.CrudRepository;

public interface SQLUserAccessor extends CrudRepository<User, String> {
    boolean existsByEmailAddress(String emailAddress);
    User findByEmailAddress(String emailAddress);
}
