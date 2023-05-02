package it.zammarchi.briscola;

import it.zammarchi.briscola.common.model.*;
import it.zammarchi.briscola.common.utils.MathUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMathUtils {

    @Test
    public void testGetHigherCard() {
        // Different rank, same suit
        assertEquals(Cards.BASTONI_FOUR, MathUtils.getHigherCard(Cards.BASTONI_TWO, Cards.BASTONI_FOUR, Suits.COPPE));
        // Same rank, different suit
        assertEquals(Cards.BASTONI_FOUR, MathUtils.getHigherCard(Cards.BASTONI_FOUR, Cards.DENARI_FOUR, Suits.COPPE));
        // Different rank, different suit, one briscola suit
        assertEquals(Cards.BASTONI_TWO, MathUtils.getHigherCard(Cards.BASTONI_TWO, Cards.DENARI_FOUR, Suits.BASTONI));
        // Same rank, different suit, one briscola suit
        assertEquals(Cards.BASTONI_FOUR, MathUtils.getHigherCard(Cards.BASTONI_FOUR, Cards.DENARI_FOUR, Suits.BASTONI));
    }

    @Test
    public void testGetUserCardsTotalValue() {
        User mario = new User("Mario");
        mario.getCards().add(Cards.DENARI_ONE);
        mario.getCards().add(Cards.BASTONI_TWO);
        mario.getCards().add(Cards.COPPE_THREE);
        mario.getCards().add(Cards.SPADE_EIGHT);
        mario.getCards().add(Cards.SPADE_NINE);
        mario.getCards().add(Cards.SPADE_TEN);
        assertEquals(30, MathUtils.getUserCardsTotalValue(mario));
    }

    @Test
    public void testGetUserWinner() {
        // Test a winner
        Match match = newMatch(Cards.SPADE_TEN, Cards.BASTONI_FOUR);
        assertEquals(match.getFirstPlayer(), match.getWinner());

        // Test a draw
        match = newMatch(Cards.COPPE_TWO, Cards.DENARI_FOUR);
        assertEquals(new User("PARITA'"), match.getWinner());
    }

    private Match newMatch(Cards cardA, Cards cardB) {
        User mario = new User("Mario");
        mario.getHandCards().add(cardA);
        User luigi = new User("Luigi");
        luigi.getHandCards().add(cardB);
        Match match = new Match(1, mario, luigi, new Stack<>(), null, cardA.getSuit(), new ArrayList<>(), null);
        match = match.userPlaysCard(match.getFirstPlayer(), match.getFirstPlayer().getHandCards().get(0));
        match = match.userPlaysCard(match.getSecondPlayer(), match.getSecondPlayer().getHandCards().get(0));
        return match;
    }
}
