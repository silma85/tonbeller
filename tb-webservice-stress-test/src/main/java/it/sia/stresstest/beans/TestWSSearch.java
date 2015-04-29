package it.sia.stresstest.beans;

import it.sia.stresstest.StressTestApplication;
import it.sia.stresstest.services.wssearch.WSSearch;
import it.sia.stresstest.services.wssearch.WSSearchServiceLocator;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class TestWSSearch implements TestWebService {

  private final static Logger log = LoggerFactory.getLogger(TestWSSearch.class);

  private static String result = "";

  public String start() {
    log.info(new Date() + " - Start...");

    byte[] byteReceived = null;

    try {
      WSSearchServiceLocator service = new WSSearchServiceLocator();
      service.setWSSearchEndpointAddress(String.format(StressTestApplication.props.getProperty("wssearch.url"),
          StressTestApplication.props.getProperty("wssearch.address"),
          StressTestApplication.props.getProperty("wssearch.port")));
      WSSearch wssearch = service.getWSSearch();

      byte[] byteToSend = getDataToSend();

      byteReceived = wssearch.doSearch(byteToSend);

      log.debug(new String(byteReceived));

      DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document doc = dBuilder.parse(new InputSource(new ByteArrayInputStream(byteReceived)));
      doc.getDocumentElement().normalize();

      result = "RetCode\t " + doc.getElementsByTagName("returnCode").item(0).getTextContent() +
          "\t CustNumber\t " + ((Element) doc.getElementsByTagName("result").item(0)).getAttribute("custno") +
          "\t Match\t " + doc.getElementsByTagName("result").item(0).getTextContent() +
          "\t PEPAPI\t " + ((Element) doc.getElementsByTagName("result").item(1)).getAttribute("custno") +
          "\t Match\t " + doc.getElementsByTagName("result").item(1).getTextContent();

      log.debug(result);

    } catch (Exception e) {
      log.error(String.format("Error: %s %s", e.getCause(), new String(byteReceived)), e);
      result = "";
    }

    log.info(new Date() + " - End");
    return result;
  }

  private static byte[] getDataToSend() throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(StressTestApplication.props.getProperty("wssearch.input")));
    String rows = "";
    String tmpRow = "";

    while ((tmpRow = br.readLine()) != null) {
      rows += tmpRow;
    }

    br.close();

    return rows.getBytes();
  }

  public String getResult() {
    return result;
  }
}
