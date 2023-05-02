package it.zammarchi.briscola.lobbies;

import it.zammarchi.briscola.common.Briscola;
import it.zammarchi.briscola.common.exceptions.ConflictException;
import it.zammarchi.briscola.common.exceptions.MissingException;
import it.zammarchi.briscola.common.exceptions.ServerOfflineException;
import it.zammarchi.briscola.common.model.Lobby;
import it.zammarchi.briscola.common.model.User;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractTestLobbies {

    protected Briscola briscola;
    protected Lobby lobby;
    protected User mario;
    protected User luigi;

    abstract public void setUp();

    protected void testCreateLobby() {
        Lobby expected = new Lobby(1, null, null);
        assertEquals(expected, lobby);
    }

    protected void testGetLobby() throws MissingException, ServerOfflineException {
        assertEquals(lobby, briscola.getLobby(1));

        assertThrows(MissingException.class, () -> briscola.getLobby(2));
    }

    protected void testGetAllLobbies() throws ServerOfflineException {
        assertEquals(1, briscola.getAllLobbies().size());
        assertTrue(briscola.getAllLobbies().contains(lobby));
    }

    protected void testDeleteLobby() throws MissingException, ServerOfflineException {
        briscola.deleteLobby(1);
        assertThrows(MissingException.class, () -> briscola.getLobby(1));
    }

    protected void testJoinUserToLobby() throws MissingException, ServerOfflineException {
        lobby = briscola.createLobby();
        assertEquals(mario, briscola.joinUserToLobby(1, mario.getName()).getUserA());

        assertThrows(MissingException.class, () -> briscola.joinUserToLobby(2, mario.getName()));
        assertThrows(MissingException.class, () -> briscola.joinUserToLobby(1, "Yoshi"));
    }

    protected void testDeleteUserFromLobby() throws MissingException, ServerOfflineException {
        briscola.joinUserToLobby(1, luigi.getName());
        briscola.deleteUserFromLobby(1, luigi.getName());
        assertNull(briscola.getLobby(1).getUserB());
        briscola.deleteUserFromLobby(1, mario.getName());
        assertThrows(MissingException.class, () -> briscola.getLobby(1));
    }

    protected void createInstance() {
        try {
            this.lobby = briscola.createLobby();
            this.mario = briscola.createUser("Mario");
            this.luigi = briscola.createUser("Luigi");
        } catch (ConflictException | IllegalArgumentException | ServerOfflineException e) {
            throw new RuntimeException(e);
        }
    }
}
