package it.unipd.mtss.model;
import it.unipd.mtss.business.exception.UserException;
import java.time.*;

public class User{
    int id;
    String name, surname;
    LocalDate birthDate;

    public User(int id, String name, String surname, LocalDate birthDate) throws UserException{
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;

        if(birthDate.isBefore(LocalDate.now())){
            throw new UserException("birth date invalid");
        }
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }

    public LocalDate getBirthDate(){
        return birthDate;
    }

    public int getAge(){
        LocalDate today = LocalDate.now();
        return Period.between(birthDate, today).getYears();
    }

}