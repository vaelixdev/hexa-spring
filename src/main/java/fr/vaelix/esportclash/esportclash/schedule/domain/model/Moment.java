package fr.vaelix.esportclash.esportclash.schedule.domain.model;

public enum Moment {
    MORNING,
    AFTERNOON;

    public static Moment fromString(String moment) {
        return switch (moment) {
            case "MORNING" -> MORNING;
            case "AFTERNOON" -> AFTERNOON;
            default -> MORNING;
        };
    }
}
