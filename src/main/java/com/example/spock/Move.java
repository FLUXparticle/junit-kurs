package com.example.spock;

/**
 * Created by sreinck on 23.11.16.
 */
public enum Move {

    STEIN {
        @Override
        public String verb(Move other) {
            switch (other) {
                case SCHEERE:
                    return "schleift";
                case ECHSE:
                    return "zerquetsch";
                default:
                    return null;
            }
        }
    }, PAPIER {
        @Override
        public String verb(Move other) {
            switch (other) {
                case SPOCK:
                    return "widerlegt";
                case STEIN:
                    return "umhüllt";
                default:
                    return null;
            }
        }
    }, SCHEERE {
        @Override
        public String verb(Move other) {
            switch (other) {
                case PAPIER:
                    return "zerschneidet";
                case ECHSE:
                    return "köpft";
                default:
                    return null;
            }
        }
    }, ECHSE {
        @Override
        public String verb(Move other) {
            switch (other) {
                case SPOCK:
                    return "vergiftet";
                case PAPIER:
                    return "frisst";
                default:
                    return null;
            }
        }
    }, SPOCK {
        @Override
        public String verb(Move other) {
            switch (other) {
                case SCHEERE:
                    return "zerbricht";
                case STEIN:
                    return "vaporisiert";
                default:
                    return null;
            }
        }
    };

    public abstract String verb(Move other);

}
