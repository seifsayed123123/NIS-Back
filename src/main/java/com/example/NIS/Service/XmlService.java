/*
package com.example.NIS.Service;

import com.example.NIS.Repository.NecdCarrierCellRepository;
import com.example.NIS.Repository.NecdSitetechnologyRepository;
import com.example.NIS.model.NECD_CARRIER_CELL;
import com.example.NIS.model.NECD_SITETECHNOLOGY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class XmlService {

    @Autowired
    private NecdCarrierCellRepository carrierRepo;

    @Autowired
    private NecdSitetechnologyRepository siteRepo;

    @Autowired
    private Xml2GParser xml2GParser;

    @Autowired
    private Xml3GParser xml3GParser;

    @Autowired
    private Xml4GParser xml4GParser;

    // ===================== MAIN =====================
    public List<CellDto> extract(MultipartFile file, Integer type) throws Exception {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("XML file is required");
        }

        File xmlFile = convertToFile(file);

        return switch (type) {
            case 2 -> handle2G(xmlFile);
            case 3 -> handle3G(xmlFile);
            case 4 -> handle4G(xmlFile);
            default -> throw new IllegalArgumentException("Allowed types: 2 , 3 , 4");
        };
    }

    // ===================== 2G =====================
    private List<CellDto> handle2G(File xmlFile) throws Exception {

        List<Map<String, String>> rows = xml2GParser.parse(xmlFile);
        List<CellDto> result = new ArrayList<>();

        for (Map<String, String> row : rows) {

            // ---- SITE ----
            NECD_SITETECHNOLOGY site = new NECD_SITETECHNOLOGY();
            site.setName(safeString(row.get("parent_site_id")));
            site.setMscId(safeString(row.get("bsc_name")));
            site.setLac(safeString(row.get("lac")));
            siteRepo.save(site);

            // ---- CELL ----
            NECD_CARRIER_CELL cell = new NECD_CARRIER_CELL();
            cell.setCellName(safeString(row.get("cell_name")));
            cell.setCellId(safeString(row.get("cell_id")));
            cell.setLac(safeString(row.get("lac")));
            cell.setBand(safeString(row.get("band")));
            cell.setMcc(safeString(row.get("mcc")));
            cell.setMnc(safeString(row.get("mnc")));
            carrierRepo.save(cell);

            // ---- DTO ----
            result.add(map2G(row));
        }

        return result;
    }

    // ===================== 3G =====================
    private List<CellDto> handle3G(File xmlFile) throws Exception {

        List<Map<String, String>> rows = xml3GParser.parse(xmlFile);
        List<CellDto> result = new ArrayList<>();

        for (Map<String, String> row : rows) {

            NECD_SITETECHNOLOGY site = new NECD_SITETECHNOLOGY();
            site.setName(safeString(row.get("parent_site_id")));
            site.setMscId(safeString(row.get("rnc")));
            site.setLac(safeString(row.get("lac")));
            siteRepo.save(site);

            NECD_CARRIER_CELL cell = new NECD_CARRIER_CELL();
            cell.setCellName(safeString(row.get("cell_name")));
            cell.setCellId(safeString(row.get("cell_id")));
            cell.setLac(safeString(row.get("lac")));
            cell.setDownlinkFreq(safeInteger(row.get("downlink_freq")));
            cell.setUplinkFreq(safeInteger(row.get("uplink_freq")));
            cell.setReferenceSignalPower(safeString(row.get("power")));
            carrierRepo.save(cell);

            result.add(map3G(row));
        }

        return result;
    }

    // ===================== 4G =====================
    private List<CellDto> handle4G(File xmlFile) throws Exception {

        List<Map<String, String>> rows = xml4GParser.parse(xmlFile);
        List<CellDto> result = new ArrayList<>();

        for (Map<String, String> row : rows) {

            NECD_SITETECHNOLOGY site = new NECD_SITETECHNOLOGY();
            site.setName(safeString(row.get("parent_site_id")));
            site.setTechnologyLookup(
                    "FDD".equalsIgnoreCase(row.get("technology")) ? 1 : 2
            );
            siteRepo.save(site);

            NECD_CARRIER_CELL cell = new NECD_CARRIER_CELL();
            cell.setCellName(safeString(row.get("cell_name")));
            cell.setCellId(safeString(row.get("cell_id")));
            cell.setLac(safeString(row.get("tac")));
            cell.setDlBandwidth(safeString(row.get("dl_bandwidth")));
            cell.setDownlinkFreq(safeInteger(row.get("downlink_freq")));
            cell.setTxRxMode(safeString(row.get("mimo")));
            carrierRepo.save(cell);

            result.add(map4G(row));
        }

        return result;
    }

    // ===================== DTO MAPPERS =====================
    private CellDto map2G(Map<String, String> row) {
        CellDto dto = new CellDto();
        dto.setTechnology(2);
        dto.setParentSiteId(row.get("parent_site_id"));
        dto.setCellName(row.get("cell_name"));
        dto.setCellId(row.get("cell_id"));
        dto.setLacOrTac(row.get("lac"));
        dto.setCellStatus(row.get("cell_status"));
        dto.setBand(row.get("band"));
        dto.setBcch(row.get("bcch"));
        dto.setBscName(row.get("bsc_name"));
        dto.setMcc(row.get("mcc"));
        dto.setMnc(row.get("mnc"));
        return dto;
    }

    private CellDto map3G(Map<String, String> row) {
        CellDto dto = new CellDto();
        dto.setTechnology(3);
        dto.setParentSiteId(row.get("parent_site_id"));
        dto.setCellName(row.get("cell_name"));
        dto.setCellId(row.get("cell_id"));
        dto.setLacOrTac(row.get("lac"));
        dto.setCellStatus(row.get("cell_status"));
        dto.setDownlinkFreq(safeInteger(row.get("downlink_freq")));
        dto.setUplinkFreq(safeInteger(row.get("uplink_freq")));
        dto.setPower(row.get("power"));
        dto.setRnc(row.get("rnc"));
        dto.setSac(row.get("sac"));
        return dto;
    }

    private CellDto map4G(Map<String, String> row) {
        CellDto dto = new CellDto();
        dto.setTechnology(4);
        dto.setParentSiteId(row.get("parent_site_id"));
        dto.setCellName(row.get("cell_name"));
        dto.setCellId(row.get("cell_id"));
        dto.setLacOrTac(row.get("tac"));
        dto.setCellStatus(row.get("cell_status"));
        dto.setTechnologyMode(row.get("technology"));
        dto.setDownlinkFreq(safeInteger(row.get("downlink_freq")));
        dto.setDlBandwidth(row.get("dl_bandwidth"));
        dto.setMimo(row.get("mimo"));
        return dto;
    }

    // ===================== HELPERS =====================
    private File convertToFile(MultipartFile file) throws Exception {
        File temp = File.createTempFile("xml-", ".xml");
        file.transferTo(temp);
        return temp;
    }

    private String safeString(String val) {
        if (val == null) return null;
        val = val.trim();
        return val.isEmpty() || val.equalsIgnoreCase("null") ? null : val;
    }

    private Integer safeInteger(String val) {
        try {
            if (val == null) return null;
            val = val.trim();
            if (val.isEmpty() || val.equalsIgnoreCase("null")) return null;
            return Integer.valueOf(val);
        } catch (Exception e) {
            return null;
        }
    }
}
*/

