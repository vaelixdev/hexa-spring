package fr.vaelix.esportclash.esportclash.player.infrastructure.spring;

public class RenamePlayerDTO {
    private String name;

    public RenamePlayerDTO() {}

    public RenamePlayerDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
