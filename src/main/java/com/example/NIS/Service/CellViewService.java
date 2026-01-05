package com.example.NIS.Service;

import com.example.NIS.Repository.NecdCarrierCellRepository;
import com.example.NIS.Repository.NecdSitetechnologyRepository;
import com.example.NIS.dtoforview.CellViewDto;
import com.example.NIS.model.NECD_CARRIER_CELL;
import com.example.NIS.model.NECD_SITETECHNOLOGY;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CellViewService {

    private final NecdCarrierCellRepository carrierRepository;
    private final NecdSitetechnologyRepository siteRepository;

    public CellViewService(NecdCarrierCellRepository carrierRepository,
                           NecdSitetechnologyRepository siteRepository) {
        this.carrierRepository = carrierRepository;
        this.siteRepository = siteRepository;
    }

    // ===================== MAIN VIEW (CACHED) =====================
    @Cacheable(
            value = "cellView",
            key = "'page=' + #page + ',size=' + #size"
    )
    public Page<CellViewDto> getCells(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<NECD_CARRIER_CELL> cellsPage =
                carrierRepository.findAll(pageable);

        // 🔹 sites متكاش
        List<NECD_SITETECHNOLOGY> allSites = getAllSitesCached();

        List<CellViewDto> dtos = cellsPage.stream()
                .map(cell -> {

                    CellViewDto dto = new CellViewDto();

                    // ===== CELL DATA =====
                    dto.setRecid(cell.getRecid());
                    dto.setCellName(cell.getCellName());
                    dto.setCellId(cell.getCellId());
                    dto.setLac(cell.getLac());
                    dto.setBand(cell.getBand());
                    dto.setMcc(cell.getMcc());
                    dto.setMnc(cell.getMnc());

                    // ===== SITE MATCH (SAFE LOOP) =====
                    if (cell.getLac() != null) {
                        for (NECD_SITETECHNOLOGY site : allSites) {
                            if (site.getLac() != null &&
                                    site.getLac().equals(cell.getLac())) {

                                dto.setParentSiteId(site.getName());
                                dto.setBscName(site.getMscId());
                                break;
                            }
                        }
                    }

                    return dto;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(
                dtos,
                pageable,
                cellsPage.getTotalElements()
        );
    }

    // ===================== SITES CACHE =====================
    @Cacheable("allSites")
    public List<NECD_SITETECHNOLOGY> getAllSitesCached() {
        return siteRepository.findAll();
    }
}
