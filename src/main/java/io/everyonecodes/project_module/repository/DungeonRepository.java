package io.everyonecodes.project_module.repository;

import io.everyonecodes.project_module.entity.Dungeon;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DungeonRepository {
    private final JdbcClient jdbcClient;

    public DungeonRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Dungeon> getAll() {
        return jdbcClient.sql("SELECT * FROM dungeon")
                .query(Dungeon.class)
                .list();
    }

    public Optional<Dungeon> getById(Long id) {
        return jdbcClient.sql("SELECT * FROM dungeon WHERE id = :id")
                .param("id", id)
                .query(Dungeon.class)
                .optional();
    }

    public void deleteById(Long id) {
        jdbcClient.sql("DELETE FROM dungeon WHERE id = :id")
                .param("id", id)
                .update();
    }

    public Dungeon create(Dungeon dungeon) {
        return jdbcClient.sql("INSERT INTO dungeon(name, description, difficulty_level, monster_id) VALUES(:name, :description, :difficultyLevel, :monsterId) RETURNING *")
                .param("name", dungeon.getName())
                .param("description", dungeon.getDescription())
                .param("difficultyLevel", dungeon.getDifficultyLevel())
                .param("monsterId", dungeon.getMonsterId())
                .query(Dungeon.class)
                .single();
    }

    public Dungeon update(Dungeon dungeon) {
        return jdbcClient.sql("UPDATE dungeon set name = :name, description = :description, difficulty_level = :difficultyLevel, monster_id = :monsterId WHERE id = :id RETURNING *")
                .param("id", dungeon.getId())
                .param("name", dungeon.getName())
                .param("description", dungeon.getDescription())
                .param("difficultyLevel", dungeon.getDifficultyLevel())
                .param("monsterId", dungeon.getMonsterId())
                .query(Dungeon.class)
                .single();
    }
}
