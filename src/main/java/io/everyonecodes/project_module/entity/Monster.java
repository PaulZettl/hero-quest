package io.everyonecodes.project_module.entity;

import lombok.Data;

@Data
public class Monster {
    private final Integer id;
    private String name;
    private Integer strengthLevel;
    private Integer constitutionLevel;
    private Integer speedLevel;
}
