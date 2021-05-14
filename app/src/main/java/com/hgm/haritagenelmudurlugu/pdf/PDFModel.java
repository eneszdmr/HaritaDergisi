package com.hgm.haritagenelmudurlugu.pdf;

public class PDFModel {
    private String pdfid;
    private String pdfName;
    private String pdfTarih;
    private String pdfUrl;



    public PDFModel() {
    }


    public PDFModel(String pdfid, String pdfName,String pdfTarih, String pdfUrl) {
        this.pdfid = pdfid;
        this.pdfName = pdfName;
        this.pdfTarih=pdfTarih;
        this.pdfUrl = pdfUrl;
    }

    public String getPdfid() {
        return pdfid;
    }

    public void setPdfid(String pdfid) {
        this.pdfid = pdfid;
    }

    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }

    public String getPdfTarih() {
        return pdfTarih;
    }

    public void setPdfTarih(String pdfTarih) {
        this.pdfTarih = pdfTarih;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }
}
