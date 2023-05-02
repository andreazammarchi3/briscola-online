package it.zammarchi.briscola.common.presentation;

import com.google.gson.*;
import it.zammarchi.briscola.common.model.Cards;

import java.lang.reflect.Type;

/**
 * Class for JSON deserialization of a card.
 */
public class CardsDeserializer implements JsonDeserializer<Cards> {
    @Override
    public Cards deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return Cards.valueOf(json.getAsString().toUpperCase());
        } catch (IllegalArgumentException | ClassCastException e){
            throw new JsonParseException("Invalid card: " + json, e);
        }
    }
}
