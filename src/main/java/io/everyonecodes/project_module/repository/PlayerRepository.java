package io.everyonecodes.project_module.repository;

import io.everyonecodes.project_module.entity.Player;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PlayerRepository {
    private final JdbcClient jdbcClient;

    public PlayerRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Optional<Player> findByUsername(String username) {
        return jdbcClient.sql("SELECT * FROM player WHERE username = :username")
                .param("username", username)
                .query(Player.class)
                .optional();
    }

    public Player create(Player player) {
        return jdbcClient.sql("INSERT INTO player(username, password) VALUES (:username, :password) RETURNING *")
                .param("username", player.getUsername())
                .param("password", player.getPassword())
                .query(Player.class)
                .single();
    }

    public boolean existsByUsername(String username) {
        return jdbcClient.sql("SELECT EXISTS (SELECT 1 FROM player WHERE LOWER(username) = LOWER(:username))")
                .param("username", username)
                .query((rs, rowNum) -> rs.getBoolean(1)) // Explicitly map JDBC driver's boolean representation
                .single();
    }

    public Optional<Integer> getExperience(String username) {
        return jdbcClient.sql("SELECT experience FROM player WHERE username = :username")
                .param("username", username)
                .query(Integer.class)
                .optional();
    }

    public void markFirstCompletionOfDungeon(Long playerId, Long dungeonId) {
        jdbcClient.sql("INSERT INTO player_dungeon_first_completions(player_id, dungeon_id) VALUES(:playerId, :dungeonId) ON CONFLICT DO NOTHING")
                .param("playerId", playerId)
                .param("dungeonId", dungeonId)
                .update();
    }

    public boolean existsById(Long playerId, Long dungeonId) {
        return jdbcClient.sql("SELECT EXISTS (SELECT 1 FROM player_dungeon_first_completions WHERE player_id = :playerId AND dungeon_id = :dungeonId)")
                .param("playerId", playerId)
                .param("dungeonId", dungeonId)
                .query(Boolean.class)
                .single();
    }

    public void addExperience(Long playerId, int experienceAmountToAdd) {
            jdbcClient.sql("UPDATE player SET experience = experience + :experienceAmountToAdd WHERE id = :playerId")
                    .param("experienceAmountToAdd", experienceAmountToAdd)
                    .param("playerId", playerId)
                    .update();

    }
}