package com.example.NIS.Service;

import com.example.NIS.DTO.Cell2GDto;
import com.example.NIS.DTO.Cell3GDto;
import com.example.NIS.DTO.Cell4GDto;
import com.example.NIS.Repository.NecdCarrierCellRepository;
import com.example.NIS.Repository.NecdSitetechnologyRepository;
import com.example.NIS.model.NECD_CARRIER_CELL;
import com.example.NIS.model.NECD_SITETECHNOLOGY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class XmlService {

    @Autowired
    private Xml2GParser xml2GParser;

    @Autowired
    private Xml3GParser xml3GParser;

    @Autowired
    private Xml4GParser xml4GParser;

    @Autowired
    private NecdCarrierCellRepository carrierRepo;

    @Autowired
    private NecdSitetechnologyRepository siteRepo;

    // ===================== MAIN =====================
    // 🔥 أي Upload جديد → امسح كاش الـ VIEW
    @CacheEvict(
            value = { "cellView", "allSites" },
            allEntries = true
    )
    public void extract(MultipartFile file, Integer type) throws Exception {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("XML file is required");
        }

        File xmlFile = convertToFile(file);

        switch (type) {
            case 2 -> handle2G(xmlFile);
            case 3 -> handle3G(xmlFile);
            case 4 -> handle4G(xmlFile);
            default -> throw new IllegalArgumentException("Allowed types: 2 , 3 , 4");
        }
    }

    // ===================== 2G =====================
    private void handle2G(File xmlFile) throws Exception {

        List<Cell2GDto> rows = xml2GParser.parse(xmlFile);

        for (Cell2GDto dto : rows) {

            NECD_SITETECHNOLOGY site = new NECD_SITETECHNOLOGY();
            site.setName(dto.getParent_site_id());
            site.setMscId(dto.getBsc_name());
            site.setLac(dto.getLac());
            siteRepo.save(site);

            NECD_CARRIER_CELL cell = new NECD_CARRIER_CELL();
            cell.setCellName(dto.getCell_name());
            cell.setCellId(dto.getCell_id());
            cell.setLac(dto.getLac());
            cell.setBand(dto.getBand());
            cell.setMcc(dto.getMcc());
            cell.setMnc(dto.getMnc());
            carrierRepo.save(cell);
        }
    }

    // ===================== 3G =====================
    private void handle3G(File xmlFile) throws Exception {

        List<Cell3GDto> rows = xml3GParser.parse(xmlFile);

        for (Cell3GDto dto : rows) {

            NECD_SITETECHNOLOGY site = new NECD_SITETECHNOLOGY();
            site.setName(dto.getParent_site_id());
            site.setMscId(dto.getRnc());
            site.setLac(dto.getLac());
            siteRepo.save(site);

            NECD_CARRIER_CELL cell = new NECD_CARRIER_CELL();
            cell.setCellName(dto.getCell_name());
            cell.setCellId(dto.getCell_id());
            cell.setLac(dto.getLac());
            cell.setDownlinkFreq(safeInteger(dto.getDownlink_freq()));
            cell.setUplinkFreq(safeInteger(dto.getUplink_freq()));
            cell.setReferenceSignalPower(dto.getPower());
            carrierRepo.save(cell);
        }
    }

    // ===================== 4G =====================
    private void handle4G(File xmlFile) throws Exception {

        List<Cell4GDto> rows = xml4GParser.parse(xmlFile);

        for (Cell4GDto dto : rows) {

            NECD_SITETECHNOLOGY site = new NECD_SITETECHNOLOGY();
            site.setName(dto.getParent_site_id());
            site.setTechnologyLookup(
                    "FDD".equalsIgnoreCase(dto.getTechnology()) ? 1 : 2
            );
            siteRepo.save(site);

            NECD_CARRIER_CELL cell = new NECD_CARRIER_CELL();
            cell.setCellName(dto.getCell_name());
            cell.setCellId(dto.getCell_id());
            cell.setLac(dto.getTac());
            cell.setDlBandwidth(dto.getDl_bandwidth());
            cell.setDownlinkFreq(safeInteger(dto.getDownlink_freq()));
            cell.setTxRxMode(dto.getMimo());
            carrierRepo.save(cell);
        }
    }

    // ===================== HELPERS =====================
    private File convertToFile(MultipartFile file) throws Exception {
        File temp = File.createTempFile("xml-", ".xml");
        file.transferTo(temp);
        return temp;
    }

    private Integer safeInteger(String val) {
        try {
            if (val == null || val.isBlank()) return null;
            return Integer.valueOf(val.trim());
        } catch (Exception e) {
            return null;
        }
    }
}
