package io.everyonecodes.project_module.controller;

import io.everyonecodes.project_module.dto.PlayerDto;
import io.everyonecodes.project_module.entity.Player;
import io.everyonecodes.project_module.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class PlayerController {
    private final PlayerService service;

    public PlayerController(PlayerService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public Long login(@RequestBody Player player) {
        return service.login(player)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password"));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerDto register(@RequestBody Player player) {
        Player savedPlayer = service.register(player)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Username is already taken."));

        return new PlayerDto(savedPlayer.getId(), savedPlayer.getUsername());
    }

    @GetMapping("/{username}/experience")
    public int getExperience(@PathVariable String username) {
        return service.getExperience(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found."));
    }
}
