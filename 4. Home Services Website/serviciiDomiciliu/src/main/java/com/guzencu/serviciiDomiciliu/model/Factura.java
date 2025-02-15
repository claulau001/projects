package com.guzencu.serviciiDomiciliu.model;

public class Factura {
    private int facturaId;
    private int userId;
    private String dataFactura;
    private String tipPlata;

    public Factura() {}

    public Factura(int facturaId, int userId, String dataFactura, String tipPlata){
        this.facturaId = facturaId;
        this.userId = userId;
        this.dataFactura = dataFactura;
        this.tipPlata = tipPlata;
    }

    public int getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDataFactura() {
        return dataFactura;
    }

    public void setDataFactura(String dataFactura) {
        this.dataFactura = dataFactura;
    }

    public String getTipPlata() {
        return tipPlata;
    }

    public void setTipPlata(String tipPlata) {
        this.tipPlata = tipPlata;
    }
}
