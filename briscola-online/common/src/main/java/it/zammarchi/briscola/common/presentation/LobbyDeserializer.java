package it.zammarchi.briscola.common.presentation;

import com.google.gson.*;
import it.zammarchi.briscola.common.model.Lobby;
import it.zammarchi.briscola.common.model.User;
import it.zammarchi.briscola.common.utils.GsonUtils;

import java.lang.reflect.Type;
import java.util.Objects;

import static it.zammarchi.briscola.common.utils.GsonUtils.getPropertyAs;

/**
 * Class for JSON deserialization of a lobby.
 */
public class LobbyDeserializer implements JsonDeserializer<Lobby> {
    @Override
    public Lobby deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            var object = json.getAsJsonObject();
            var id = getPropertyAs(object, "id", Integer.class, context);
            User userA = getPropertyAs(object, "user_a", User.class, context);
            User userB = getPropertyAs(object, "user_b", User.class, context);
            return new Lobby(id, userA, userB);
        } catch (ClassCastException e) {
            throw new JsonParseException("Invalid lobby: " + json, e);
        }
    }


}
