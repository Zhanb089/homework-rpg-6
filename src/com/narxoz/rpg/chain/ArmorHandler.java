package com.narxoz.rpg.chain;

import com.narxoz.rpg.arena.ArenaFighter;

public class ArmorHandler extends DefenseHandler {
    private final int armorValue;

    public ArmorHandler(int armorValue) {
        this.armorValue = armorValue;
    }

    @Override
    public void handle(int incomingDamage, ArenaFighter target) {
        int finalDamage = incomingDamage - this.armorValue;
        if (finalDamage < 0) {
            finalDamage = 0;
        }
        System.out.println("[Armor] Броня поглотила " + (incomingDamage - finalDamage) + " ед. урона.");
        
        passToNext(finalDamage, target);
    }
}
