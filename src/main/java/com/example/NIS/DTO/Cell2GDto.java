package com.example.NIS.DTO;

public class Cell2GDto {

    // ---- SITE ----
    private String parent_site_id;
    private String bsc_name;
    private String lac;

    // ---- CELL ----
    private String cell_name;
    private String cell_id;
    private String band;
    private String mcc;
    private String mnc;

    // getters & setters
    public String getParent_site_id() {
        return parent_site_id;
    }

    public void setParent_site_id(String parent_site_id) {
        this.parent_site_id = parent_site_id;
    }

    public String getBsc_name() {
        return bsc_name;
    }

    public void setBsc_name(String bsc_name) {
        this.bsc_name = bsc_name;
    }

    public String getLac() {
        return lac;
    }

    public void setLac(String lac) {
        this.lac = lac; 
    }

    public String getCell_name() {
        return cell_name;
    }

    public void setCell_name(String cell_name) {
        this.cell_name = cell_name;
    }

    public String getCell_id() {
        return cell_id;
    }

    public void setCell_id(String cell_id) {
        this.cell_id = cell_id;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }
}
