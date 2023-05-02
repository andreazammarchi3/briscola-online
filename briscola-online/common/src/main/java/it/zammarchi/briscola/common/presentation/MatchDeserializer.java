package it.zammarchi.briscola.common.presentation;

import com.google.gson.*;
import it.zammarchi.briscola.common.model.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static it.zammarchi.briscola.common.utils.GsonUtils.deserializeCards;
import static it.zammarchi.briscola.common.utils.GsonUtils.getPropertyAs;

/**
 * Class for JSON deserialization of a match.
 */
public class MatchDeserializer implements JsonDeserializer<Match> {
    @Override
    public Match deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            var object = json.getAsJsonObject();
            int id = getPropertyAs(object, "id", Integer.class, context);
            User firstPlayer = getPropertyAs(object, "first_player", User.class, context);
            User secondPlayer = getPropertyAs(object, "second_player", User.class, context);
            var deckArray = object.getAsJsonArray("deck");
            Stack<Cards> deck = new Stack<>();
            deck.addAll(deserializeCards(deckArray, context));
            Cards lastCard = getPropertyAs(object, "last_card", Cards.class, context);
            Suits briscolaSuit = getPropertyAs(object, "briscola_suit", Suits.class, context);
            var playedCardsArray = object.getAsJsonArray("played_cards");
            List<Cards> playedCards = deserializeCards(playedCardsArray, context);
            User winner = getPropertyAs(object, "winner", User.class, context);
            return new Match(id, firstPlayer, secondPlayer, deck, lastCard, briscolaSuit, playedCards, winner);
        } catch (ClassCastException e) {
            throw new JsonParseException("Invalid lobby: " + json, e);
        }
    }
}
