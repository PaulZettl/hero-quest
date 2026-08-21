package io.everyonecodes.project_module.service;

import io.everyonecodes.project_module.entity.Player;
import io.everyonecodes.project_module.repository.PlayerRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {
    private final PlayerRepository repository;

    public PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }

    public Optional<Long> login(Player player) {
        Optional<Player> playerInRepository = repository.findByUsername(player.getUsername());
        if (playerInRepository.isEmpty() || !BCrypt.checkpw(player.getPassword(), playerInRepository.get().getPassword())) {
            return Optional.empty();
        }
        return Optional.of(playerInRepository.get().getId());
    }

    public Optional<Player> register(Player player) {
        if (repository.existsByUsername(player.getUsername())) {
            return Optional.empty();
        }
        String hashedPassword = BCrypt.hashpw(player.getPassword(), BCrypt.gensalt());
        player.setPassword(hashedPassword);
        return Optional.of(repository.create(player));
    }
}
