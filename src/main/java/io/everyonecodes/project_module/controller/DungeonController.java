package io.everyonecodes.project_module.controller;

import io.everyonecodes.project_module.entity.Dungeon;
import io.everyonecodes.project_module.repository.DungeonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class DungeonController {

    private final DungeonRepository repository;

    public DungeonController(DungeonRepository repository) {
        this.repository = repository;
    }

    private ResponseStatusException dungeonNotFound(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Dungeon with ID " + id + " not found");
    }

    @GetMapping("/dungeon")
    public List<Dungeon> getAllDungeons() {
        return repository.getAll();
    }
}
