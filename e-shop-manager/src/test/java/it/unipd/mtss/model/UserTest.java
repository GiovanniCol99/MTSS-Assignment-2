package it.unipd.mtss.model;

import java.time.*;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import it.unipd.mtss.business.exception.UserException;

public class UserTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testUser() throws UserException {

        exceptionRule.expect(UserException.class);
        exceptionRule.expectMessage("birth date invalid");

        new User(1, "prova", "prova", LocalDate.of(1999, 6, 27));
        
    }
}
