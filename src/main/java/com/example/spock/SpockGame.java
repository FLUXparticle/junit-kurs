package com.example.spock;

/**
 * Created by sreinck on 22.01.18.
 */
public class SpockGame {

    private final Strategy strategy;

    private Move lastMove;

    public SpockGame(Strategy strategy) {
        this.strategy = strategy;
    }

    public String play(Move move) {
        Move other = strategy.nextMove(lastMove);
        lastMove = move;

        String verb = move.verb(other);
        if (verb != null) {
            return String.format("%s %s %s", move, verb, other);
        }
        verb = other.verb(move);
        if (verb != null) {
            return String.format("%s %s %s", other, verb, move);
        }
        return "UNENTSCHIEDEN";
    }

}
