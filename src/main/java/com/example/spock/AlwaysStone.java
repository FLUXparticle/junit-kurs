package com.example.spock;

/**
 * Created by sreinck on 22.01.18.
 */
public class AlwaysStone implements Strategy {

    @Override
    public Move nextMove(Move lastMove) {
        return Move.STEIN;
    }

}
