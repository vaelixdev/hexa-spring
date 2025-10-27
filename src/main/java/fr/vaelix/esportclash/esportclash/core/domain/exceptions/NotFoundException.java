package fr.vaelix.esportclash.esportclash.core.domain.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String entity, String key) {
        super(String.format("%s with the key %s was not found", entity, key));
    }
}
