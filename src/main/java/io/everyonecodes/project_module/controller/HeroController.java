package io.everyonecodes.project_module.controller;

import io.everyonecodes.project_module.entity.Hero;
import io.everyonecodes.project_module.repository.HeroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class HeroController {

    private final HeroRepository repository;

    public HeroController(HeroRepository repository) {
        this.repository = repository;
    }

    private ResponseStatusException heroNotFound(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Hero with ID " + id + " not found");
    }

    @GetMapping("/hero")
    public List<Hero> getAllHeroes() {
        return repository.getAll();
    }

    @GetMapping("/hero/{id}")
    public Hero getHeroById(@PathVariable Long id) {
        return repository.getById(id).orElseThrow(() -> heroNotFound(id));
    }

    @DeleteMapping("/hero/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHero(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PostMapping("/hero")
    @ResponseStatus(HttpStatus.CREATED)
    public Hero createHero(@RequestBody Hero hero) {
        return repository.create(hero);
    }

    @PutMapping("/hero/{id}")
    public Hero updateHero(@PathVariable Long id, @RequestBody Hero hero) {
        hero.setId(id);
        return repository.update(hero).orElseThrow(() -> heroNotFound(id));
    }

    @GetMapping("/hero/{id}/completedDungeons")
    public List<Long> getCompletedDungeonIds(@PathVariable Long id) {
        repository.getById(id).orElseThrow(() -> heroNotFound(id));
        return repository.getAllCompletedDungeonIdsByHeroId(id);
    }
}
