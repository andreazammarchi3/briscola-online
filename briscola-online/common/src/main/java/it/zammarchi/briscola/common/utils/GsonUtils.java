package it.zammarchi.briscola.common.utils;

import com.google.gson.*;
import it.zammarchi.briscola.common.model.*;
import it.zammarchi.briscola.common.presentation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Util class for the JSON serialization/deserialization.
 */
public class GsonUtils {
    public static Gson createGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .registerTypeAdapter(User.class, new UserSerializer())
                .registerTypeAdapter(User.class, new UserDeserializer())
                .registerTypeAdapter(Lobby.class, new LobbySerializer())
                .registerTypeAdapter(Lobby.class, new LobbyDeserializer())
                .registerTypeAdapter(Cards.class, new CardsSerializer())
                .registerTypeAdapter(Cards.class, new CardsDeserializer())
                .registerTypeAdapter(Suits.class, new SuitsSerializer())
                .registerTypeAdapter(Suits.class, new SuitsDeserializer())
                .registerTypeAdapter(Match.class, new MatchSerializer())
                .registerTypeAdapter(Match.class, new MatchDeserializer())
                .create();
    }

    /**
     * Deserialize a JSON Object into a specific Java Type.
     * @param object the serialized Json Object
     * @param name the name of the object
     * @param type the type of the deserialized object
     * @param context the context
     * @return the deserialized object
     * @param <T> the type of the deserialized object
     */
    public static <T> T getPropertyAs(JsonObject object, String name, Class<T> type, JsonDeserializationContext context) {
        if (object.has(name)) {
            JsonElement value = object.get(name);
            if (value.isJsonNull()) return null;
            return context.deserialize(value, type);
        }
        return null;
    }

    /**
     * Deserialize a JSON Array of cards into a list.
     * @param jsonArray the serialized array of cards
     * @param context the context
     * @return a list with the cards deserialized
     */
    public static List<Cards> deserializeCards(JsonArray jsonArray, JsonDeserializationContext context) {
        List<Cards> handCards = new ArrayList<>(jsonArray.size());
        for (var card : jsonArray) {
            if (card.isJsonNull()) continue;
            var cardObject = card.getAsJsonObject();
            Cards cardItem = getPropertyAs(cardObject, "card", Cards.class, context);
            handCards.add(cardItem);
        }
        return handCards;
    }
}
