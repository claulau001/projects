package com.guzencu.serviciiDomiciliu.model;

public class User {
    private int userId;
    private String nume;
    private String prenume;
    private String sex;
    private String dataNastere;
    private String judet;
    private String oras;
    private String strada;
    private int numarStrada;
    private String bloc;
    private String scara;
    private int apartament;
    private String email;
    private String numarTelefon;
    private String username;
    private String password;
    private boolean isAdmin;

    public User() {}

    public User(
            int userId,
            String nume,
            String prenume,
            String sex,
            String dataNastere,
            String judet,
            String oras,
            String strada,
            int numarStrada,
            String bloc,
            String scara,
            int apartament,
            String email,
            String numarTelefon,
            String username,
            String password,
            boolean isAdmin
    ){
        this.userId = userId;
        this.nume = nume;
        this.prenume = prenume;
        this.sex = sex;
        this.dataNastere = dataNastere;
        this.judet = judet;
        this.oras = oras;
        this.strada = strada;
        this.numarStrada = numarStrada;
        this.bloc = bloc;
        this.scara = scara;
        this.apartament = apartament;
        this.email = email;
        this.numarTelefon = numarTelefon;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public int getUserId() {
        return userId;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDataNastere() {
        return dataNastere;
    }

    public void setDataNastere(String dataNastere) {
        this.dataNastere = dataNastere;
    }

    public String getJudet() {
        return judet;
    }

    public void setJudet(String judet) {
        this.judet = judet;
    }

    public String getOras() {
        return oras;
    }

    public void setOras(String oras) {
        this.oras = oras;
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }

    public int getNumarStrada() {
        return numarStrada;
    }

    public void setNumarStrada(int numarStrada) {
        this.numarStrada = numarStrada;
    }

    public String getBloc() {
        return bloc;
    }

    public void setBloc(String bloc) {
        this.bloc = bloc;
    }

    public String getScara() {
        return scara;
    }

    public void setScara(String scara) {
        this.scara = scara;
    }

    public int getApartament() {
        return apartament;
    }

    public void setApartament(int apartament) {
        this.apartament = apartament;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumarTelefon() {
        return numarTelefon;
    }

    public void setNumarTelefon(String numarTelefon) {
        this.numarTelefon = numarTelefon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
