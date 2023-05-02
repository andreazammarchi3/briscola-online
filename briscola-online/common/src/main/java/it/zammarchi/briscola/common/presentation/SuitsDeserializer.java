package it.zammarchi.briscola.common.presentation;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import it.zammarchi.briscola.common.model.Suits;

import java.lang.reflect.Type;

/**
 * Class for JSON deserialization of a suit.
 */
public class SuitsDeserializer implements JsonDeserializer<Suits> {
    @Override
    public Suits deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return Suits.valueOf(json.getAsString().toUpperCase());
        } catch (IllegalArgumentException | ClassCastException e) {
            throw new JsonParseException("Invalid suit: " + json, e);
        }
    }
}
