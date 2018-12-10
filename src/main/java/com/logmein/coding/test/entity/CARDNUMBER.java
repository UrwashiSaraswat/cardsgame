package com.logmein.coding.test.entity;

import java.util.Arrays;
import java.util.stream.Stream;

public enum CARDNUMBER {

    ACE(1, "Ace"),
    TWO(2, "2"),
    THREE(3, "3"),
    FOUR(4, "4"),
    FIVE(5, "5"),
    SIX(6, "6"),
    SEVEN(7, "7"),
    EIGHT(8, "8"),
    NINE(9, "9"),
    TEN(10, "10"),
    JACK(11, "Jack"),
    QUEEN(12, "Queen"),
    KING(13, "King");

    private final int number;
    private final String numberName;

    CARDNUMBER(int number,String numberName)
    {
        this.number = number;
        this.numberName = numberName;
    }
    public int getNumber()
    {
        return number;
    }

    public String getNumberName()
    {
        return numberName;
    }

    public static CARDNUMBER getNumberOfCard(final int number)
    {
        return Arrays.stream(CARDNUMBER.values()).
                filter(v -> v.getNumber() == number).
                findFirst().
                orElseThrow(()->new IllegalArgumentException("No card exists with number "+number));
    }
}
