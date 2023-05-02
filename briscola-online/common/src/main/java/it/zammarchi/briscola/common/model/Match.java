package it.zammarchi.briscola.common.model;

import it.zammarchi.briscola.common.utils.MathUtils;

import java.util.*;

/**
 * Class representing the concept of match between two users.
 */
public class Match {
    private static final int DECK_SIZE = 40;
    private final int id;
    private User firstPlayer;
    private User secondPlayer;
    private Stack<Cards> deck = new Stack<>();
    private Cards lastCard;
    private Suits briscolaSuit;
    private List<Cards> playedCards;
    private User winner;

    public Match(Match other) {
        this.id = other.getId();
        this.firstPlayer = other.getFirstPlayer();
        this.secondPlayer = other.getSecondPlayer();
        this.winner = other.getWinner();
        setUpTable();
    }

    public Match(int id, User firstPlayer, User secondPlayer, Stack<Cards> deck, Cards lastCard, Suits briscolaSuit,
                 List<Cards> playedCards, User winner) {
        this.id = id;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.deck = deck;
        this.lastCard = lastCard;
        this.briscolaSuit = briscolaSuit;
        this.playedCards = playedCards;
        this.winner = winner;
    }

    public Match(Lobby lobby) {
        this.id = lobby.getId();
        this.firstPlayer = lobby.getUserA();
        this.secondPlayer = lobby.getUserB();
        setUpTable();
    }

    /**
     * Get the deck.
     * @return the deck
     */
    public Stack<Cards> getDeck() {
        return deck;
    }

    /**
     * Get the last card.
     * @return the last card
     */
    public Cards getLastCard() {
        return lastCard;
    }

    /**
     * Get the id of the match.
     * @return the id of the match
     */
    public int getId() {
        return id;
    }

    /**
     * Get the first player.
     * @return the first player
     */
    public User getFirstPlayer() {
        return firstPlayer;
    }

    /**
     * Get the second player.
     * @return the second player
     */
    public User getSecondPlayer() {
        return secondPlayer;
    }

    /**
     * Get the suit of briscola.
     * @return the suit of briscola
     */
    public Suits getBriscolaSuit() {
        return briscolaSuit;
    }

    /**
     * Get the list of played cards.
     * @return the list of played cards.
     */
    public List<Cards> getPlayedCards() {
        return playedCards;
    }

    /**
     * Get the winner of the match, if existing.
     * @return the user winner of the match
     */
    public User getWinner() {
        return winner;
    }

    private void swapPlayersOrder() {
        User temp = firstPlayer;
        firstPlayer = secondPlayer;
        secondPlayer = temp;
    }

    private void setUpTable() {
        // Shuffle new deck
        for (int i = 1; i <= DECK_SIZE; ++i) {
            deck.push(Cards.getCardById(i));
        }
        Collections.shuffle(deck);
        playedCards = new ArrayList<>();

        // Set last card for briscola
        lastCard = deck.pop();
        briscolaSuit = lastCard.getSuit();

        // SetUp users cards
        if (MathUtils.getRandomNumber(1, 2) == 1) {
            swapPlayersOrder();
        }
        firstPlayer.getHandCards().clear();
        secondPlayer.getHandCards().clear();
        for (int i = 0; i < 3; i++) {
            userDraws(firstPlayer);
            userDraws(secondPlayer);
        }

        firstPlayer.getCards().clear();
        secondPlayer.getCards().clear();
    }

    private void userDraws(User user) {
        if (deck.size() == 0) {
            if (lastCard != null) {
                user.getHandCards().add(lastCard);
                lastCard = null;
            }
        } else {
            user.getHandCards().add(deck.pop());
        }
    }

    /**
     * A user plays a card. The actions change whether the user is the first or the second.
     * @param user the user that has played the card
     * @param card tha card played
     * @return the match instance updated
     */
    public Match userPlaysCard(User user, Cards card) {
        user.getHandCards().remove(card);
        playedCards.add(card);
        if (user == secondPlayer) {
            Cards higherCard = MathUtils.getHigherCard(playedCards.get(0), playedCards.get(1), briscolaSuit);
            if (higherCard == card) {
                swapPlayersOrder();
            }
            firstPlayer.getCards().addAll(playedCards);

            userDraws(firstPlayer);
            userDraws(secondPlayer);

            playedCards.clear();

            if (firstPlayer.getHandCards().size() == 0 && secondPlayer.getHandCards().size() == 0) {
                winner = MathUtils.getUserWinner(this);
            }
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return Objects.equals(id, match.id) &&
                Objects.equals(firstPlayer, match.firstPlayer) &&
                Objects.equals(secondPlayer, match.secondPlayer) &&
                Objects.equals(deck, match.deck) &&
                lastCard == match.lastCard;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstPlayer, secondPlayer, deck, lastCard);
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", firstPlayer=" + firstPlayer +
                ", secondPlayer=" + secondPlayer +
                ", deck=" + deck +
                ", lastCard=" + lastCard +
                '}';
    }
}
