package it.zammarchi.briscola.common.presentation;

import com.google.gson.*;
import it.zammarchi.briscola.common.model.User;
import it.zammarchi.briscola.common.utils.GsonUtils;

import java.lang.reflect.Type;

/**
 * Class for JSON serialization of a user.
 */
public class UserSerializer implements JsonSerializer<User> {
    @Override
    public JsonElement serialize(User src, Type typeOfSrc, JsonSerializationContext context) {
        var object = new JsonObject();
        object.addProperty("name", src.getName());
        var handCards = new JsonArray();
        for (var card : src.getHandCards()) {
            var cardObject = new JsonObject();
            cardObject.add("card", context.serialize(card));
            handCards.add(cardObject);
        }
        object.add("hand_cards", handCards);
        var cards = new JsonArray();
        for (var card : src.getCards()) {
            var cardObject = new JsonObject();
            cardObject.add("card", context.serialize(card));
            cards.add(cardObject);
        }
        object.add("cards", cards);
        return object;
    }
}
