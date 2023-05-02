package it.zammarchi.briscola.users;

import it.zammarchi.briscola.common.Briscola;
import it.zammarchi.briscola.common.exceptions.ConflictException;
import it.zammarchi.briscola.common.exceptions.MissingException;
import it.zammarchi.briscola.common.exceptions.ServerOfflineException;
import it.zammarchi.briscola.common.model.User;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class AbstractTestUsers {

    protected Briscola briscola;

    private User mario;

    abstract public void setUp();

    protected void testCreateUser() throws ConflictException, ServerOfflineException, IllegalArgumentException {
        User expected = new User("Mario", new ArrayList<>(), new ArrayList<>());
        mario = briscola.createUser("Mario");
        assertEquals(expected, mario);

        assertThrows(ConflictException.class, () -> briscola.createUser(mario.getName()));
        assertThrows(IllegalArgumentException.class, () -> briscola.createUser(""));
    }

    protected void testGetUser() throws MissingException, ServerOfflineException {
        assertEquals(mario, briscola.getUser(mario.getName()));

        assertThrows(MissingException.class, () -> briscola.getUser("Luigi"));
    }

    protected void testDeleteUser() throws MissingException, ServerOfflineException {
        briscola.deleteUser(mario.getName());
        assertThrows(MissingException.class, () -> briscola.getUser(mario.getName()));
    }
}
