package com.hgm.haritagenelmudurlugu.SiteActivities.Utility;

public class BilimModel {
    private String id;
    private String unvan;
    private String adiSoyadi;
    private String kurumu;
    private String bransi;

    public BilimModel() {
    }

    public BilimModel(String id, String unvan, String adiSoyadi, String kurumu, String bransi) {
        this.id = id;
        this.unvan = unvan;
        this.adiSoyadi = adiSoyadi;
        this.kurumu = kurumu;
        this.bransi = bransi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnvan() {
        return unvan;
    }

    public void setUnvan(String unvan) {
        this.unvan = unvan;
    }

    public String getAdiSoyadi() {
        return adiSoyadi;
    }

    public void setAdiSoyadi(String adiSoyadi) {
        this.adiSoyadi = adiSoyadi;
    }

    public String getKurumu() {
        return kurumu;
    }

    public void setKurumu(String kurumu) {
        this.kurumu = kurumu;
    }

    public String getBransi() {
        return bransi;
    }

    public void setBransi(String bransi) {
        this.bransi = bransi;
    }
}
