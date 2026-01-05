package com.example.NIS.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter

@Entity
@Table(name = "necd_sitetechnology")
public class NECD_SITETECHNOLOGY {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recid")
    private Long recid;


    @Column(name = "legacyid")
    private String legacyId;

    @Column(name = "siteid")
    private Long siteId;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private Integer status;

    @Column(name = "technologytype_lookup")
    private Integer technologyLookup;

    @Column(name = "mscid")
    private String mscId;

    @Column(name = "mscloc")
    private String mscLoc;

    @Column(name = "totalcapacity")
    private Integer totalCapacity;

    @Column(name = "cella")
    private Integer cellA;

    @Column(name = "cellb")
    private Integer cellB;

    @Column(name = "cellc")
    private Integer cellC;

    @Column(name = "celld")
    private Integer cellD;

    @Column(name = "switch_loc")
    private Integer switchLoc;

    @Column(name = "external_id")
    private String externalId;

    @Column(name = "optionid")
    private Integer optionId;

    @Column(name = "capacityid")
    private Integer capacityId;

    @Column(name = "hasservice")
    private Integer hasService;

    @Column(name = "threegcapacity")
    private Integer threeGCapacity;

    @Column(name = "notes")
    private String notes;

    @Column(name = "hspa")
    private String hspa;

    @Column(name = "powertype")
    private String powerType;

    @Column(name = "hspatechtype")
    private String hspaTechType;

    @Column(name = "ontargetdate")
    private LocalDateTime onTargetDate;

    @Column(name = "notes2")
    private String notes2;

    @Column(name = "serviceid")
    private Integer serviceId;

    @Column(name = "siteoption")
    private String siteOption;

    @Column(name = "sitename")
    private String siteName;

    @Column(name = "onairdate")
    private LocalDateTime onAirDate;

    @Column(name = "plannedintegrationdate")
    private LocalDateTime plannedIntegrationDate;

    @Column(name = "actualintegrationdate")
    private LocalDateTime actualIntegrationDate;

    @Column(name = "lac")
    private String lac;

    @Column(name = "trx_configuration")
    private String trxConfiguration;

    @Column(name = "modernization")
    private String modernization;

    @Column(name = "vendor")
    private Integer vendor;

    @Column(name = "is_swapped")
    private Integer isSwapped;

    @Column(name = "swapped_date")
    private LocalDateTime swappedDate;

    @Column(name = "flag")
    private Integer flag;

    @Column(name = "netq_status")
    private String netqStatus;

    @Column(name = "netq_status_reason")
    private String netqStatusReason;

    @Column(name = "project_netq")
    private String projectNetq;

    @Column(name = "old_nts_status")
    private Integer oldNtsStatus;

    @Column(name = "old_nts_vendor")
    private Integer oldNtsVendor;

    @Column(name = "old_nts_mscid")
    private String oldNtsMscId;

    @Column(name = "internal_id")
    private Long internalId;

    @Column(name = "rollback")
    private String rollback;

    @Column(name = "filename")
    private String filename;

    @Column(name = "inserted_sysdate")
    private LocalDateTime insertedSysdate;

    @Column(name = "enodeb_name")
    private String enodebName;

    @Column(name = "tra_export")
    private Integer traExport;
}
