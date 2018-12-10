package com.logmein.coding.test.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


public class Card implements Serializable
{
    private static final long serialVersionUID = -3769037798856374921L;

    @JsonProperty
    private CARDTYPE cardType;

    @JsonProperty
    private CARDNUMBER cardNumber ;

    public Card() {
    }

    Card(@NotNull CARDTYPE cardType, @NotNull CARDNUMBER cardNumber) {
        this.cardType = cardType;
        this.cardNumber = cardNumber;
    }

    public CARDTYPE getCardType() {
        return cardType;
    }

    public CARDNUMBER getCardNumber() {
        return cardNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return getCardType() == card.getCardType() &&
                getCardNumber() == card.getCardNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCardType(), getCardNumber());
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardType=" + cardType +
                ", cardNumber=" + cardNumber +
                '}';
    }
}
