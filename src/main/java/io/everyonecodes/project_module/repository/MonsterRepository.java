package io.everyonecodes.project_module.repository;

import io.everyonecodes.project_module.entity.Monster;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MonsterRepository {
    private final JdbcClient jdbcClient;

    public MonsterRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Monster> getAll() {
        return jdbcClient.sql("SELECT * FROM monster")
                .query(Monster.class)
                .list();
    }

    public Optional<Monster> getById(int id) {
        return jdbcClient.sql("SELECT * FROM monster WHERE id = :id")
                .param("id", id)
                .query(Monster.class)
                .optional();
    }

    public void deleteById(int id) {
        jdbcClient.sql("DELETE FROM monster WHERE id = :id")
                .param("id", id)
                .update();
    }

    public Monster create(Monster monster) {
        return jdbcClient.sql("INSERT INTO monster(name, strength_level, constitution_level, speed_level) VALUES (:name, :strengthLevel, :constitutionLevel, :speedLevel) RETURNING *")
                .param("name", monster.getName())
                .param("strengthLevel", monster.getStrengthLevel())
                .param("constitutionLevel", monster.getConstitutionLevel())
                .param("speedLevel", monster.getSpeedLevel())
                .query(Monster.class)
                .single();
    }

    public Monster update(Monster monster) {
        return jdbcClient.sql("UPDATE monster set name = :name, strength_level = :strengthLevel, constitution_level = :constitutionLevel, speed_level = :speedLevel WHERE id = :id RETURNING *")
                .param("id", monster.getId())
                .param("name", monster.getName())
                .param("strengthLevel", monster.getStrengthLevel())
                .param("constitutionLevel", monster.getConstitutionLevel())
                .param("speedLevel", monster.getSpeedLevel())
                .query(Monster.class)
                .single();
    }
}
