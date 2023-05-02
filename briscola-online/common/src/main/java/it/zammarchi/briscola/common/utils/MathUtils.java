package it.zammarchi.briscola.common.utils;

import it.zammarchi.briscola.common.model.Cards;
import it.zammarchi.briscola.common.model.Match;
import it.zammarchi.briscola.common.model.Suits;
import it.zammarchi.briscola.common.model.User;

import java.util.Random;

/**
 * Utils class with some commons math methods.
 */
public class MathUtils {

    /**
     * Get a random int number between a range.
     * @param min the lower bound
     * @param max the upper bound
     * @return the randomly generated int
     */
    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    /**
     * Check which card has the highest value, depending on the order and the suit.
     * @param cardA the first card played
     * @param cardB the second card played
     * @param briscolaSuit the suit of briscola
     * @return the higher card
     */
    public static Cards getHigherCard(Cards cardA, Cards cardB, Suits briscolaSuit) {
        if (cardA.getSuit().equals(briscolaSuit) && !cardB.getSuit().equals(briscolaSuit)) {
            return cardA;
        } else if (!cardA.getSuit().equals(briscolaSuit) && cardB.getSuit().equals(briscolaSuit)) {
            return cardB;
        } else {
            if (cardA.getSuit() != cardB.getSuit()) {
                return cardA;
            } else {
                if (cardA.getValue() < cardB.getValue()) {
                    return cardB;
                } else if (cardA.getValue() > cardB.getValue()) {
                    return cardA;
                } else {
                    if (cardA.getRank() < cardB.getRank()) {
                        return cardB;
                    } else {
                        return cardA;
                    }
                }
            }
        }
    }

    /**
     * Get the sum of values of the cards obtained by the user.
     * @param user the user tha has obtained the cards
     * @return the sum of the values of the cards
     */
    public static int getUserCardsTotalValue(User user) {
        int totalValue = 0;
        for (Cards card : user.getCards()) {
            totalValue = totalValue + card.getValue();
        }
        return totalValue;
    }

    /**
     * Check which user has the highest sum of values of cards obtained.
     * @param match the match to be analyzed
     * @return the user winner, in case of draw return a new user with the nicnkame "PARITA'"
     */
    public static User getUserWinner(Match match) {
        int totalFirstPlayer = getUserCardsTotalValue(match.getFirstPlayer());
        int totalSecondPlayer = getUserCardsTotalValue(match.getSecondPlayer());
        if (totalFirstPlayer > totalSecondPlayer) {
            return match.getFirstPlayer();
        } else if (totalFirstPlayer < totalSecondPlayer) {
            return match.getSecondPlayer();
        } else {
            return new User("PARITA'");
        }
    }

    /**
     * Get a randomly played card by a user. Can be used for testing.
     * @param user the user playing a card
     * @return the card randomly played
     */
    public static Cards randomPlay(User user) {
        return user.getHandCards().get(new Random().nextInt(0, user.getHandCards().size() - 1));
    }
}
