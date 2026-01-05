package com.example.NIS.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter


@Entity
@Table(name = "necd_carrier_cell")
public class NECD_CARRIER_CELL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recid")
    private Long recid;


    @Column(name = "cell_name")
    private String cellName;

    @Column(name = "cell_type")
    private String cellType;

    @Column(name = "cell_letter_id")
    private Integer cellLetterId;

    @Column(name = "carrier_id")
    private Integer carrierId;

    @Column(name = "antenna_id")
    private Integer antennaId;

    @Column(name = "electrical_tilt")
    private Integer electricalTilt;

    @Column(name = "tma")
    private Integer tma;

    @Column(name = "cell_id")
    private String cellId;

    @Column(name = "arfcn")
    private Integer arfcn;

    @Column(name = "uplink_freq")
    private Integer uplinkFreq;

    @Column(name = "downlink_freq")
    private Integer downlinkFreq;

    @Column(name = "band")
    private String band;

    @Column(name = "antbw")
    private String antbw;

    @Column(name = "bandwidth")
    private String bandwidth;

    @Column(name = "cdmagroupstart")
    private String cdmaGroupStart;

    @Column(name = "cell_enode_id")
    private String cellEnodeId;

    @Column(name = "cgi")
    private String cgi;

    @Column(name = "configuration")
    private String configuration;

    @Column(name = "dlpuscallpermbase")
    private String dlpUscAllPermBase;

    @Column(name = "dlpuscallprbsid")
    private String dlpUscAllPrbsId;

    @Column(name = "dlpuscallstcpermbase")
    private String dlpUscAllStcPermBase;

    @Column(name = "dlpuscallstcprbsid")
    private String dlpUscAllStcPrbsId;

    @Column(name = "dlpuscstcpermbase")
    private String dlpUscStcPermBase;

    @Column(name = "dlpuscstcprbsid")
    private String dlpUscStcPrbsId;

    @Column(name = "eirp")
    private String eirp;

    @Column(name = "freq_no")
    private String freqNo;

    @Column(name = "groundheight")
    private String groundHeight;

    @Column(name = "lac")
    private String lac;

    @Column(name = "mcc")
    private String mcc;

    @Column(name = "mmesgw")
    private String mmeSgw;

    @Column(name = "mnc")
    private String mnc;

    @Column(name = "preamble_index")
    private String preambleIndex;

    @Column(name = "rac")
    private String rac;

    @Column(name = "siteconfiguration")
    private String siteConfiguration;

    @Column(name = "sitename")
    private String siteName;

    @Column(name = "ulpuscallpermbase")
    private String ulpUscAllPermBase;

    @Column(name = "ulpuscpermbase")
    private String ulpUscPermBase;

    @Column(name = "new_bsid")
    private String newBsid;

    @Column(name = "internal_id")
    private Long internalId;

    @Column(name = "rollback")
    private String rollback;

    @Column(name = "logg")
    private String logg;

    @Column(name = "localcellid")
    private Integer localCellId;

    @Column(name = "inserted_sysdate")
    private LocalDateTime insertedSysdate;

    @Column(name = "filename")
    private String filename;

    @Column(name = "isnew")
    private Integer isNew;

    @Column(name = "cell_band")
    private Integer cellBand;

    @Column(name = "status")
    private Integer status;

    @Column(name = "tra_export")
    private Integer traExport;

    @Column(name = "down_tilt_mechanical")
    private Integer downTiltMechanical;

    @Column(name = "reference_signal_power")
    private String referenceSignalPower;

    @Column(name = "cpich")
    private String cpich;

    @Column(name = "cell_radius")
    private String cellRadius;

    @Column(name = "wcel_index")
    private String wcelIndex;

    @Column(name = "feeder_atten")
    private String feederAtten;

    @Column(name = "rachrootseq")
    private String rachRootSeq;

    @Column(name = "bcchpwr")
    private String bcchPwr;

    @Column(name = "dlbandwidth")
    private String dlBandwidth;

    @Column(name = "neighbours")
    private String neighbours;

    @Column(name = "frequency")
    private String frequency;

    @Column(name = "notes")
    private String notes;

    @Column(name = "sa_export")
    private Integer saExport;

    @Column(name = "nodebid")
    private String nodebId;

    @Column(name = "ci")
    private String ci;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "maxmimo")
    private String maxMimo;

    @Column(name = "enodeb_name")
    private String enodebName;

    @Column(name = "sac")
    private String sac;

    @Column(name = "txrxmode")
    private String txRxMode;

    @Column(name = "nrducellid")
    private String nrDuCellId;

    @Column(name = "dat_chanel_power")
    private String datChannelPower;

    @Column(name = "maxtxpower")
    private String maxTxPower;
}

