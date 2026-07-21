package io.everyonecodes.project_module.entity;

import lombok.Data;

import java.util.List;

@Data
public class Hero {
    private final Long id;
    private String name;
    private Integer strengthLevel;
    private Integer constitutionLevel;
    private Integer speedLevel;
    private List<Long> dungeonIds;
}
