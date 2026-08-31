package io.everyonecodes.project_module.entity;

import lombok.Data;

@Data
public class Player {
    private long id;
    private String username;
    private String password;
    private int experiencePoints;
}
