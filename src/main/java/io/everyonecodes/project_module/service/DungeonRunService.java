package io.everyonecodes.project_module.service;

import io.everyonecodes.project_module.entity.Dungeon;
import io.everyonecodes.project_module.entity.DungeonReport;
import io.everyonecodes.project_module.entity.Hero;
import io.everyonecodes.project_module.entity.Monster;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DungeonRunService {

    private final HeroService heroService;
    private final DungeonService dungeonService;
    private final MonsterService monsterService;

    public DungeonRunService(HeroService heroService, DungeonService dungeonService, MonsterService monsterService) {
        this.heroService = heroService;
        this.dungeonService = dungeonService;
        this.monsterService = monsterService;
    }

    public DungeonReport generateReport(Long heroId, Long dungeonId) {
        Hero hero = heroService.getById(heroId).orElseThrow();
        Dungeon dungeon = dungeonService.getById(dungeonId).orElseThrow();
        Monster monster = monsterService.getMonsterById(dungeon.getMonsterId()).orElseThrow();
        List<String> combatLines = new ArrayList<>();
        hero.setCurrentHp(hero.getMaximumHP());
        monster.setCurrentHp(monster.getMaximumHp());
        DungeonReport report = new DungeonReport();
        report.setHeroName(hero.getName());
        report.setDungeonName(dungeon.getName());

        // First round
        combatLines.addAll(writeStatus(hero, monster));
        combatLines.addAll(writeSpeed(hero, monster));

        int roundNumber = 1;

        while (true) {
            combatLines.addAll(writeCombatRound(hero, monster, roundNumber));
            if (hero.getCurrentHp() < 1) {
                combatLines.addAll(writeFinalStatus(hero, monster));
                report.setHeroVictorious(false);
                report.setCombatLines(combatLines);
                return report;
            } else if (monster.getCurrentHp() < 1) {
                combatLines.addAll(writeFinalStatus(hero, monster));
                report.setHeroVictorious(true);
                report.setCombatLines(combatLines);
                return report;
            }
            roundNumber ++;
        }
    }

    public List<String> writeStatus(Hero hero, Monster monster) {
        return List.of("--- Status ---",
                hero.getName() + ": " + hero.getCurrentHp() + " HP",
                monster.getName() + ": " + monster.getCurrentHp() + " HP",
                "");
    }

    public List<String> writeSpeed(Hero hero, Monster monster) {
        if (hero.getSpeedLevel() > monster.getSpeedLevel()) {
            return List.of(hero.getName() + " is able to outspeed " + monster.getName(),
                    "getting to strike first!");
        }
        if (hero.getSpeedLevel().equals(monster.getSpeedLevel())) {
            return List.of("The " + hero.getName() + " keeps pace with the " + monster.getName() + ",",
                    "though the monster feels a step ahead.");
        } else {
            return List.of("The " + monster.getName() + " easily outmaneuvers " + hero.getName() + "!");
        }
    }

    public List<String> writeCombatRound(Hero hero, Monster monster, int roundNumber) {
        List<String> combatLines = new ArrayList<>();
        combatLines.add("--- Round " + roundNumber + " ---");
        if (hero.getSpeedLevel() > monster.getSpeedLevel()) {
            combatLines.addAll(heroStrikesMonster(hero, monster));
            monster.setCurrentHp(monster.getCurrentHp() - hero.getStrengthLevel());
            if (monster.getCurrentHp() > 0) {
                combatLines.addAll(monsterStrikesHero(monster, hero));
                hero.setCurrentHp(hero.getCurrentHp() - monster.getStrengthLevel());
            } else {
                combatLines.add(hero.getName() + " has defeated the " + monster.getName() + "!");
            }
            return combatLines;
        } else {
            combatLines.addAll(monsterStrikesHero(monster, hero));
            hero.setCurrentHp(hero.getCurrentHp() - monster.getStrengthLevel());
            if (hero.getCurrentHp() > 0) {
                combatLines.addAll(heroStrikesMonster(hero, monster));
                monster.setCurrentHp(monster.getCurrentHp() - hero.getStrengthLevel());
            } else {
                combatLines.add("Fearing the end,");
                combatLines.add(hero.getName() + " retreats out of combat!");
            }
        }
        return combatLines;
    }

    public List<String> heroStrikesMonster(Hero hero, Monster monster) {
        List<String> combatLines = new ArrayList<>();
        combatLines.add(hero.getName() + " strikes the " + monster.getName() + ",");
        combatLines.add("dealing " + hero.getStrengthLevel() + " points of damage.");
        return combatLines;
    }

    public List<String> monsterStrikesHero(Monster monster, Hero hero) {
        List<String> combatLines = new ArrayList<>();
        combatLines.add("The " + monster.getName() + " strikes " + hero.getName() + " ,");
        combatLines.add("dealing " + monster.getStrengthLevel() + "points of damage.");
        return combatLines;
    }

    public List<String> writeFinalStatus(Hero hero, Monster monster) {
        List<String> combatLines = new ArrayList<>();
        combatLines.add("--- Final Status ---");
        String lastLine = "Try again with someone else!";

        if (hero.getCurrentHp() < 1) {
            combatLines.add(hero.getName() + ": Fled!");
        } else {
            combatLines.add(hero.getName() + ": " + hero.getCurrentHp() + "HP");
            if (monster.getCurrentHp() < 1) {
                lastLine = "You are victorious. Well done!";
            }
        }

        if (monster.getCurrentHp() < 1) {
            combatLines.add(monster.getName() + ": Defeated!");
        } else {
            combatLines.add(monster.getName() + ": " + monster.getCurrentHp() + " HP");
        }

        combatLines.add(lastLine);
        return combatLines;
    }
}
