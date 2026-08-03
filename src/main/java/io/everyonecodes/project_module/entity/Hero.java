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
//    private List<Long> dungeonIds;

    public int getMaximumHP() {
        if (constitutionLevel == null) {
            return 0;
        }
        return constitutionLevel * 2;
    }
}
