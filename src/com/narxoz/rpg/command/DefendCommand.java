package com.narxoz.rpg.command;

import com.narxoz.rpg.arena.ArenaFighter;

public class DefendCommand implements ActionCommand {
    private final ArenaFighter target;
    private final double dodgeBoost;

    public DefendCommand(ArenaFighter target, double dodgeBoost) {
        this.target = target;
        this.dodgeBoost = dodgeBoost;
    }

    @Override
    public void execute() {
        target.modifyDodgeChance(dodgeBoost);
        System.out.println("[Command] Защита: шанс уворота вырос на " + (dodgeBoost * 100) + "%");
    }

    @Override
    public void undo() {
        target.modifyDodgeChance(-dodgeBoost);
        System.out.println("[Undo] Отмена защиты: бонус уворота убран");
    }

    @Override
    public String getDescription() {
        return "Защита (бонус к увороту: +" + dodgeBoost + ")";
    }
}
