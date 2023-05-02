package it.zammarchi.briscola.matches;

import it.zammarchi.briscola.common.LocalBriscola;
import it.zammarchi.briscola.common.exceptions.ConflictException;
import it.zammarchi.briscola.common.exceptions.MissingException;
import it.zammarchi.briscola.common.exceptions.ServerOfflineException;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestLocalMatches extends AbstractTestMatches {

    @BeforeAll
    @Override
    public void setUp() {
        this.briscola = new LocalBriscola();
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
    }
}
