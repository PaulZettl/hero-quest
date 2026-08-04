package io.everyonecodes.project_module.controller;

import io.everyonecodes.project_module.entity.Dungeon;
import io.everyonecodes.project_module.entity.DungeonReport;
import io.everyonecodes.project_module.service.DungeonRunService;
import io.everyonecodes.project_module.service.DungeonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DungeonController {

    private final DungeonService service;
    private final DungeonRunService dungeonRunService;

    public DungeonController(DungeonService service, DungeonRunService dungeonRunService) {
        this.service = service;
        this.dungeonRunService = dungeonRunService;
    }

    @GetMapping("/dungeon")
    public List<Dungeon> getAllDungeons() {
        return service.getAll();
    }

    @PostMapping("/dungeon/{dungeonId}/{heroId}")
    public DungeonReport getDungeonReport(@PathVariable Long dungeonId, @PathVariable Long heroId) {
        return dungeonRunService.generateReport(heroId, dungeonId);
    }
}
