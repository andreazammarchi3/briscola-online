package it.zammarchi.briscola.common.presentation;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import it.zammarchi.briscola.common.model.Suits;

import java.lang.reflect.Type;

/**
 * Class for JSON serialization of a suit.
 */
public class SuitsSerializer implements JsonSerializer<Suits> {
    @Override
    public JsonElement serialize(Suits src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.name().toLowerCase());
    }
}
