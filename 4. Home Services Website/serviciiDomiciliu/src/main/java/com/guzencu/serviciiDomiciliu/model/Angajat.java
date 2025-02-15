package com.guzencu.serviciiDomiciliu.model;

public class Angajat {
    private int angajatID;
    private String nume;
    private String prenume;
    private String dataNastere;
    private int salariu;
    private String nrTelefon;

    public Angajat() {}

    public Angajat(int angajatID, String nume, String prenume, String dataNastere, int salariu, String nrTelefon){
        this.angajatID = angajatID;
        this.nume = nume;
        this.prenume = prenume;
        this.dataNastere = dataNastere;
        this.salariu = salariu;
        this.nrTelefon = nrTelefon;
    }
    public int getAngajatID() {
        return angajatID;
    }

    public void setAngajatID(int angajatID) {
        this.angajatID = angajatID;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getDataNastere() {
        return dataNastere;
    }

    public void setDataNastere(String dataNastere) {
        this.dataNastere = dataNastere;
    }

    public int getSalariu() {
        return salariu;
    }

    public void setSalariu(int salariu) {
        this.salariu = salariu;
    }

    public String getNrTelefon() {
        return nrTelefon;
    }

    public void setNrTelefon(String nrTelefon) {
        this.nrTelefon = nrTelefon;
    }

}
