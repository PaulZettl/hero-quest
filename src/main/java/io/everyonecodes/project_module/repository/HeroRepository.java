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

    public List<Hero> getAll() {
        return jdbcClient.sql("SELECT * FROM hero")
                .query(Hero.class)
                .list();
    }

    public List<Hero> getAllByPlayerId(Long playerId) {
        return jdbcClient.sql("SELECT * FROM hero WHERE player_id = :playerId")
                .param("playerId", playerId)
                .query(Hero.class)
                .list();
    }

    public Optional<Hero> getById(Long id) {
        return jdbcClient.sql("SELECT * FROM hero WHERE id = :id")
                .param("id", id)
                .query(Hero.class)
                .optional();
    }

    public void deleteById(Long id) {
        jdbcClient.sql("DELETE FROM hero WHERE id = :id")
                .param("id", id)
                .update();
    }

    public Hero create(Hero hero) {
        return jdbcClient.sql("INSERT INTO hero(name, strength_level, constitution_level, speed_level, player_id) VALUES (:name, :strengthLevel, :constitutionLevel, :speedLevel, :playerId) RETURNING *")
                .param("name", hero.getName())
                .param("strengthLevel", hero.getStrengthLevel())
                .param("constitutionLevel", hero.getConstitutionLevel())
                .param("speedLevel", hero.getSpeedLevel())
                .param("playerId", hero.getPlayerId())
                .query(Hero.class)
                .single();
    }

    public Optional<Hero> update(Hero hero) {
        return jdbcClient.sql("UPDATE hero set name = :name, strength_level = :strengthLevel, constitution_level = :constitutionLevel, speed_level = :speedLevel WHERE id = :id RETURNING *")
                .param("id", hero.getId())
                .param("name", hero.getName())
                .param("strengthLevel", hero.getStrengthLevel())
                .param("constitutionLevel", hero.getConstitutionLevel())
                .param("speedLevel", hero.getSpeedLevel())
                .query(Hero.class)
                .optional();
    }

    public List<Long> getAllCompletedDungeonIdsByHeroId(Long id) {
        return jdbcClient.sql("SELECT dungeon_id FROM hero_dungeon_completed WHERE hero_id = :id")
                .param("id", id)
                .query(Long.class)
                .list();
    }

    public void markDungeonAsComplete(Long heroId, Long dungeonId) {
        jdbcClient.sql("INSERT INTO hero_dungeon_completed(hero_id, dungeon_id) VALUES(:heroId, :dungeonId) ON CONFLICT DO NOTHING")
                .param("heroId", heroId)
                .param("dungeonId", dungeonId)
                .update();
    }

    public boolean existsByName(String name) {
        return jdbcClient.sql("SELECT EXISTS (SELECT 1 FROM hero WHERE name = :name)")
                .param("name", name)
                .query(Boolean.class)
                .single();
    }
}
