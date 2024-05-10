package com.example.duanmau.model;

public class Members {
    private int ID;
    private String NAME;
    private String DATE;
    private byte[] IMAGE;

    public Members(int ID, String NAME, String DATE, byte[] IMAGE) {
        this.ID = ID;
        this.NAME = NAME;
        this.DATE = DATE;
        this.IMAGE = IMAGE;
    }

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

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public byte[] getIMAGE() {
        return IMAGE;
    }

    public void setIMAGE(byte[] IMAGE) {
        this.IMAGE = IMAGE;
    }
}