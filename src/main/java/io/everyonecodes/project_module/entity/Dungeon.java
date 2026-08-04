package io.everyonecodes.project_module.entity;

import lombok.Data;

@Data
public class Dungeon {
    private Long id;
    private String name;
    private String description;
    private Integer difficultyLevel;
    private Long monsterId;
}
