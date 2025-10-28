package fr.vaelix.esportclash.esportclash.auth.infrastructure.persistence.jpa;

import fr.vaelix.esportclash.esportclash.auth.application.ports.UserRepository;
import fr.vaelix.esportclash.esportclash.auth.domain.model.User;
import fr.vaelix.esportclash.esportclash.core.infrastructure.persistence.sql.SQLBaseRepository;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class SQLUserRepository extends SQLBaseRepository<User> implements UserRepository {
    private final SQLUserAccessor accessor;

    public SQLUserRepository(EntityManager em, SQLUserAccessor accessor) {
        super(em);
        this.accessor = accessor;
    }

    @Override
    public boolean isEmailAddressAvailable(String emailAddress) {
        return !accessor.existsByEmailAddress(emailAddress);
    }

    @Override
    public Optional<User> findByEmailAddress(String emailAddress) {
        return Optional.ofNullable(accessor.findByEmailAddress(emailAddress));
    }

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }
}
