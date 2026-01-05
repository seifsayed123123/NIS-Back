package com.example.NIS.projection;

public interface CellViewProjection {

    String getSiteName();
    String getBscId();
    Integer getTechType();

    String getCellName();
    String getCellId();
    String getLac();
    String getBand();

    String getUplinkFreq();
    String getDownlinkFreq();
    String getBandwidth();

    Integer getStatus();
}
