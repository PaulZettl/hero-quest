package io.everyonecodes.project_module.entity;

import lombok.Data;

import java.util.List;

@Data
public class DungeonReport {
    private String heroName;
    private String dungeonName;
    private boolean isHeroVictorious;
    private List<String> combatLines;
}
