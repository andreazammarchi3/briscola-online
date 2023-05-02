package it.zammarchi.briscola.users;

import it.zammarchi.briscola.common.LocalBriscola;
import it.zammarchi.briscola.common.exceptions.ConflictException;
import it.zammarchi.briscola.common.exceptions.MissingException;
import it.zammarchi.briscola.common.exceptions.ServerOfflineException;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestLocalUsers extends AbstractTestUsers {

    @BeforeAll
    @Override
    public void setUp() {
        this.briscola = new LocalBriscola();
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
}
