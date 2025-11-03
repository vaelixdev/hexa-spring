package fr.vaelix.esportclash.esportclash.schedule.domain.model;

import fr.vaelix.esportclash.esportclash.core.domain.model.BaseEntity;

public class Match extends BaseEntity<Match> {
    private String firstId;
    private String secondId;

    public Match(
            String id,
            String firstId,
            String secondId
    ) {
        super(id);
        this.firstId = firstId;
        this.secondId = secondId;
    }

    public boolean hasTeam(String id) {
        return firstId.equals(id) || secondId.equals(id);
    }

    @Override
    public Match deepClone() {
        return null;
    }
}
