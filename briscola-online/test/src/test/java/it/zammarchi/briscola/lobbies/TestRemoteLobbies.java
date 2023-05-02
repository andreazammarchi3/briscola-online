package it.zammarchi.briscola.lobbies;

import it.zammarchi.briscola.client.RemoteBriscola;
import it.zammarchi.briscola.common.exceptions.MissingException;
import it.zammarchi.briscola.common.exceptions.ServerOfflineException;
import it.zammarchi.briscola.server.BriscolaService;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestRemoteLobbies extends AbstractTestLobbies {

    private static final int PORT = 7777;
    private BriscolaService service;

    @BeforeAll
    @Override
    public void setUp() {
        service = new BriscolaService(PORT);
        service.startService();
        this.briscola = new RemoteBriscola("localhost", PORT);
        createInstance();
    }

    @Test
    @Order(1)
    @Override
    protected void testCreateLobby() {
        super.testCreateLobby();
    }

    @Test
    @Order(2)
    @Override
    protected void testGetLobby() throws MissingException, ServerOfflineException {
        super.testGetLobby();
    }

    @Test
    @Order(3)
    @Override
    protected void testGetAllLobbies() throws ServerOfflineException {
        super.testGetAllLobbies();
    }

    @Test
    @Order(4)
    @Override
    protected void testDeleteLobby() throws MissingException, ServerOfflineException {
        super.testDeleteLobby();
    }

    @Test
    @Order(5)
    @Override
    protected void testJoinUserToLobby() throws MissingException, ServerOfflineException {
        super.testJoinUserToLobby();
    }

    @Test
    @Order(6)
    @Override
    protected void testDeleteUserFromLobby() throws MissingException, ServerOfflineException {
        super.testDeleteUserFromLobby();
        service.stopService();
    }
}
