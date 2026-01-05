/*
package com.example.NIS;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class Nis3G {

    public static void main(String[] args) throws Exception {

        File xmlFile = new File("CMExport_RDIWH01_10.133.19.132_2024111206.xml");

        // Extract RNC name from file name
        String fileName = xmlFile.getName();
        String rncName = "Unknown";

        String[] parts = fileName.split("_");
        if (parts.length > 1) {
            rncName = parts[1];   // RDIWH01
        }

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        NodeList moList = doc.getElementsByTagName("MO");

        for (int i = 0; i < moList.getLength(); i++) {
            Element mo = (Element) moList.item(i);

            // Filter correct className
            if (!mo.getAttribute("className").equals("BSC6900UMTSCell"))
                continue;

            NodeList attrs = mo.getElementsByTagName("attr");

            String cellName = "";
            String cellId = "";
            String lac = "";
            String statusRaw = "";
            String downlink = "";
            String uplink = "";
            String power = "";
            String sac = "";

            // Extract required parameters
            for (int j = 0; j < attrs.getLength(); j++) {
                Element attr = (Element) attrs.item(j);

                switch (attr.getAttribute("name")) {

                    case "CELLNAME":
                        cellName = attr.getTextContent().trim();
                        break;

                    case "CELLID":
                        cellId = attr.getTextContent().trim();
                        break;

                    case "LAC":
                        lac = attr.getTextContent().trim();
                        break;

                    case "ACTSTATUS":
                        statusRaw = attr.getTextContent().trim();
                        break;

                    case "UARFCNDOWNLINK":
                        downlink = attr.getTextContent().trim();
                        break;

                    case "UARFCNUPLINK":
                        uplink = attr.getTextContent().trim();
                        break;

                    case "MAXTXPOWER":
                        power = attr.getTextContent().trim();
                        break;

                    case "SAC":
                        sac = attr.getTextContent().trim();
                        break;
                }
            }

            if (cellName.isEmpty())
                continue;

            // Extract Parent Site ID (last part after _)
            String parentSiteID = cellName;

// لو فيه "_" → ناخد اللي بعده
            int idx = cellName.lastIndexOf("_");
            if (idx != -1 && idx + 1 < cellName.length()) {
                parentSiteID = cellName.substring(idx + 1);
            }

// لو فيه "-" → نشيل الجزء اللي بعده
            int dash = parentSiteID.indexOf("-");
            if (dash != -1) {
                parentSiteID = parentSiteID.substring(0, dash);
            }


            // Determine Status
            String status = statusRaw.equalsIgnoreCase("ACTIVATED") ? "On Air" : "Off Air";

            // === FINAL OUTPUT ===
            System.out.println("==== NEW 3G CELL ====");
            System.out.println("RNC           = " + rncName);
            System.out.println("Parent SiteID = " + parentSiteID);
            System.out.println("Cell Name     = " + cellName);
            System.out.println("Cell ID       = " + cellId);
            System.out.println("LAC           = " + lac);
            System.out.println("Downlink Freq = " + downlink);
            System.out.println("Uplink Freq   = " + uplink);
            System.out.println("Power         = " + power);
            System.out.println("SAC           = " + sac);
            System.out.println("Status        = " + status);
            System.out.println("------------------------------------");
        }
    }
}
*/


package com.example.NIS.Service;

import com.example.NIS.DTO.Cell3GDto;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

@Service
public class Xml3GParser {

    public List<Cell3GDto>parse(File xmlFile) throws Exception {

        List<Cell3GDto> result = new ArrayList<>();

        String fileName = xmlFile.getName();
        String rnc = fileName.split("_").length > 1
                ? fileName.split("_")[1]
                : "UNKNOWN";

        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(xmlFile);

        NodeList moList = doc.getElementsByTagName("MO");

        for (int i = 0; i < moList.getLength(); i++) {

            Element mo = (Element) moList.item(i);
            if (!"BSC6900UMTSCell".equals(mo.getAttribute("className")))
                continue;

            NodeList attrs = mo.getElementsByTagName("attr");
            Cell3GDto dto = new Cell3GDto();

            for (int j = 0; j < attrs.getLength(); j++) {

                Element attr = (Element) attrs.item(j);
                String name = attr.getAttribute("name");
                String value = attr.getTextContent().trim();

                switch (name) {

                    case "CELLNAME":
                        dto.setCell_name(value);

                        String siteId = value;
                        if (siteId.contains("_"))
                            siteId = siteId.substring(siteId.lastIndexOf("_") + 1);
                        if (siteId.contains("-"))
                            siteId = siteId.substring(0, siteId.indexOf("-"));

                        dto.setParent_site_id(siteId);
                        break;

                    case "CELLID": dto.setCell_id(value); break;
                    case "LAC": dto.setLac(value); break;
                    case "UARFCNDOWNLINK": dto.setDownlink_freq(value); break;
                    case "UARFCNUPLINK": dto.setUplink_freq(value); break;
                    case "MAXTXPOWER": dto.setPower(value); break;
                    case "SAC": dto.setSac(value); break;
                    case "ACTSTATUS":
                        dto.setCell_status(
                                value.equalsIgnoreCase("ACTIVATED")
                                        ? "On Air"
                                        : "Off Air"
                        );
                        break;
                }
            }

            if (dto.getCell_name() == null) continue;

            dto.setRnc(rnc);
            result.add(dto);
        }

        return result;
    }
}

