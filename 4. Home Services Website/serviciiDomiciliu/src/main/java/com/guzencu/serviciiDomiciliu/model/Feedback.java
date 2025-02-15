package com.guzencu.serviciiDomiciliu.model;

public class Feedback {
    private int programareId;
    private String experienta;
    private String descriere;
    private int nota;

    public Feedback() {}

    public Feedback(int programareId, String experienta, String descriere, int nota){
        this.programareId = programareId;
        this.experienta = experienta;
        this.descriere = descriere;
        this.nota = nota;
    }

    public int getProgramareId() {
        return programareId;
    }

    public void setProgramareId(int programareId) {
        this.programareId = programareId;
    }

    public String getExperienta() {
        return experienta;
    }

    public void setExperienta(String experienta) {
        this.experienta = experienta;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }
}
