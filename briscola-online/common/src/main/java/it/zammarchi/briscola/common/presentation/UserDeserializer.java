package it.zammarchi.briscola.common.presentation;

import com.google.gson.*;
import it.zammarchi.briscola.common.model.Cards;
import it.zammarchi.briscola.common.model.User;
import it.zammarchi.briscola.common.utils.GsonUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static it.zammarchi.briscola.common.utils.GsonUtils.deserializeCards;
import static it.zammarchi.briscola.common.utils.GsonUtils.getPropertyAs;

/**
 * Class for JSON deserialization of a user.
 */
public class UserDeserializer implements JsonDeserializer<User> {
    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            var object = json.getAsJsonObject();
            String name = getPropertyAs(object, "name", String.class, context);
            var handCardsArray = object.getAsJsonArray("hand_cards");
            List<Cards> handCards = deserializeCards(handCardsArray, context);
            var cardsArray = object.getAsJsonArray("cards");
            List<Cards> cards = deserializeCards(cardsArray, context);
            return new User(name, handCards, cards);
        } catch (ClassCastException e) {
            throw new JsonParseException("Invalid user: " + json, e);
        }
    }
}
