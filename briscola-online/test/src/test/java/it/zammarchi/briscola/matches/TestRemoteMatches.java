package it.zammarchi.briscola.matches;

import it.zammarchi.briscola.client.RemoteBriscola;
import it.zammarchi.briscola.common.exceptions.ConflictException;
import it.zammarchi.briscola.common.exceptions.MissingException;
import it.zammarchi.briscola.common.exceptions.ServerOfflineException;
import it.zammarchi.briscola.server.BriscolaService;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestRemoteMatches extends AbstractTestMatches {

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
    protected void testStartMatch() throws MissingException, ConflictException, ServerOfflineException {
        super.testStartMatch();
    }

    @Test
    @Order(2)
    @Override
    protected void testGetMatch() throws MissingException, ServerOfflineException {
        super.testGetMatch();
    }

    @Test
    @Order(3)
    @Override
    protected void testGetAllMatches() throws ServerOfflineException {
        super.testGetAllMatches();
    }

    @Test
    @Order(4)
    @Override
    protected void testDeleteMatch() throws MissingException, ServerOfflineException {
        super.testDeleteMatch();
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @Nested
    protected class TestUserPlaysCard extends AbstractTestMatches.TestUserPlaysCard {

        @Test
        @Order(1)
        @Override
        protected void testFirstPlay() throws MissingException, ConflictException, ServerOfflineException {
            super.testFirstPlay();
        }

        @Test
        @Order(2)
        @Override
        protected void testSecondPlay() throws MissingException, ServerOfflineException {
            super.testSecondPlay();
        }

        @AfterAll
        public void tearDown() {
            service.stopService();
        }
    }
}
