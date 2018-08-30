package Servlet;

import Config.ConfigReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ServletLoader {
    private static final String WEBAPPCONFIGFILE = "web.xml";
    public static HashMap<String, String> servletClassMap = new HashMap<>();
    public static HashMap<String, String> servletMappingMap = new HashMap<>();

    public void startup() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc;

        if (ConfigReader.servletPath.charAt(ConfigReader.servletPath.length() - 1) == '/') {
            doc = builder.parse(new File(ConfigReader.servletPath + WEBAPPCONFIGFILE));
        } else {
            doc = builder.parse(new File(ConfigReader.servletPath + "/" + WEBAPPCONFIGFILE));
        }


        Element root = doc.getDocumentElement();

        NodeList servletList = root.getElementsByTagName("servlet");
        for (int i = 0; i < servletList.getLength(); i++) {
            String servletName = null;
            String servletClass = null;
            NodeList list = servletList.item(i).getChildNodes();
            for (int j = 0; j < list.getLength(); j++) {
                if (list.item(j).getNodeName().equals("servlet-name")) {
                    servletName = list.item(j).getTextContent().trim();
                } else if (list.item(j).getNodeName().equals("servlet-class")) {
                    servletClass = list.item(j).getTextContent().trim();
                }
            }
            if (servletName != null && servletClass != null) {
                servletClassMap.put(servletName, servletClass);
            }
        }

        NodeList servletMapList = root.getElementsByTagName("servlet-mapping");
        for (int i = 0; i < servletMapList.getLength(); i++) {
            NodeList list = servletMapList.item(i).getChildNodes();
            String servletName = null;
            String servletUri = null;
            for (int j = 0; j < list.getLength(); j++) {
                if (list.item(j).getNodeName().equals("servlet-name")) {
                    servletName = list.item(j).getTextContent().trim();
                } else if (list.item(j).getNodeName().equals("url-pattern")) {
                    servletUri = list.item(j).getTextContent().trim();
                }
            }

            if (servletName != null && servletUri != null) {
                servletMappingMap.put(servletUri, servletName);
            }

        }
    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        ServletLoader servletLoader = new ServletLoader();
        servletLoader.startup();
        System.out.println(ServletLoader.servletClassMap);
        System.out.println(ServletLoader.servletMappingMap);
    }
}
