package com.proyecto.ubicua.apecapp.Modelo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by RODIE on 07-Jun-16.
 */
public class Student implements Serializable {


    private String namestudent;
    private String regnumber;
    private Date birth;
    private String address;
    private String grade;
    private String phone;

    public Student(){}

    public String getNamestudent() {
        return namestudent;
    }

    public void setNamestudent(String namestudent) {
        this.namestudent = namestudent;
    }

    public String getRegnumber() {
        return regnumber;
    }

    public void setRegnumber(String regnumber) {
        this.regnumber = regnumber;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
