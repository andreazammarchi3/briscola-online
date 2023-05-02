package it.zammarchi.briscola.common.presentation;

import com.google.gson.*;
import it.zammarchi.briscola.common.model.Lobby;

import java.lang.reflect.Type;

/**
 * Class for JSON serialization of a lobby.
 */
public class LobbySerializer implements JsonSerializer<Lobby> {
    @Override
    public JsonElement serialize(Lobby src, Type typeOfSrc, JsonSerializationContext context) {
        var object = new JsonObject();
        object.addProperty("id", src.getId());
        object.add("user_a", context.serialize(src.getUserA()));
        object.add("user_b", context.serialize(src.getUserB()));
        return object;
    }
}
