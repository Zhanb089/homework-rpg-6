package com.narxoz.rpg.command;

import com.narxoz.rpg.arena.ArenaOpponent;

public class AttackCommand implements ActionCommand {
    private final ArenaOpponent target;
    private final int attackPower;
    private int damageDealt;

    public AttackCommand(ArenaOpponent target, int attackPower) {
        this.target = target;
        this.attackPower = attackPower;
    }

    @Override
    public void execute() {
        int hpBefore = target.getHealth();
        target.takeDamage(attackPower);
        this.damageDealt = hpBefore - target.getHealth();
    }

    @Override
    public void undo() {
        target.restoreHealth(this.damageDealt);
    }

    @Override
    public String getDescription() {
        return "Атака (сила: " + attackPower + ")";
    }
}
