package com.example.spock;

public enum Move {

    STEIN {
        @Override
        public String verb(Move other) {
            switch (other) {
                case SCHERE:
                    return "schleift";
                default:
                    return null;
            }
        }
    },
    PAPIER {
        @Override
        public String verb(Move other) {
            switch (other) {
                case STEIN:
                    return "umhüllt";
                default:
                    return null;
            }
        }
    },
    SCHERE {
        @Override
        public String verb(Move other) {
            switch (other) {
                case PAPIER:
                    return "zerschneidet";
                default:
                    return null;
            }
        }
    };

    public abstract String verb(Move other);

}
