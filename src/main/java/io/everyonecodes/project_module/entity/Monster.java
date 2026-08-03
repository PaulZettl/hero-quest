package io.everyonecodes.project_module.entity;

import lombok.Data;

@Data
public class Monster {
    private Long id;
    private String name;
    private Integer strengthLevel;
    private Integer constitutionLevel;
    private Integer speedLevel;
    private int currentHp;

    public int getMaximumHp() {
        if (constitutionLevel == null) {
            return 0;
        }
        return constitutionLevel * 2;
    }
}
