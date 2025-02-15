package com.guzencu.serviciiDomiciliu.model;

public class Serviciu {
    private int serviciuID;
    private String numeServiciu;
    private String descriere;
    private int pretPeOra;
    private String numeImagine;

    public Serviciu(){}

    public Serviciu(int serviciuID, String numeServiciu, String descriere, int pretPeOra, String numeImagine){
        this.serviciuID = serviciuID;
        this.numeServiciu = numeServiciu;
        this.descriere = descriere;
        this.pretPeOra = pretPeOra;
        this.numeImagine = numeImagine;
    }

    public int getServiciuID() {
        return serviciuID;
    }

    public String getNumeServiciu() {
        return numeServiciu;
    }

    public void setNumeServiciu(String numeServiciu) {
        this.numeServiciu = numeServiciu;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public int getPretPeOra() {
        return pretPeOra;
    }

    public void setPretPeOra(int pretPeOra) {
        this.pretPeOra = pretPeOra;
    }

    public String getNumeImagine() {
        return numeImagine;
    }

    public void setNumeImagine(String numeImagine) {
        this.numeImagine = numeImagine;
    }
}
