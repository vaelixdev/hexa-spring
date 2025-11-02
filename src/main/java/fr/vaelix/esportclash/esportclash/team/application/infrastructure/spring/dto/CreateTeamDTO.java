package fr.vaelix.esportclash.esportclash.team.application.infrastructure.spring.dto;

public class CreateTeamDTO {
    private String name;

    public CreateTeamDTO() {}

    public CreateTeamDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
