package it.unipd.mtss.model;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

import java.lang.IllegalArgumentException;;

public class UserTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test(timeout = 500)
    public void testUserInvalidbirthDate(){

        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("birth date invalid");

        new User(1, "prova", "prova", LocalDate.of(2022, 12,1));
    }

    @Test(timeout = 500)
    public void testUserConstructor(){
        LocalDate dataNascita = LocalDate.of(2000, 1, 1);
        User utente = new User(1, "nome", "cognome", dataNascita);
        assertEquals(utente.getId(), 1);
        assertEquals(utente.getName(), "nome");
        assertEquals(utente.getSurname(), "cognome");
        assertEquals(utente.getBirthDate(), dataNascita);
    }

    @Test(timeout = 500)
    public void testGetAge(){
        LocalDate today = LocalDate.now();
        LocalDate twentyYearsAgo = LocalDate.of(today.getYear()-20, today.getMonthValue(), today.getDayOfMonth());
        User utente = new User(1, "nome", "cognome", twentyYearsAgo);

        assertEquals(20, utente.getAge());
    }
}
