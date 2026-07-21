package io.everyonecodes.project_module.entity;

import lombok.Data;

import java.util.List;

@Data
public class Dungeon {
    private final Integer id;
    private String name;
    private String description;
    private Integer difficultyLevel;
    private Integer monsterId;
    private List<Integer> heroIds;
}
