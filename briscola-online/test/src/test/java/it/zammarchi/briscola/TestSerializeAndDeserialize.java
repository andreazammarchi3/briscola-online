package it.zammarchi.briscola;

import com.google.gson.Gson;
import it.zammarchi.briscola.common.model.Cards;
import it.zammarchi.briscola.common.model.Lobby;
import it.zammarchi.briscola.common.model.Match;
import it.zammarchi.briscola.common.model.User;
import it.zammarchi.briscola.common.utils.GsonUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSerializeAndDeserialize {
    private static final User MARIO = new User(
            "Mario"
    );

    private static final User LUIGI = new User(
            "Luigi",
            new ArrayList<>(),
            new ArrayList<>()
    );

    private static final Lobby EMPTY_LOBBY = new Lobby(1);

    private static final Lobby FULL_LOBBY = new Lobby(
            2,
            MARIO,
            LUIGI
    );

    private static final Match MATCH = new Match(FULL_LOBBY);

    private static Gson gson;

    @BeforeAll
    public static void firstSetUp() {
        LUIGI.getHandCards().add(Cards.BASTONI_ONE);
        LUIGI.getHandCards().add(Cards.DENARI_TWO);
        LUIGI.getHandCards().add(Cards.SPADE_THREE);
        LUIGI.getCards().add(Cards.COPPE_FOUR);
        LUIGI.getHandCards().add(Cards.BASTONI_FIVE);
        LUIGI.getHandCards().add(Cards.SPADE_SIX);

        gson = GsonUtils.createGson();
    }

    @Test
    public void testUserGson() {
        for (var user : List.of(MARIO, LUIGI)) {
            String serialized = gson.toJson(user);
            User deserialized = gson.fromJson(serialized, User.class);
            assertEquals(user, deserialized);
        }
    }

   @Test
   public void testLobbyGson() {
        for(var lobby : List.of(EMPTY_LOBBY, FULL_LOBBY)) {
            String serialized = gson.toJson(lobby);
            Lobby deserialized = gson.fromJson(serialized, Lobby.class);
            assertEquals(lobby, deserialized);
        }
   }

   @Test
   public void testCardsGson() {
       String serialized = gson.toJson(Cards.BASTONI_ONE);
       Cards deserialized = gson.fromJson(serialized, Cards.class);
       assertEquals(Cards.BASTONI_ONE, deserialized);
   }

   @Test
   public void testMatchGson() {
       String serialized = gson.toJson(MATCH);
       Match deserialized = gson.fromJson(serialized, Match.class);
       assertEquals(MATCH, deserialized);
   }
}
