package it.zammarchi.briscola.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class representing the concept of player. It has a unique nickname as identifier.
 */
public class User {
    private final String name;
    private List<Cards> handCards = new ArrayList<>();
    private List<Cards> cards = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }

    public User(String name, List<Cards> handCards, List<Cards> cards) {
        this.name = name;
        this.handCards = handCards;
        this.cards = cards;
    }

    public User(User other) {
        this.name = other.name;
        this.cards = other.cards;
        this.handCards = other.handCards;
    }

    /**
     * Get the username.
     * @return the username
     */
    public String getName() {
        return name;
    }

    /**
     * Get the cards obtained by the user.
     * @return the cards obtained by the user
     */
    public List<Cards> getCards() {
        return cards;
    }

    /**
     * Get the cards in the hand of the user.
     * @return the cards in the hand of the user
     */
    public List<Cards> getHandCards() {
        return handCards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(handCards, user.handCards) && Objects.equals(cards, user.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, handCards, cards);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", handCards=" + handCards +
                ", cards=" + cards +
                '}';
    }
}
