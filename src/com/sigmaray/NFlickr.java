/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigmaray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author John
 */
public class NFlickr {
    static String API_KEY = "4cc2a6e2419deebfe86eca026cfda157";
    static String per_page = "20";
    public static void main(String args[]) {
        String s = getUrl("pizza", 1);
        StringBuffer xo = fileGetContents(s);
        System.out.println(xo);
        parseXml(xo.toString());
    }
    public static String getUrl(String searchString, int page) {
        String url = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=" +
                API_KEY + "&text=" + searchString + "&safe_search=1&page=" + Integer.toString(page) + "&per_page=" + per_page;
        return url;
    }

    public static StringBuffer fileGetContents(String url) {
        String encode = "utf8";
        StringBuffer buffer = new StringBuffer();
        try {
            InputStream is = new URL(url).openStream();
            InputStreamReader isr = new InputStreamReader(is, encode);
            BufferedReader in = new BufferedReader(isr);
            String s = null;
            while ((s = in.readLine()) != null) {
                buffer.append(s).append("\n");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            buffer = null;
        } finally {
            return buffer;
        }
    }    

    // http://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
    // http://stackoverflow.com/questions/562160/in-java-how-do-i-parse-xml-as-a-string-instead-of-a-file
    public static void parseXml(String xmlString) {
      try {	
//         File inputFile = new File("input.txt");
         DocumentBuilderFactory dbFactory 
            = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//         Document doc = dBuilder.parse(inputFile);
         InputSource is = new InputSource(new StringReader(xmlString));
         Document doc = dBuilder.parse(is);
         doc.getDocumentElement().normalize();
         System.out.println("Root element :" 
            + doc.getDocumentElement().getNodeName());
         NodeList nList = doc.getElementsByTagName("student");
         
         NodeList nListZ = doc.getElementsByTagName("photo");
         for (int temp = 0; temp < nListZ.getLength(); temp++) {
            Node nNode = nListZ.item(temp);
            System.out.println("\nCurrent Element :" 
               + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                System.out.println("id : " 
                     + eElement.getAttribute("id"));
                
                System.out.println("owner : " 
                     + eElement.getAttribute("id"));
            }
         }
         
         if (false) {
            System.out.println("----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
               Node nNode = nList.item(temp);
               System.out.println("\nCurrent Element :" 
                  + nNode.getNodeName());
               if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                  Element eElement = (Element) nNode;
                  System.out.println("Student roll no : " 
                     + eElement.getAttribute("rollno"));
                  System.out.println("First Name : " 
                     + eElement
                     .getElementsByTagName("firstname")
                     .item(0)
                     .getTextContent());
                  System.out.println("Last Name : " 
                  + eElement
                     .getElementsByTagName("lastname")
                     .item(0)
                     .getTextContent());
                  System.out.println("Nick Name : " 
                  + eElement
                     .getElementsByTagName("nickname")
                     .item(0)
                     .getTextContent());
                  System.out.println("Marks : " 
                  + eElement
                     .getElementsByTagName("marks")
                     .item(0)
                     .getTextContent());
               }
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }    
}
