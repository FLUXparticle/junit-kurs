package com.example.spock;

import java.util.*;

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

        return String.format("%s %s %s", move, "GLEICH", other);
    }

    public static void main(String[] args) {
        Strategy strategy = new RandomThenCopyStrategy();
        SpockGame game = new SpockGame(strategy);

        System.out.println("Stein-Papier-Schere");

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("Eingabe: 1=Stein | 2=Papier | 3=Schere | q=Quit");
                System.out.print("> ");
                String input = scanner.nextLine().trim().toLowerCase();

                if ("q".equals(input)) {
                    System.out.println("Bye.");
                    break;
                }

                Move move = parseMove(input);
                if (move == null) {
                    System.out.println("Ungültige Eingabe.");
                    continue;
                }

                String result = game.play(move);

                System.out.println(result);
            }
        }
    }

    private static Move parseMove(String input) {
        return switch (input) {
            case "1" -> Move.STEIN;
            case "2" -> Move.PAPIER;
            case "3" -> Move.SCHERE;
            default -> null;
        };
    }

}
