package com.example.NIS.dtoforview;

public class CellViewDto {

    private Long recid;

    // الحقول اللي في XmlService
    private String cellName;
    private String cellId;
    private String lac;
    private String band;
    private String mcc;
    private String mnc;

    // من الـ site
    private String parentSiteId;  // ده الـ name في الـ site
    private String bscName;       // ده الـ mscId في الـ site

    // constructor فاضي
    public CellViewDto() {
    }

    // constructor للـ query
    public CellViewDto(Long recid, String cellName, String cellId, String lac,
                       String band, String mcc, String mnc,
                       String parentSiteId, String bscName) {
        this.recid = recid;
        this.cellName = cellName;
        this.cellId = cellId;
        this.lac = lac;
        this.band = band;
        this.mcc = mcc;
        this.mnc = mnc;
        this.parentSiteId = parentSiteId;
        this.bscName = bscName;
    }

    // Getters & Setters لكل حاجة
    public Long getRecid() { return recid; }
    public void setRecid(Long recid) { this.recid = recid; }

    public String getCellName() { return cellName; }
    public void setCellName(String cellName) { this.cellName = cellName; }

    public String getCellId() { return cellId; }
    public void setCellId(String cellId) { this.cellId = cellId; }

    public String getLac() { return lac; }
    public void setLac(String lac) { this.lac = lac; }

    public String getBand() { return band; }
    public void setBand(String band) { this.band = band; }

    public String getMcc() { return mcc; }
    public void setMcc(String mcc) { this.mcc = mcc; }

    public String getMnc() { return mnc; }
    public void setMnc(String mnc) { this.mnc = mnc; }

    public String getParentSiteId() { return parentSiteId; }
    public void setParentSiteId(String parentSiteId) { this.parentSiteId = parentSiteId; }

    public String getBscName() { return bscName; }
    public void setBscName(String bscName) { this.bscName = bscName; }
}