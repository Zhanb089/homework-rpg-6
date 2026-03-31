package com.narxoz.rpg.command;

import com.narxoz.rpg.arena.ArenaFighter;

public class HealCommand implements ActionCommand {
    private final ArenaFighter target;
    private final int healAmount;
    private int actualHealApplied;

    public HealCommand(ArenaFighter target, int healAmount) {
        this.target = target;
        this.healAmount = healAmount;
    }

    @Override
    public void execute() {
        int hpBefore = target.getHealth();
        target.heal(healAmount);
        this.actualHealApplied = target.getHealth() - hpBefore;
        System.out.println("[Command] Полечились на " + actualHealApplied + " HP");
    }

    @Override
    public void undo() {
        target.takeDamage(actualHealApplied);
        System.out.println("[Undo] Отмена лечения: убрали " + actualHealApplied + " HP");
    }

    @Override
    public String getDescription() {
        return "Лечение на " + healAmount + " HP";
    }
}
