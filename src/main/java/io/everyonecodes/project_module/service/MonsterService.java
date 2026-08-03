package io.everyonecodes.project_module.service;

import io.everyonecodes.project_module.entity.Monster;
import io.everyonecodes.project_module.repository.MonsterRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MonsterService {
    private final MonsterRepository repository;

    public MonsterService(MonsterRepository repository) {
        this.repository = repository;
    }

    public Optional<Monster> getMonsterById(Long id) {
        return repository.getById(id);
    }
}
