package com.example.params;

public enum Month {

    JANUARY(31),
    FEBRUARY(28) {
        public int length(boolean isALeapYear) {
            // Februar hat 29 Tage in Schaltjahren
            return (isALeapYear) ? 29 : days;
        }
    },
    MARCH(31),
    APRIL(30),
    MAY(31),
    JUNE(30),
    JULY(31),
    AUGUST(31),
    SEPTEMBER(30),
    OCTOBER(31),
    NOVEMBER(30),
    DECEMBER(31);

    protected final int days;

    Month(int days) {
        this.days = days;
    }

    public int length(boolean isALeapYear) {
        return days;
    }

    public int getValue() {
        return ordinal() + 1;
    }

}
