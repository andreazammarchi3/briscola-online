package it.zammarchi.briscola.common.presentation;

import com.google.gson.*;
import it.zammarchi.briscola.common.model.Cards;

import java.lang.reflect.Type;

/**
 * Class for JSON serialization of a card.
 */
public class CardsSerializer implements JsonSerializer<Cards> {
    @Override
    public JsonElement serialize(Cards src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.name().toLowerCase());
    }
}
