package it.zammarchi.briscola.common.presentation;

import com.google.gson.*;
import it.zammarchi.briscola.common.model.Lobby;
import it.zammarchi.briscola.common.model.Match;

import java.lang.reflect.Type;

/**
 * Class for JSON serialization of a match.
 */
public class MatchSerializer implements JsonSerializer<Match> {
    @Override
    public JsonElement serialize(Match src, Type typeOfSrc, JsonSerializationContext context) {
        var object = new JsonObject();
        object.add("id", context.serialize(src.getId()));
        object.add("first_player", context.serialize(src.getFirstPlayer()));
        object.add("second_player", context.serialize(src.getSecondPlayer()));
        var deck = new JsonArray();
        for (var card : src.getDeck()) {
            var cardObject = new JsonObject();
            cardObject.add("card", context.serialize(card));
            deck.add(cardObject);
        }
        object.add("deck", deck);
        var playedCards = new JsonArray();
        for (var card : src.getPlayedCards()) {
            var cardObject = new JsonObject();
            cardObject.add("card", context.serialize(card));
            playedCards.add(cardObject);
        }
        object.add("last_card", context.serialize(src.getLastCard()));
        object.add("briscola_suit", context.serialize(src.getBriscolaSuit()));
        object.add("played_cards", playedCards);
        object.add("winner", context.serialize(src.getWinner()));
        return object;
    }
}
