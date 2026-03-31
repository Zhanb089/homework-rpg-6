package com.narxoz.rpg;

import com.narxoz.rpg.arena.ArenaFighter;
import com.narxoz.rpg.arena.ArenaOpponent;
import com.narxoz.rpg.arena.TournamentResult;
import com.narxoz.rpg.chain.ArmorHandler;
import com.narxoz.rpg.chain.BlockHandler;
import com.narxoz.rpg.chain.DefenseHandler;
import com.narxoz.rpg.chain.DodgeHandler;
import com.narxoz.rpg.chain.HpHandler;
import com.narxoz.rpg.command.ActionQueue;
import com.narxoz.rpg.command.AttackCommand;
import com.narxoz.rpg.command.DefendCommand;
import com.narxoz.rpg.command.HealCommand;
import com.narxoz.rpg.tournament.TournamentEngine;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Демонстрация HW 6: Команды и Цепочка защиты ===");

        ArenaFighter hero = new ArenaFighter("Ерлан", 100, 0.20, 25, 5, 18, 3);
        ArenaOpponent opponent = new ArenaOpponent("Босс", 90, 15);

        System.out.println("--- 1. Тест очереди команд ---");
        ActionQueue queue = new ActionQueue();

        queue.enqueue(new AttackCommand(opponent, hero.getAttackPower()));
        queue.enqueue(new HealCommand(hero, 20));
        queue.enqueue(new DefendCommand(hero, 0.15));

        System.out.println("Запланированные действия:");
        for (String desc : queue.getCommandDescriptions()) {
            System.out.println("  -> " + desc);
        }

        System.out.println("[Undo] Отменяем последнее действие...");
        queue.undoLast();

        System.out.println("Очередь после отмены:");
        for (String desc : queue.getCommandDescriptions()) {
            System.out.println("  -> " + desc);
        }

        System.out.println("Выполняем команды из очереди:");
        queue.executeAll();

        System.out.println("--- 2. Тест цепочки защиты ---");
        
        DefenseHandler dodge = new DodgeHandler(0.40, 77L);
        DefenseHandler block = new BlockHandler(0.30);
        DefenseHandler armor = new ArmorHandler(5);
        DefenseHandler hp = new HpHandler();
        
        dodge.setNext(block).setNext(armor).setNext(hp);

        System.out.println("Герой получает удар на 30 урона...");
        System.out.println("HP до удара: " + hero.getHealth());
        
        dodge.handle(30, hero);
        
        System.out.println("HP после удара: " + hero.getHealth());


        System.out.println("--- 3. Полный турнир в движке ---");
        
        
        ArenaFighter fighter = new ArenaFighter("Герой", 120, 0.15, 20, 8, 20, 2);
        ArenaOpponent boss = new ArenaOpponent("Босс", 100, 45);

        TournamentEngine engine = new TournamentEngine(fighter, boss);
        TournamentResult result = engine.runTournament();

        System.out.println("ИТОГИ БИТВЫ:");
        System.out.println("Победитель: " + result.getWinner());
        System.out.println("Всего раундов: " + result.getRounds());
        
        System.out.println("Лог сражения:");
        for (String line : result.getLog()) {
            System.out.println("  " + line);
        }

        System.out.println("=== Демонстрация завершена ===");
    }
}
