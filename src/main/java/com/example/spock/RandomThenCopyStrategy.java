package com.example.spock;

import java.util.*;

public class RandomThenCopyStrategy implements Strategy {

    private final Random random = new Random();

    @Override
    public Move nextMove(Move lastMove) {
        if (lastMove != null) {
            return lastMove;
        }

        Move[] moves = Move.values();
        return moves[random.nextInt(moves.length)];
    }

}
