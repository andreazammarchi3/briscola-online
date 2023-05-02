package it.zammarchi.briscola.users;

import it.zammarchi.briscola.client.RemoteBriscola;
import it.zammarchi.briscola.common.exceptions.ConflictException;
import it.zammarchi.briscola.common.exceptions.MissingException;
import it.zammarchi.briscola.common.exceptions.ServerOfflineException;
import it.zammarchi.briscola.server.BriscolaService;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestRemoteUsers extends AbstractTestUsers {

    private static final int PORT = 7777;
    private BriscolaService service;

    @BeforeAll
    @Override
    public void setUp() {
        service = new BriscolaService(PORT);
        service.startService();
        this.briscola = new RemoteBriscola("localhost", PORT);
    }

    @Test
    @Order(1)
    @Override
    public void testCreateUser() throws ConflictException, IllegalArgumentException, ServerOfflineException {
        super.testCreateUser();
    }

    @Test
    @Order(2)
    @Override
    public void testGetUser() throws MissingException, ServerOfflineException {
        super.testGetUser();
    }

    @Test
    @Order(3)
    @Override
    public void testDeleteUser() throws MissingException, ServerOfflineException {
        super.testDeleteUser();
    }

    @Test
    @Order(4)
    public void testServerOffline() {
        service.stopService();
        assertThrows(ServerOfflineException.class, () -> briscola.createUser("Mario"));
    }
}
