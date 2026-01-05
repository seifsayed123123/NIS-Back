/*
package com.example.NIS;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.*;

@SpringBootApplication
public class NisApplication2G {

	static List<String> printData = new ArrayList<>();

	public static void main(String[] args) throws Exception {

		File xmlFile = new File("C:/Users/NTG/Desktop/CMExport_BBAGH03_192.168.60.32_2024111206.xml");

		Set<String> cellAttributes = Set.of(
				"CELLNAME", "CI", "LAC", "TYPE",
				"ACTSTATUS", "BCCHNO", "mcc", "mnc"
		);

		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();

		DefaultHandler handler = new DefaultHandler() {

			String currentAttr = null;
			Deque<String> moClassStack = new ArrayDeque<>(); // stack of MO classNames
			String bscName = null; // current BSC name (from <attr name="name"> inside BSC6900GSMNE)

			boolean insideCell = false;
			Map<String, String> cellData = new LinkedHashMap<>();

			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes)
					throws SAXException {

				if (qName.equalsIgnoreCase("MO")) {
					String className = attributes.getValue("className");
					moClassStack.push(className); // enter a new MO
					// if entering a new BSC, reset bscName (we will read its <attr name="name">)
					if ("BSC6900GSMNE".equals(className)) {
						bscName = null;
					}
					// if entering a cell
					if ("BSC6900GSMCell".equals(className)) {
						insideCell = true;
						cellData.clear();
						printData.add("\n==== NEW 2G CELL ====");
					}
				}

				if (qName.equalsIgnoreCase("attr")) {
					currentAttr = attributes.getValue("name");
				}
			}

			@Override
			public void characters(char[] ch, int start, int length)
					throws SAXException {

				if (currentAttr == null) return;

				String value = new String(ch, start, length).trim();
				if (value.isEmpty()) return;

				// If current MO (top of stack) is BSC6900GSMNE and attr name is "name" => this is the BSC name
				String topClass = moClassStack.peek();
				if ("BSC6900GSMNE".equals(topClass) && "name".equals(currentAttr)) {
					bscName = value; // e.g. BBAGH01
				}

				// Read cell attributes when inside a cell MO
				if (insideCell && cellAttributes.contains(currentAttr)) {
					if (currentAttr.equals("ACTSTATUS")) {
						value = value.equalsIgnoreCase("ACTIVATED") ? "On Air" : "Off Air";
					}
					cellData.put(currentAttr, value);
				}
			}

			@Override
			public void endElement(String uri, String localName, String qName)
					throws SAXException {

				if (qName.equalsIgnoreCase("MO")) {
					// pop the MO we are leaving
					String poppedClass = moClassStack.isEmpty() ? null : moClassStack.pop();

					// If we left a cell MO -> print its info (use current bscName which is the nearest enclosing BSC)
					if ("BSC6900GSMCell".equals(poppedClass) && insideCell) {
						insideCell = false;

						String rawName = cellData.getOrDefault("CELLNAME", "N/A");

						// Extract SiteID (example BAG0854 from DoraZeraa4_BAG0854-6)
						String siteID = rawName;
						if (siteID.contains("_")) {
							siteID = siteID.substring(siteID.lastIndexOf("_") + 1);
						}
						if (siteID.contains("-")) {
							siteID = siteID.substring(0, siteID.indexOf("-"));
						}


						printData.add("Parent SiteID = " + siteID);
						printData.add("Cell Name     = " + rawName);
						printData.add("Cell ID       = " + cellData.get("CI"));
						printData.add("LAC           = " + cellData.get("LAC"));
						printData.add("BAND          = " + cellData.get("TYPE"));
						printData.add("BCCH          = " + cellData.get("BCCHNO"));
						printData.add("Cell Status   = " + cellData.get("ACTSTATUS"));
						printData.add("MCC           = " + 418);
						printData.add("MNC           = " + 5);
						printData.add("BSC           = " + bscName);
					}

					// If we left a BSC MO -> nothing special needed (bscName will be overwritten when next BSC is entered)
					if ("BSC6900GSMNE".equals(poppedClass)) {
						// optional: keep bscName or set to null to avoid accidental reuse outside scope
						// bscName = null;
					}
				}

				currentAttr = null;
			}
		};

		saxParser.parse(xmlFile, handler);

		System.out.println("======= Filtered 2G Cell Data =======");
		for (String line : printData) {
			System.out.println(line);
		}
	}
}
*/

