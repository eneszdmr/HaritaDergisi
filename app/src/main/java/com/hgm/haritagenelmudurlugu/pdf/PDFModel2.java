package com.hgm.haritagenelmudurlugu.pdf;

public class PDFModel2 {
    private String pdfid;
    private String pdfName;
    private String pdfDesc;
    private String pdfUrl;


    public PDFModel2() {
    }

    public PDFModel2(String pdfid, String pdfName, String pdfDesc, String pdfUrl) {
        this.pdfid = pdfid;
        this.pdfName = pdfName;
        this.pdfDesc = pdfDesc;
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

    public String getPdfDesc() {
        return pdfDesc;
    }

    public void setPdfDesc(String pdfDesc) {
        this.pdfDesc = pdfDesc;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }
}
