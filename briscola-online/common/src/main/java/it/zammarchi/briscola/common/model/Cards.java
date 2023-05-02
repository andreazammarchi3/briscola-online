package it.zammarchi.briscola.common.model;

/**
 * Enum containing all the Cards in a typical deck of Briscola.
 */
public enum Cards {
    BASTONI_ONE(1, Suits.BASTONI, 1, 11),
    BASTONI_TWO(2, Suits.BASTONI, 2, 0),
    BASTONI_THREE(3, Suits.BASTONI, 3, 10),
    BASTONI_FOUR(4, Suits.BASTONI, 4, 0),
    BASTONI_FIVE(5, Suits.BASTONI, 5, 0),
    BASTONI_SIX(6, Suits.BASTONI, 6, 0),
    BASTONI_SEVEN(7, Suits.BASTONI, 7, 0),
    BASTONI_EIGHT(8, Suits.BASTONI, 8, 2),
    BASTONI_NINE(9, Suits.BASTONI, 9, 3),
    BASTONI_TEN(10, Suits.BASTONI, 10, 4),

    DENARI_ONE(11, Suits.DENARI, 1, 11),
    DENARI_TWO(12, Suits.DENARI, 2, 0),
    DENARI_THREE(13, Suits.DENARI, 3, 10),
    DENARI_FOUR(14, Suits.DENARI, 4, 0),
    DENARI_FIVE(15, Suits.DENARI, 5, 0),
    DENARI_SIX(16, Suits.DENARI, 6, 0),
    DENARI_SEVEN(17, Suits.DENARI, 7, 0),
    DENARI_EIGHT(18, Suits.DENARI, 8, 2),
    DENARI_NINE(19, Suits.DENARI, 9, 3),
    DENARI_TEN(20, Suits.DENARI, 10, 4),

    COPPE_ONE(21, Suits.COPPE, 1, 11),
    COPPE_TWO(22, Suits.COPPE, 2, 0),
    COPPE_THREE(23, Suits.COPPE, 3, 10),
    COPPE_FOUR(24, Suits.COPPE, 4, 0),
    COPPE_FIVE(25, Suits.COPPE, 5, 0),
    COPPE_SIX(26, Suits.COPPE, 6, 0),
    COPPE_SEVEN(27, Suits.COPPE, 7, 0),
    COPPE_EIGHT(28, Suits.COPPE, 8, 2),
    COPPE_NINE(29, Suits.COPPE, 9, 3),
    COPPE_TEN(30, Suits.COPPE, 10, 4),

    SPADE_ONE(31, Suits.SPADE, 1, 11),
    SPADE_TWO(32, Suits.SPADE, 2, 0),
    SPADE_THREE(33, Suits.SPADE, 3, 10),
    SPADE_FOUR(34, Suits.SPADE, 4, 0),
    SPADE_FIVE(35, Suits.SPADE, 5, 0),
    SPADE_SIX(36, Suits.SPADE, 6, 0),
    SPADE_SEVEN(37, Suits.SPADE, 7, 0),
    SPADE_EIGHT(38, Suits.SPADE, 8, 2),
    SPADE_NINE(39, Suits.SPADE, 9, 3),
    SPADE_TEN(40, Suits.SPADE, 10, 4),
    ;

    private final int id;
    private final Suits suit;
    private final int rank;
    private final int value;

    Cards(int id, Suits suit, int rank, int value) {
        this.id = id;
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    /**
     * Get the id of the card.
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Get the suit of the card.
     * @return the suit
     */
    public Suits getSuit() {
        return suit;
    }

    /**
     * Get the rank of the card.
     * @return the rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * Get the value of the card.
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * Get the card by its id.
     * @param id the id of the card
     * @return the card
     */
    public static Cards getCardById(int id) {
        for (Cards card : values()) {
            if (card.id == id) {
                return card;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return getRank() + " di " + getSuit();
    }
}
