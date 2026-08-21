package io.everyonecodes.project_module.service;

import io.everyonecodes.project_module.entity.Player;
import io.everyonecodes.project_module.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {
    private final PlayerRepository repository;

    public PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }

    public Optional<Long> login(Player player) {
        Optional<Player> playerInRepository = repository.findByUsername(player.getUserName());
        if (playerInRepository.isEmpty() || !playerInRepository.get().getPassword().equals(player.getPassword())) {
            return Optional.empty();
        }
        return Optional.of(playerInRepository.get().getId());
    }

    public Optional<Player> register(Player player) {
        if (repository.existsByUsername(player.getUserName())) {
            return Optional.empty();
        }
        return Optional.of(repository.create(player));
    }
}
