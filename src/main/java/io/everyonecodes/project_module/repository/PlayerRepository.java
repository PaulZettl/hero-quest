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
}
