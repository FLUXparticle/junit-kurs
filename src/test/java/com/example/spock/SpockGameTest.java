package com.example.spock;

import org.junit.*;
import org.mockito.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;

public class SpockGameTest {

    @Test
    public void mock() {
        Strategy strategy = Mockito.mock(Strategy.class);
        Mockito.when(strategy.nextMove(null)).thenReturn(Move.STEIN);

        SpockGame game = new SpockGame(strategy);
        String play = game.play(Move.SPOCK);

        Mockito.verify(strategy, Mockito.times(1)).nextMove(any());
        assertEquals("SPOCK vaporisiert STEIN", play);
    }

    @Test
    public void spy() {
        Strategy strategy = new AlwaysStone();
        Move move = Mockito.spy(Move.SPOCK);

        SpockGame game = new SpockGame(strategy);
        String play = game.play(move);

        Mockito.verify(move).verb(Move.STEIN);
        assertEquals("SPOCK vaporisiert STEIN", play);
    }

}
