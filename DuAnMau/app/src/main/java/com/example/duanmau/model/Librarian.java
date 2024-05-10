package com.example.duanmau.model;

public class Librarian {
    private String ID;
    private String NAME;
    private String PASSWORD;
    private byte[] AVATAR;

    public Librarian(String ID, String NAME, String PASSWORD, byte[] AVATAR) {
        this.ID = ID;
        this.NAME = NAME;
        this.PASSWORD = PASSWORD;
        this.AVATAR = AVATAR;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public byte[] getAVATAR() {
        return AVATAR;
    }

    public void setAVATAR(byte[] AVATAR) {
        this.AVATAR = AVATAR;
    }
}
