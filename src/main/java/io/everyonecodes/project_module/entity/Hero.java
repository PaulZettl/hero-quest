package io.everyonecodes.project_module.entity;

import lombok.Data;

import java.util.List;

@Data
public class Hero {
    private final Integer id;
    private String name;
    private Integer strengthLevel;
    private Integer constitutionLevel;
    private Integer speedLevel;
    private List<Integer> dungeonIds;
}
