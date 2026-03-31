package com.narxoz.rpg.chain;

import com.narxoz.rpg.arena.ArenaFighter;

public class HpHandler extends DefenseHandler {

    @Override
    public void handle(int incomingDamage, ArenaFighter target) {
        System.out.println("[HP] Урон нанесен по здоровью: " + incomingDamage);
        target.takeDamage(incomingDamage);
    }
}
