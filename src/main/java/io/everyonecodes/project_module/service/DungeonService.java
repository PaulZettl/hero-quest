package io.everyonecodes.project_module.service;

import io.everyonecodes.project_module.entity.Dungeon;
import io.everyonecodes.project_module.repository.DungeonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DungeonService {
    private final DungeonRepository repository;

    public DungeonService(DungeonRepository repository) {
        this.repository = repository;
    }

    public List<Dungeon> getAll() {
        return repository.getAll();
    }
}
