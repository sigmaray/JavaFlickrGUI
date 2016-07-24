/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sigmaray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.awt.Desktop;
import java.net.URI;
import java.net.URLEncoder;

public class JavaFlickr {
    static String API_KEY = "4cc2a6e2419deebfe86eca026cfda157";
    static String per_page = "20";
    
    public static void printR(Object someObject) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(someObject));
    }
    
    public static String printRToString(Object someObject) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(someObject);
    }
    
//    public static void main(String args[]) {
//        String s = getUrl("pizza");
//        StringBuffer xo = fileGetContents(s);
//        List photosList = parseXml(xo.toString());
//        String url = buildImgUrl((HashMap) photosList.get(0));
//        try {
//            Desktop.getDesktop().browse(new URI(url));
//        } catch (Exception  e) {
//            e.printStackTrace();
//        }        
//    }
    public static List getList(String searchString) {
        return getList(searchString, 0);
    }
    
    public static List getList(String searchString, int page) {
        String s = getUrl(searchString, page);
        StringBuffer xo = fileGetContents(s);
        List photosList = parseXml(xo.toString());
        return photosList;
    }
    
    public static List flickrListToUrlList(List flickrList) {        
        List urlsList = new ArrayList();
        for (int i = 0; i < flickrList.size(); i++) {
            urlsList.add(buildImgUrl((HashMap) flickrList.get(i)));
        }
        return urlsList;
    }

    public static String getUrl(String searchString) {
        return getUrl(searchString, 0);
    }

    public static String getUrl(String searchString, int page) {
        String url = "";
        try{
            url = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=" +
                API_KEY + "&text=" + URLEncoder.encode(searchString, "UTF-8") + "&safe_search=1&page=" + Integer.toString(page) + "&per_page=" + per_page;
        } catch (Exception e) {}
        return url;
    }
    
    public static String buildImgUrl(HashMap item) {
        String src = "http://farm" + item.get("farm") + ".static.flickr.com/" + item.get("server") + "/" + item.get("id") + "_" + item.get("secret") + "_c.jpg";
//        String flickrUrl = "https://www.flickr.com/photos/" + item.get("owner") + "/" + item.get("id");
        return src;
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
    public static List parseXml(String xmlString) {
      List photosList = new ArrayList();
      try {
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         InputSource is = new InputSource(new StringReader(xmlString));
         Document doc = dBuilder.parse(is);
         doc.getDocumentElement().normalize();
//         System.out.println("Root element :" 
//            + doc.getDocumentElement().getNodeName());
         NodeList nList = doc.getElementsByTagName("student");
         
         NodeList nListZ = doc.getElementsByTagName("photo");
         for (int temp = 0; temp < nListZ.getLength(); temp++) {
            Node nNode = nListZ.item(temp);
//            System.out.println("\nCurrent Element :" 
//               + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                HashMap hm = new HashMap();
                hm.put("id", eElement.getAttribute("id"));
                hm.put("owner", eElement.getAttribute("owner"));
                hm.put("server", eElement.getAttribute("server"));
                hm.put("farm", eElement.getAttribute("farm"));
                hm.put("title", eElement.getAttribute("title"));
                hm.put("secret", eElement.getAttribute("secret"));
                
                photosList.add(hm);
//                System.out.println(photosList.size());
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      return photosList;
   }    
}
