package com.hgm.haritagenelmudurlugu.Makale;

public class Makaleler {
    private String makale_id;
    private String makale_adi;
    private String makale_yil;
    private String makale_sayi;
    private String makale_yazar;
    private String makale_url;

    public Makaleler() {
    }

    public Makaleler(String makale_id, String makale_adi, String makale_yil, String makale_sayi, String makale_yazar, String makale_url) {
        this.makale_id = makale_id;
        this.makale_adi = makale_adi;
        this.makale_yil = makale_yil;
        this.makale_sayi = makale_sayi;
        this.makale_yazar = makale_yazar;
        this.makale_url = makale_url;
    }

    public String getMakale_id() {
        return makale_id;
    }

    public void setMakale_id(String makale_id) {
        this.makale_id = makale_id;
    }

    public String getMakale_adi() {
        return makale_adi;
    }

    public void setMakale_adi(String makale_adi) {
        this.makale_adi = makale_adi;
    }

    public String getMakale_yil() {
        return makale_yil;
    }

    public void setMakale_yil(String makale_yil) {
        this.makale_yil = makale_yil;
    }

    public String getMakale_sayi() {
        return makale_sayi;
    }

    public void setMakale_sayi(String makale_sayi) {
        this.makale_sayi = makale_sayi;
    }

    public String getMakale_yazar() {
        return makale_yazar;
    }

    public void setMakale_yazar(String makale_yazar) {
        this.makale_yazar = makale_yazar;
    }

    public String getMakale_url() {
        return makale_url;
    }

    public void setMakale_url(String makale_url) {
        this.makale_url = makale_url;
    }
}
