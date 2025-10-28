package fr.vaelix.esportclash.esportclash.auth.application.exception;

import fr.vaelix.esportclash.esportclash.core.domain.exceptions.BadRequestException;

public class EmailAddressUnavailableException extends BadRequestException {
    public EmailAddressUnavailableException() {
        super("Email address in already in use");
    }
}
