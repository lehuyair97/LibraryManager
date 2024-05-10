package com.example.duanmau.model;

public class Category {
    private int ID;
    private String NAME;
    private byte[] IMG;


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

    public Category(int ID, String NAME, byte[] IMG) {
        this.ID = ID;
        this.NAME = NAME;
        this.IMG = IMG;
    }

    public byte[] getIMG() {
        return IMG;
    }

    public void setIMG(byte[] IMG) {
        this.IMG = IMG;
    }
}
