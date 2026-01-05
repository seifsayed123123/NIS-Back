package com.example.NIS.DTO;

public class Cell3GDto {

    private String parent_site_id;
    private String cell_name;
    private String cell_id;
    private String lac;
    private String downlink_freq;
    private String uplink_freq;
    private String power;
    private String sac;
    private String cell_status;
    private String rnc;

    // getters & setters
    public String getParent_site_id() { return parent_site_id; }
    public void setParent_site_id(String parent_site_id) { this.parent_site_id = parent_site_id; }

    public String getCell_name() { return cell_name; }
    public void setCell_name(String cell_name) { this.cell_name = cell_name; }

    public String getCell_id() { return cell_id; }
    public void setCell_id(String cell_id) { this.cell_id = cell_id; }

    public String getLac() { return lac; }
    public void setLac(String lac) { this.lac = lac; }

    public String getDownlink_freq() { return downlink_freq; }
    public void setDownlink_freq(String downlink_freq) { this.downlink_freq = downlink_freq; }

    public String getUplink_freq() { return uplink_freq; }
    public void setUplink_freq(String uplink_freq) { this.uplink_freq = uplink_freq; }

    public String getPower() { return power; }
    public void setPower(String power) { this.power = power; }

    public String getSac() { return sac; }
    public void setSac(String sac) { this.sac = sac; }

    public String getCell_status() { return cell_status; }
    public void setCell_status(String cell_status) { this.cell_status = cell_status; }

    public String getRnc() { return rnc; }
    public void setRnc(String rnc) { this.rnc = rnc; }
}
