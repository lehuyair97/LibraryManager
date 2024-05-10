package com.example.duanmau.model;

public class Books {
    private int ID;
    private int IDCATEGORY;
    private String NAME;
    private int PRICE;
    private  byte[] IMAGE;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getIDCATEGORY() {
        return IDCATEGORY;
    }

    public void setIDCATEGORY(int IDCATEGORY) {
        this.IDCATEGORY = IDCATEGORY;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public int getPRICE() {
        return PRICE;
    }

    public void setPRICE(int PRICE) {
        this.PRICE = PRICE;
    }



    public byte[] getIMAGE() {
        return IMAGE;
    }

    public void setIMAGE(byte[] IMAGE) {
        this.IMAGE = IMAGE;
    }

    public Books(int ID,String NAME, int PRICE, int IDCATEGORY, byte[] IMAGE) {
        this.ID = ID;
        this.IDCATEGORY = IDCATEGORY;
        this.NAME = NAME;
        this.PRICE = PRICE;
        this.IMAGE = IMAGE;
    }
}
