package io.everyonecodes.project_module.service;

import io.everyonecodes.project_module.entity.Player;
import io.everyonecodes.project_module.repository.PlayerRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PlayerService {
    private final PlayerRepository repository;

    @Value("${game.experience.amount-added}")
    private int experienceGain;

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

    public Optional<Integer> getExperience(String username) {
        return repository.getExperience(username);
    }

    public Boolean checkFirstCompletion(Long playerId, Long heroId) {
        return repository.existsById(playerId, heroId);
    }

    @Transactional
    public void markFirstCompletionOfDungeon(Long playerId, Long dungeonId) {
        repository.markFirstCompletionOfDungeon(playerId, dungeonId);
        repository.addExperience(playerId, experienceGain);
    }
}
