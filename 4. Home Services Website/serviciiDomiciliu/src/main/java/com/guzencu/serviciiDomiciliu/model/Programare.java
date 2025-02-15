package com.guzencu.serviciiDomiciliu.model;

public class Programare {
    private int programareId;
    private int serviciuPrestatDeAngajatId;
    private int userId;
    private String dataProgramare;
    private String statusProgramare;
    private String numeServiciu;
    private String numeAngajat;
    private String prenumeAngajat;
    private String nrTelefonAngajat;

    public Programare() {}

    public Programare(int programareId, int serviciuPrestatDeAngajatId, int userId, String dataProgramare, String statusProgramare, String numeServiciu,
                      String numeAngajat, String prenumeAngajat, String nrTelefonAngajat){
        this.programareId = programareId;
        this.serviciuPrestatDeAngajatId = serviciuPrestatDeAngajatId;
        this.userId = userId;
        this.dataProgramare = dataProgramare;
        this.statusProgramare = statusProgramare;
        this.numeServiciu = numeServiciu;
        this. numeAngajat = numeAngajat;
        this.prenumeAngajat = prenumeAngajat;
        this.nrTelefonAngajat = nrTelefonAngajat;
    }

    public int getProgramareId() {
        return programareId;
    }

    public void setProgramareId(int programareId) {
        this.programareId = programareId;
    }

    public int getServiciuPrestatDeAngajatId() {
        return serviciuPrestatDeAngajatId;
    }

    public void setServiciuPrestatDeAngajatId(int serviciuPrestatDeAngajatId) {
        this.serviciuPrestatDeAngajatId = serviciuPrestatDeAngajatId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDataProgramare() {
        return dataProgramare;
    }

    public void setDataProgramare(String dataProgramare) {
        this.dataProgramare = dataProgramare;
    }

    public String getStatusProgramare() {
        return statusProgramare;
    }

    public void setStatusProgramare(String statusProgramare) {
        this.statusProgramare = statusProgramare;
    }

    public String getNumeServiciu() {
        return numeServiciu;
    }

    public void setNumeServiciu(String numeServiciu) {
        this.numeServiciu = numeServiciu;
    }

    public String getNumeAngajat() {
        return numeAngajat;
    }

    public void setNumeAngajat(String numeAngajat) {
        this.numeAngajat = numeAngajat;
    }

    public String getPrenumeAngajat() {
        return prenumeAngajat;
    }

    public void setPrenumeAngajat(String prenumeAngajat) {
        this.prenumeAngajat = prenumeAngajat;
    }

    public String getNrTelefonAngajat() {
        return nrTelefonAngajat;
    }

    public void setNrTelefonAngajat(String nrTelefonAngajat) {
        this.nrTelefonAngajat = nrTelefonAngajat;
    }
}
