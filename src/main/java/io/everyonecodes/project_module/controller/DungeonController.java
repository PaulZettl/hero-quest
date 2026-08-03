package io.everyonecodes.project_module.controller;

import io.everyonecodes.project_module.entity.Dungeon;
import io.everyonecodes.project_module.service.DungeonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DungeonController {

    private final DungeonService service;

    public DungeonController(DungeonService service) {
        this.service = service;
    }

    @GetMapping("/dungeon")
    public List<Dungeon> getAllDungeons() {
        return service.getAll();
    }
}
