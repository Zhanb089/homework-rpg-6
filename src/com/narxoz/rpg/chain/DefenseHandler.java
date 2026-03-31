package com.narxoz.rpg.chain;

import com.narxoz.rpg.arena.ArenaFighter;

public abstract class DefenseHandler {
    private DefenseHandler next;

    public DefenseHandler setNext(DefenseHandler next) {
        this.next = next;
        return next;
    }

    protected DefenseHandler getNext() {
        return next;
    }

    protected void passToNext(int damage, ArenaFighter target) {
        if (this.next != null && damage >= 0) {
            this.next.handle(damage, target);
        }
    }

    public abstract void handle(int incomingDamage, ArenaFighter target);
}
