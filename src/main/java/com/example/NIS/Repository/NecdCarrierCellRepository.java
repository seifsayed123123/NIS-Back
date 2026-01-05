package com.example.NIS.Repository;

import com.example.NIS.dtoforview.CellViewDto;
import com.example.NIS.model.NECD_CARRIER_CELL;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NecdCarrierCellRepository extends JpaRepository<NECD_CARRIER_CELL, Long> {

    @Query(value = """
        SELECT 
            c.recid,
            c.cell_name as cellName,
            c.cell_id as cellId,
            c.lac,
            c.band,
            c.mcc,
            c.mnc,
            s.name as parentSiteId,
            s.mscid as bscName
        FROM necd_carrier_cell c
        LEFT JOIN necd_sitetechnology s ON c.lac = s.lac
        WHERE c.cell_name IS NOT NULL
        ORDER BY c.inserted_sysdate DESC
        """,
            countQuery = "SELECT COUNT(*) FROM necd_carrier_cell c WHERE c.cell_name IS NOT NULL",
            nativeQuery = true)
    Page<CellViewDto> findCellViewData(Pageable pageable);
}