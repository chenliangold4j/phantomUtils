package xml.parse;

import java.util.Iterator;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

import xml.util.Dom4jUtil;

public class Dom4jParseUtil
{
   
   
   
   public static void main(String[] args)
   {
      String xml1 = "<?xml version=\"1.0\"?>\r\n" + 
               "<msg>\r\n" + 
               "<cc id=\"3\" type=\"set\">\r\n" + 
               "<serverTime>20160825123533</serverTime>\r\n" + 
               "<firmware size=\"5361739\"/>\r\n" + 
               "</cc>\r\n" + 
               "</msg>\r\n";
      Document document = Dom4jUtil.parse(xml1);
      Element root  = document.getRootElement();
      for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
         Element element = it.next();
         System.out.println(element.getName());
         System.out.println(element.getData());
         int i = element.attributeCount();
         for(int j = 0;j<i;j++) {
            Attribute attr = element.attribute(j);
            System.out.println(attr.getName()+";"+attr.getData());
         }
         // do something
     }
    
      
   }
   
}
