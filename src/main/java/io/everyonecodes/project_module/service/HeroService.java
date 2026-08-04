package io.everyonecodes.project_module.service;

import io.everyonecodes.project_module.entity.Hero;
import io.everyonecodes.project_module.repository.HeroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HeroService {
    private final HeroRepository repository;

    public HeroService(HeroRepository repository) {
        this.repository = repository;
    }

    public List<Hero> getAll() {
        return repository.getAll();
    }

    public Optional<Hero> getById(Long id) {
        return repository.getById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Hero create(Hero hero) {
        return repository.create(hero);
    }

    public Optional<Hero> update(Hero hero) {
        return repository.update(hero);
    }

    public List<Long> getAllCompletedDungeonIdsByHeroId(Long id) {
        return repository.getAllCompletedDungeonIdsByHeroId(id);
    }

    public void markDungeonAsComplete(Long heroId, Long dungeonId) {
        repository.markDungeonAsComplete(heroId, dungeonId);
    }
}