package com.example.NIS.Service;

import com.example.NIS.DTO.Cell2GDto;
import org.springframework.stereotype.Service;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.*;

@Service
public class Xml2GParser {

	public List<Cell2GDto> parse(File xmlFile) throws Exception {

		List<Cell2GDto> result = new ArrayList<>();

		Set<String> cellAttributes = Set.of(
				"CELLNAME", "CI", "LAC", "TYPE",
				"ACTSTATUS", "BCCHNO"
		);

		SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();

		DefaultHandler handler = new DefaultHandler() {

			String currentAttr;
			Deque<String> moStack = new ArrayDeque<>();
			String bscName;

			boolean insideCell = false;
			Map<String, String> cellData = new HashMap<>();

			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes) {

				if ("MO".equalsIgnoreCase(qName)) {
					String className = attributes.getValue("className");
					moStack.push(className);

					if ("BSC6900GSMNE".equals(className)) {
						bscName = null;
					}

					if ("BSC6900GSMCell".equals(className)) {
						insideCell = true;
						cellData = new HashMap<>();
					}
				}

				if ("attr".equalsIgnoreCase(qName)) {
					currentAttr = attributes.getValue("name");
				}
			}

			@Override
			public void characters(char[] ch, int start, int length) {

				if (currentAttr == null) return;

				String value = new String(ch, start, length).trim();
				if (value.isEmpty()) return;

				if ("BSC6900GSMNE".equals(moStack.peek()) && "name".equals(currentAttr)) {
					bscName = value;
				}

				if (insideCell && cellAttributes.contains(currentAttr)) {
					if ("ACTSTATUS".equals(currentAttr)) {
						value = value.equalsIgnoreCase("ACTIVATED") ? "On Air" : "Off Air";
					}
					cellData.put(currentAttr, value);
				}
			}

			@Override
			public void endElement(String uri, String localName, String qName) {

				if ("MO".equalsIgnoreCase(qName)) {

					String popped = moStack.pop();

					if ("BSC6900GSMCell".equals(popped)) {

						insideCell = false;

						String cellName = cellData.get("CELLNAME");
						if (cellName == null) return;

						String siteId = cellName;
						if (siteId.contains("_"))
							siteId = siteId.substring(siteId.lastIndexOf("_") + 1);
						if (siteId.contains("-"))
							siteId = siteId.substring(0, siteId.indexOf("-"));

						Cell2GDto dto = new Cell2GDto();
						dto.setParent_site_id(siteId);
						dto.setCell_name(cellName);
						dto.setCell_id(cellData.get("CI"));
						dto.setLac(cellData.get("LAC"));
						dto.setBand(cellData.get("TYPE"));
						dto.setBsc_name(bscName);
						dto.setMcc("418");
						dto.setMnc("5");

						result.add(dto);
					}
				}
				currentAttr = null;
			}
		};

		saxParser.parse(xmlFile, handler);

		System.out.println("======= 2G DTO OUTPUT =======");
		result.forEach(dto -> {
			System.out.println("---- NEW 2G CELL ----");
			System.out.println("parent_site_id = " + dto.getParent_site_id());
			System.out.println("cell_name = " + dto.getCell_name());
			System.out.println("cell_id = " + dto.getCell_id());
			System.out.println("lac = " + dto.getLac());
			System.out.println("band = " + dto.getBand());
			System.out.println("bsc_name = " + dto.getBsc_name());
		});

		return result;
	}
}

