package it.zammarchi.briscola.client.gui.utils;

import javafx.scene.image.Image;

/**
 * Enum containing all the possible cards.
 */
public enum CardsImages {

    NULL(0, null),
    CLUBS_ONE(1, "/imgs/cards/clubs_one.png"),
    CLUBS_TWO(2, "/imgs/cards/clubs_two.png"),
    CLUBS_THREE(3, "/imgs/cards/clubs_three.png"),
    CLUBS_FOUR(4, "/imgs/cards/clubs_four.png"),
    CLUBS_FIVE(5, "/imgs/cards/clubs_five.png"),
    CLUBS_SIX(6, "/imgs/cards/clubs_six.png"),
    CLUBS_SEVEN(7, "/imgs/cards/clubs_seven.png"),
    CLUBS_EIGHT(8, "/imgs/cards/clubs_eight.png"),
    CLUBS_NINE(9, "/imgs/cards/clubs_nine.png"),
    CLUBS_TEN(10, "/imgs/cards/clubs_ten.png"),

    COINS_ONE(11, "/imgs/cards/coins_one.png"),
    COINS_TWO(12, "/imgs/cards/coins_two.png"),
    COINS_THREE(13, "/imgs/cards/coins_three.png"),
    COINS_FOUR(14, "/imgs/cards/coins_four.png"),
    COINS_FIVE(15, "/imgs/cards/coins_five.png"),
    COINS_SIX(16, "/imgs/cards/coins_six.png"),
    COINS_SEVEN(17, "/imgs/cards/coins_seven.png"),
    COINS_EIGHT(18, "/imgs/cards/coins_eight.png"),
    COINS_NINE(19, "/imgs/cards/coins_nine.png"),
    COINS_TEN(20, "/imgs/cards/coins_ten.png"),

    CUPS_ONE(21, "/imgs/cards/cups_one.png"),
    CUPS_TWO(22, "/imgs/cards/cups_two.png"),
    CUPS_THREE(23, "/imgs/cards/cups_three.png"),
    CUPS_FOUR(24, "/imgs/cards/cups_four.png"),
    CUPS_FIVE(25, "/imgs/cards/cups_five.png"),
    CUPS_SIX(26, "/imgs/cards/cups_six.png"),
    CUPS_SEVEN(27, "/imgs/cards/cups_seven.png"),
    CUPS_EIGHT(28, "/imgs/cards/cups_eight.png"),
    CUPS_NINE(29, "/imgs/cards/cups_nine.png"),
    CUPS_TEN(30, "/imgs/cards/cups_ten.png"),

    SWORDS_ONE(31, "/imgs/cards/swords_one.png"),
    SWORDS_TWO(32, "/imgs/cards/swords_two.png"),
    SWORDS_THREE(33, "/imgs/cards/swords_three.png"),
    SWORDS_FOUR(34, "/imgs/cards/swords_four.png"),
    SWORDS_FIVE(35, "/imgs/cards/swords_five.png"),
    SWORDS_SIX(36, "/imgs/cards/swords_six.png"),
    SWORDS_SEVEN(37, "/imgs/cards/swords_seven.png"),
    SWORDS_EIGHT(38, "/imgs/cards/swords_eight.png"),
    SWORDS_NINE(39, "/imgs/cards/swords_nine.png"),
    SWORDS_TEN(40, "/imgs/cards/swords_ten.png"),

    BACK(41, "/imgs/back.png"),
    NOT_FOUND(42, "/imgs/not_found.png")
    ;

    private final int id;
    private final String path;

    CardsImages(int id, String path) {
        this.id = id;
        this.path = path;
    }

    /**
     * Get the card Image
     * @return the Image
     */
    public Image getImage() {
        return path != null ? new Image(path) : null;
    }

    /**
     * Get the card Image by the card id
     * @param id the id of the card
     * @return the Image of the card
     */
    public static Image getImageById(int id) {
        for (CardsImages cardImage : values()) {
            if (cardImage.id == id) {
                return new Image(cardImage.path);
            }
        }
        return new Image("/imgs/not_found.png");
    }
}
