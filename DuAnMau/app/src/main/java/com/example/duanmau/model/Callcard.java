package com.example.duanmau.model;

public class Callcard {
    private int ID;
    private String DATEIN;
    private String DATEOUT;
    private int IDBOOKS;
    private int IDMEMBERS;
    private String IDLIBRARIAN;

    public String getIDLIBRARIAN() {
        return IDLIBRARIAN;
    }

    public Callcard(int ID, String DATEIN, String DATEOUT, int IDBOOKS, int IDMEMBERS, String IDLIBRARIAN) {
        this.ID = ID;
        this.DATEIN = DATEIN;
        this.DATEOUT = DATEOUT;
        this.IDBOOKS = IDBOOKS;
        this.IDMEMBERS = IDMEMBERS;
        this.IDLIBRARIAN = IDLIBRARIAN;
    }

    public void setIDLIBRARIAN(String IDLIBRARIAN) {
        this.IDLIBRARIAN = IDLIBRARIAN;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDATEIN() {
        return DATEIN;
    }

    public void setDATEIN(String DATEIN) {
        this.DATEIN = DATEIN;
    }

    public String getDATEOUT() {
        return DATEOUT;
    }

    public void setDATEOUT(String DATEOUT) {
        this.DATEOUT = DATEOUT;
    }

    public int getIDBOOKS() {
        return IDBOOKS;
    }

    public void setIDBOOKS(int IDBOOKS) {
        this.IDBOOKS = IDBOOKS;
    }

    public int getIDMEMBERS() {
        return IDMEMBERS;
    }

    public void setIDMEMBERS(int IDMEMBERS) {
        this.IDMEMBERS = IDMEMBERS;
    }


    public Callcard(int ID, String DATEIN, String DATEOUT, int IDBOOKS, int IDMEMBERS) {
        this.ID = ID;
        this.DATEIN = DATEIN;
        this.DATEOUT = DATEOUT;
        this.IDBOOKS = IDBOOKS;
        this.IDMEMBERS = IDMEMBERS;
    }
}
