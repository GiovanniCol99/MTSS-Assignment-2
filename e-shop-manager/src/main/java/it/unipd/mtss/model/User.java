package it.unipd.mtss.model;
import java.time.LocalDate;
import it.unipd.mtss.business.exception.BillException;

public class User{
    int id;
    String name, surname;
    LocalDate birthDate;

    public User(int id, String name, String surname, LocalDate birthDate) throws BillException{
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;

        if(birthDate == null){
            throw new BillException("birth date cannot be null");
        } 
        
        if(birthDate.isBefore(LocalDate.now())){
            throw new BillException("birth date invalid");
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

}