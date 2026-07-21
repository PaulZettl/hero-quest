package io.everyonecodes.project_module.entity;

import lombok.Data;

import java.util.List;

@Data
public class Dungeon {
    private final Long id;
    private String name;
    private String description;
    private Integer difficultyLevel;
    private Long monsterId;
    private List<Long> heroIds;
}
