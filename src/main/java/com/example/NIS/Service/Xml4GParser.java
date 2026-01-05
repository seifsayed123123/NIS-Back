/*
package com.example.NIS;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NIS4G {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("GExport_Haddama_BAS3677_10.130.199.47_20241110030000.xml");

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            List<String> cellList = new ArrayList<>();

            DefaultHandler handler = new DefaultHandler() {

                boolean insideCellClass = false;
                boolean insideExternalClass = false;   // للـ EUTRANEXTERNALCELL
                boolean insideObject = false;

                String parentSiteID = "";
                String cellName = "";
                String cellID = "";
                String tac = "";
                String cellStatus = "";
                String technology = "";
                String uplinkFreq = "";
                String dlBandwidth = "";
                String mimo = "";

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) {

                    if (qName.equalsIgnoreCase("class")) {
                        String className = attributes.getValue("name");

                        if ("CELL_BTS3900".equalsIgnoreCase(className)) {
                            insideCellClass = true;
                        }

                        if ("EUTRANEXTERNALCELL_BTS3900".equalsIgnoreCase(className)) {
                            insideExternalClass = true;
                        }
                    }

                    if ((insideCellClass || insideExternalClass) && qName.equalsIgnoreCase("object")) {
                        insideObject = true;

                        cellName = "";
                        cellID = "";
                        tac = "";
                        cellStatus = "";
                        technology = "";
                        uplinkFreq = "";
                        dlBandwidth = "";
                        mimo = "";
                    }

                    if (insideObject && qName.equalsIgnoreCase("parameter")) {

                        String name = attributes.getValue("name");
                        String value = attributes.getValue("value");

                        if (name != null && value != null) {

                            switch (name) {

                                case "CELLNAME":
                                    cellName = value;
                                    if (!value.isEmpty()) {
                                        String[] parts = value.split("_");
                                        if (parts.length >= 3) {
                                            parentSiteID = parts[2];
                                            int dash = parentSiteID.indexOf("-");
                                            if (dash != -1) parentSiteID = parentSiteID.substring(0, dash);
                                        }
                                    }
                                    break;

                                case "CELLID":
                                    int id = Integer.parseInt(value);
                                    cellID = String.format("%03d", id);
                                    break;

                                case "TAC":     // من CELL أو من EUTRANEXTERNALCELL
                                    tac = value;
                                    break;

                                case "CELLACTIVESTATE":
                                    cellStatus = value.equalsIgnoreCase("CELL_ACTIVE") ? "On Air" : "Off Air";
                                    break;

                                case "FDDTDDIND":
                                    technology = value.replace("CELL_", "");
                                    break;

                                case "DLEARFCN":
                                    uplinkFreq = value;
                                    break;

                                case "DLBANDWIDTH":
                                    dlBandwidth = value;
                                    break;

                                case "TXRXMODE":
                                    mimo = value;
                                    break;
                            }
                        }
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) {

                    if (insideObject && qName.equalsIgnoreCase("object")) {

                        String formatted = String.format(
                                "==== NEW 4G CELL ====\n" +
                                        "Parent SiteID : %s\n" +
                                        "Cell Name     : %s\n" +
                                        "Cell ID       : %s\n" +
                                        "TAC           : %s\n" +
                                        "Status        : %s\n" +
                                        "Technology    : %s\n" +
                                        "Uplink Freq   : %s\n" +
                                        "DL BW         : %s\n" +
                                        "MIMO          : %s\n",
                                parentSiteID, cellName, cellID, tac, cellStatus,
                                technology, uplinkFreq, dlBandwidth, mimo
                        );

                        // لو TAC جاي من EUTRANEXTERNALCELL فقط → برضه يضاف
                        cellList.add(formatted);

                        insideObject = false;
                    }

                    if (insideCellClass && qName.equalsIgnoreCase("class")) {
                        insideCellClass = false;
                    }

                    if (insideExternalClass && qName.equalsIgnoreCase("class")) {
                        insideExternalClass = false;
                    }
                }
            };

            saxParser.parse(xmlFile, handler);

            System.out.println("4G: Cells including TAC from EUTRANEXTERNALCELL_BTS3900:\n");
            for (String cell : cellList) {
                System.out.println(cell);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

 */

package com.example.NIS.Service;

import com.example.NIS.DTO.Cell4GDto;
import org.springframework.stereotype.Service;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.*;

@Service
public class Xml4GParser {

    public List<Cell4GDto> parse(File xmlFile) throws Exception {

        // key = CELLID , value = DTO
        Map<String, Cell4GDto> cells = new LinkedHashMap<>();

        SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();

        DefaultHandler handler = new DefaultHandler() {

            String currentCellId = null;

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) {

                if (!"parameter".equalsIgnoreCase(qName))
                    return;

                String name = attributes.getValue("name");
                String value = attributes.getValue("value");
                if (name == null || value == null) return;

                // ================= CELL ID =================
                if ("CELLID".equalsIgnoreCase(name)) {
                    currentCellId = value;
                    cells.putIfAbsent(currentCellId, new Cell4GDto());
                    cells.get(currentCellId).setCell_id(value);
                    return;
                }

                // لو لسه مش عارف CELLID تجاهل باقي الداتا
                if (currentCellId == null) return;

                Cell4GDto dto = cells.get(currentCellId);

                switch (name) {

                    case "CELLNAME":
                        dto.setCell_name(value);
                        if (value.contains("_")) {
                            try {
                                String siteId = value.split("_")[2].split("-")[0];
                                dto.setParent_site_id(siteId);
                            } catch (Exception ignored) {}
                        }
                        break;

                    case "TAC":
                        dto.setTac(value);
                        break;

                    case "CELLACTIVESTATE":
                        dto.setCell_status(
                                value.equalsIgnoreCase("CELL_ACTIVE")
                                        ? "On Air" : "Off Air"
                        );
                        break;

                    case "FDDTDDIND":
                        dto.setTechnology(value.replace("CELL_", ""));
                        break;

                    case "DLEARFCN":
                        dto.setDownlink_freq(value);
                        break;

                    case "DLBANDWIDTH":
                        dto.setDl_bandwidth(value);
                        break;

                    case "TXRXMODE":
                        dto.setMimo(value);
                        break;
                }
            }
        };

        saxParser.parse(xmlFile, handler);

        // فلترة الخلايا غير المكتملة
        List<Cell4GDto> result = new ArrayList<>();
        for (Cell4GDto dto : cells.values()) {
            if (dto.getCell_name() != null && dto.getCell_id() != null) {
                result.add(dto);
            }
        }

        return result;
    }
}
