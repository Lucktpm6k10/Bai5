package com.example.vanluc.bai5.model;

public class Contact {
    private int ID;
    private String name;
    private String number;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Contact() {

    }

    public Contact(int ID, String name, String number) {
        this.ID = ID;
        this.name = name;
        this.number = number;
    }
}
