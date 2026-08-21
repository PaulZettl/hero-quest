package io.everyonecodes.project_module.entity;

import lombok.Data;

@Data
public class Hero {
    private Long id;
    private String name;
    private Integer strengthLevel;
    private Integer constitutionLevel;
    private Integer speedLevel;
    private int currentHp;
}
