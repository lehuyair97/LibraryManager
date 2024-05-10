package com.example.duanmau.model;

public class T10BooksBestsellers {
    private int ID;
    private String NAME;
    private int AMOUNT;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public int getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(int AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public T10BooksBestsellers(int ID, String NAME, int AMOUNT) {
        this.ID = ID;
        this.NAME = NAME;
        this.AMOUNT = AMOUNT;
    }
}
