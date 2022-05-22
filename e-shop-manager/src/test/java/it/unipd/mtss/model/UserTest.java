package it.unipd.mtss.model;

import java.time.*;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import java.lang.IllegalArgumentException;;

public class UserTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testUserInvalidbirthDate(){

        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("birth date invalid");

        new User(1, "prova", "prova", LocalDate.of(2022, 12,1));
        
    }
}
