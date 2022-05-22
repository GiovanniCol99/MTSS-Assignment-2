////////////////////////////////////////////////////////////////////
// [GIOVANNI] [MORETTI] [1217655]
// [GIOVANNI] [COLANGELO] [2008070]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.model;
import java.lang.IllegalArgumentException;
import java.time.LocalDate;
import java.time.Period;

public class User{
    int id;
    String name, surname;
    LocalDate birthDate;

    public User (int id, String name, String surname, LocalDate birthDate) throws IllegalArgumentException{
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;

        if(birthDate.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("birth date invalid");
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