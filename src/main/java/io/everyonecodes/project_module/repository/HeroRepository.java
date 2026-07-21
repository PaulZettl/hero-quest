package io.everyonecodes.project_module.repository;

import io.everyonecodes.project_module.entity.Hero;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class HeroRepository {
    private final JdbcClient jdbcClient;

    public HeroRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Hero> findAll() {
        return jdbcClient.sql("SELECT * FROM hero")
                .query(Hero.class)
                .list();
    }

    public Optional<Hero> findById(int id) {
        return jdbcClient.sql("SELECT * FROM hero WHERE id = :id")
                .param("id", id)
                .query(Hero.class)
                .optional();
    }

    public void deleteById(int id) {
        jdbcClient.sql("DELETE FROM hero WHERE id = :id")
                .param("id", id)
                .update();
    }

    public Hero create(Hero hero) {
        return jdbcClient.sql("INSERT INTO hero(name, strength_level, constitution_level, speed_level) VALUES (:name, :strengthLevel, :constitutionLevel, :speedLevel) RETURNING *")
                .param("name", hero.getName())
                .param("strengthLevel", hero.getStrengthLevel())
                .param("constitutionLevel", hero.getConstitutionLevel())
                .param("speedLevel", hero.getSpeedLevel())
                .query(Hero.class)
                .single();
    }
    public Hero update(Hero hero) {
        return jdbcClient.sql("UPDATE hero set name = :name, strength_level = :strengthLevel, constitution_level = :constitutionLevel, speed_level = :speedLevel WHERE id = :id RETURNING *")
                .param("id", hero.getId())
                .param("name", hero.getName())
                .param("strengthLevel", hero.getStrengthLevel())
                .param("constitutionLevel", hero.getConstitutionLevel())
                .param("speedLevel", hero.getSpeedLevel())
                .query(Hero.class)
                .single();
    }
}
