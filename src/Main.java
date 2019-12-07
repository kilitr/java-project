import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        try {
            File fXmlFile = new File("large_graph.graphml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList vList = doc.getElementsByTagName("node");
            System.out.println("----- Getting all Vertices -----");
            for(int temp = 0; temp < vList.getLength(); temp++) {
                Node nNode = vList.item(temp);

                if(nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String n_id = eElement.getAttribute("id");
                    String v_id = eElement.getElementsByTagName("data").item(0).getTextContent();
                    System.out.println(nNode.getNodeName() + " id: "+ n_id + "\tv_id: " + v_id);
                }
            }

            NodeList eList = doc.getElementsByTagName("edge");
            System.out.println("----- Getting all Edges -----");
            for(int temp = 0; temp < eList.getLength(); temp++) {
                Node nNode = eList.item(temp);

                if(nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String source = eElement.getAttribute("source");
                    String target = eElement.getAttribute("target");
                    String e_id = eElement.getElementsByTagName("data").item(0).getTextContent();
                    String e_weight = eElement.getElementsByTagName("data").item(1).getTextContent();
                    System.out.println(nNode.getNodeName() + " source: "+ source + "\ttarget: " + target +
                            "\te_id: " + e_id + "\te_weight: " + e_weight);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
