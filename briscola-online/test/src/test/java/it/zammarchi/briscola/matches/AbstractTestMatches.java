package it.zammarchi.briscola.matches;

import it.zammarchi.briscola.common.Briscola;
import it.zammarchi.briscola.common.exceptions.ConflictException;
import it.zammarchi.briscola.common.exceptions.MissingException;
import it.zammarchi.briscola.common.exceptions.ServerOfflineException;
import it.zammarchi.briscola.common.model.*;
import it.zammarchi.briscola.common.utils.MathUtils;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractTestMatches {

    protected Briscola briscola;
    protected Match match;
    protected Lobby lobby;
    protected User mario;
    protected User luigi;

    abstract void setUp();

    protected void testStartMatch() throws MissingException, ConflictException, ServerOfflineException {
        Match expected = new Match(lobby);
        match = briscola.startMatch(lobby);

        if (Objects.equals(match.getFirstPlayer().getName(), mario.getName())) {
            mario = match.getFirstPlayer();
            luigi = match.getSecondPlayer();
        } else {
            luigi = match.getFirstPlayer();
            mario = match.getSecondPlayer();
        }
        assertEquals(expected.getId(), match.getId());
        assertEquals(3, mario.getHandCards().size());
        assertEquals(3, luigi.getHandCards().size());
        assertEquals(33, match.getDeck().size());
        assertNotNull(match.getLastCard());
        assertEquals(0, match.getPlayedCards().size());

        assertThrows(MissingException.class, () -> briscola.startMatch(new Lobby(2, null, null)));
        assertThrows(ConflictException.class, () -> briscola.startMatch(lobby));
    }

    protected void testGetMatch() throws MissingException, ServerOfflineException{
        assertEquals(match, briscola.getMatch(1));

        assertThrows(MissingException.class, () -> briscola.getMatch(2));
    }

    protected void testGetAllMatches() throws ServerOfflineException {
        assertEquals(1, briscola.getAllMatches().size());
        assertTrue(briscola.getAllMatches().contains(match));
    }

    protected void testDeleteMatch() throws MissingException, ServerOfflineException {
        briscola.deleteMatch(1);
        assertThrows(MissingException.class, () -> briscola.getMatch(1));
    }

    protected abstract class TestUserPlaysCard {

        protected void testFirstPlay() throws MissingException, ConflictException, ServerOfflineException {
            match = briscola.startMatch(lobby);
            match = briscola.userPlaysCard(match.getId(), match.getFirstPlayer(), MathUtils.randomPlay(match.getFirstPlayer()));
            assertEquals(match.getSecondPlayer().getHandCards().size() - 1, match.getFirstPlayer().getHandCards().size());
            assertEquals(1, match.getPlayedCards().size());
        }

        protected void testSecondPlay() throws MissingException, ServerOfflineException {
            match = briscola.userPlaysCard(match.getId(), match.getSecondPlayer(), MathUtils.randomPlay(match.getSecondPlayer()));
            assertEquals(match.getFirstPlayer().getHandCards().size(), match.getSecondPlayer().getHandCards().size());
            assertEquals(0, match.getPlayedCards().size());
            assertTrue(match.getFirstPlayer().getCards().size() != 0 || match.getSecondPlayer().getCards().size() != 0);
        }
    }

    protected void createInstance() {
        try {
            briscola.createLobby();
            this.mario = briscola.createUser("Mario");
            this.luigi = briscola.createUser("Luigi");
            briscola.joinUserToLobby(1, mario.getName());
            this.lobby = briscola.joinUserToLobby(1, luigi.getName());
        } catch (ConflictException | IllegalArgumentException | ServerOfflineException | MissingException e) {
            throw new RuntimeException(e);
        }
    }
}
