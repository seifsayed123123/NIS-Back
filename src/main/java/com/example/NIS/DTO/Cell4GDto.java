package com.example.NIS.DTO;

public class Cell4GDto {

    private String parent_site_id;
    private String cell_name;
    private String cell_id;
    private String tac;
    private String cell_status;
    private String technology;
    private String downlink_freq;
    private String dl_bandwidth;
    private String mimo;

    // getters & setters
    public String getParent_site_id() { return parent_site_id; }
    public void setParent_site_id(String parent_site_id) { this.parent_site_id = parent_site_id; }

    public String getCell_name() { return cell_name; }
    public void setCell_name(String cell_name) { this.cell_name = cell_name; }

    public String getCell_id() { return cell_id; }
    public void setCell_id(String cell_id) { this.cell_id = cell_id; }

    public String getTac() { return tac; }
    public void setTac(String tac) { this.tac = tac; }

    public String getCell_status() { return cell_status; }
    public void setCell_status(String cell_status) { this.cell_status = cell_status; }

    public String getTechnology() { return technology; }
    public void setTechnology(String technology) { this.technology = technology; }

    public String getDownlink_freq() { return downlink_freq; }
    public void setDownlink_freq(String downlink_freq) { this.downlink_freq = downlink_freq; }

    public String getDl_bandwidth() { return dl_bandwidth; }
    public void setDl_bandwidth(String dl_bandwidth) { this.dl_bandwidth = dl_bandwidth; }

    public String getMimo() { return mimo; }
    public void setMimo(String mimo) { this.mimo = mimo; }
}
