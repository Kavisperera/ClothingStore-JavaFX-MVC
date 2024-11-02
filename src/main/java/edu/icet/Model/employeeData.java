package edu.icet.Model;

import java.sql.Date;

public class employeeData {

    private final String employeeeId;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String gender;
    private final Date date;

    public employeeData(String employeeeId,String password,String firstName,String lastName,String gender,Date date){
        this.employeeeId = employeeeId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.date = date;
    }

    public String getEmployeeId() {
        return employeeeId;
    }

    public String getPassword(){
        return password;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getGender(){
        return gender;
    }
    public Date getDate(){
        return date;
    }

}
