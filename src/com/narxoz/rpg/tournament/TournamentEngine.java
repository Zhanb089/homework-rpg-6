package com.narxoz.rpg.tournament;

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

public class TournamentEngine {
    private final ArenaFighter hero;
    private final ArenaOpponent opponent;

    public TournamentEngine(ArenaFighter hero, ArenaOpponent opponent) {
        this.hero = hero;
        this.opponent = opponent;
    }

    public TournamentResult runTournament() {
        TournamentResult result = new TournamentResult();
        int round = 0;

        // Собираем цепочку защиты (Chain of Responsibility)
        DefenseHandler defenseChain = new DodgeHandler(hero.getDodgeChance(), 123L);
        defenseChain.setNext(new BlockHandler(hero.getBlockRating() / 100.0)) // 100.0 для double!
                    .setNext(new ArmorHandler(hero.getArmorValue()))
                    .setNext(new HpHandler());

        ActionQueue actionQueue = new ActionQueue();

        // Цикл боя
        while (hero.isAlive() && opponent.isAlive() && round < 20) {
            round++;
            
            // Наполняем очередь команд для героя
            actionQueue.enqueue(new AttackCommand(opponent, hero.getAttackPower()));
            actionQueue.enqueue(new DefendCommand(hero, 0.10));

            // Выполняем действия героя
            actionQueue.executeAll();

            // Если враг выжил, он атакует героя через цепочку защиты
            if (opponent.isAlive()) {
                System.out.println("\nВраг атакует " + hero.getName() + "!");
                defenseChain.handle(opponent.getAttackPower(), hero);
            }

            String log = "Раунд " + round + ": У врага " + opponent.getHealth() + " HP, у героя " + hero.getHealth() + " HP";
            result.addLine(log);
        }

        result.setWinner(hero.isAlive() ? hero.getName() : opponent.getName());
        result.setRounds(round);
        return result;
    }
}
