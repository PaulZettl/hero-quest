package io.everyonecodes.project_module.entity;

import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
public class Hero {
    private Long id;
    private String name;
    private Integer strengthLevel;
    private Integer constitutionLevel;
    private Integer speedLevel;
//    private List<Long> dungeonIds;
}
