package io.everyonecodes.project_module.entity;

import lombok.Data;

import java.util.Set;

@Data
public class Player {
    private long id;
    private String userName;
    private String password;
    private Set<Long> heroIds;
}
