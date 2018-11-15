package xml.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


public class ClientServiceDom4jUtil
{

   private static final String ROOT_ELEMENT   = "DSXClient";
   private static final String LOGOUT_ELEMENT = "Logout";
   private static final String RESULT         = "result";

   /**
    * 通过xml获取document
    * 
    * @param xml
    * @return
    * @throws DocumentException
    * @throws UnsupportedEncodingException
    */
   public static Document parse(String xml) throws DocumentException, UnsupportedEncodingException
   {
      SAXReader reader = new SAXReader();
      StringReader stringReader = new StringReader(xml);
      Document document = reader.read(stringReader);
      return document;
   }
   // ---------------------------------------------------------------------------

   /**
    * 获取自节点的datamap
    * 
    * @param xml
    * @return
    * @throws UnsupportedEncodingException
    * @throws DocumentException
    */
   public static Map<String, Object> getDataMapFromXml(String xml) throws UnsupportedEncodingException, DocumentException
   {
      Map<String, Object> result = null;
      Document document = parse(xml);
      Element root = document.getRootElement();
      if (root != null)
      {
         @SuppressWarnings("unchecked")
         Iterator<Element> eleIterator = root.elementIterator();
         Element son = eleIterator.next();
         if (son != null)
         {
            result = getMapFromELement(son);
         } // --------End If--------
      } // --------End If--------
      return result;
   }
   // ---------------------------------------------------------------------------


   /**
    * 从节点中获取 递归获取map 当有子节点时 value为map 否则为String
    * 
    * @param elements
    * @return
    */
   @SuppressWarnings("unchecked")
   private static Map<String, Object> getMapFromELement(Element elements)
   {
      Map<String, Object> map = new HashMap<String, Object>();
      for (Iterator<Element> it = elements.elementIterator(); it.hasNext();)
      {
         Element element = it.next();
         if (element.hasMixedContent())
         {
            String key = element.getName();
            Map<String, Object> sonMap = getMapFromELement(element);
            map.put(key, sonMap);
         }
         else
         {
            String key = element.getName();
            String value = element.getText();
            map.put(key, value);
         } // --------End If--------
      } // --------End For--------
      return map;
   }
   // ---------------------------------------------------------------------------

   /**
    * 截取root和mian节点 拼装为url
    * 
    * @param xml
    * @return
    * @throws UnsupportedEncodingException
    * @throws DocumentException
    */
   public static String getURLFromXml(String xml) throws UnsupportedEncodingException, DocumentException
   {
      String result = null;
      Document document = parse(xml);
      Element root = document.getRootElement();
      String FURL = root.getName();
      if (root != null)
      {
         @SuppressWarnings("unchecked")
         Iterator<Element> eleIterator = root.elementIterator();
         Element son = eleIterator.next();
         if (son != null)
         {
            String EURL = son.getName();
            result = "/" + FURL + "/" + EURL;// 将root节点和main节点拼装url
         } // --------End If--------
      } // --------End If--------
      return result;
   }
   // ---------------------------------------------------------------------------

   /**
    * 创建只有一个子节点的Element
    * 
    * @param root
    * @param main
    * @param key
    * @param value
    * @return
    */
   public static Element createResponseXmlSingleElement(String root, String main, String key, String value)
   {
      Element rootEle = DocumentHelper.createElement(root);
      Element mainEle = rootEle.addElement(main);
      mainEle.addElement(key).setText(value);
      return rootEle;
   }
   // ---------------------------------------------------------------------------

   public static Element createResponseXmlSingleElement(String root, String main, String key, int i)
   {
      return createResponseXmlSingleElement(root, main, key, String.valueOf(i));
   }
   // ---------------------------------------------------------------------------

   /**
    * @param root
    * @param main
    * @param data
    * @return
    */
   public static Element createResponseXmlMonolayer(String root, String main, Map<String, String> data)
   {
      Element rootEle = DocumentHelper.createElement(root);
      Element mainEle = rootEle.addElement(main);
      for (String key : data.keySet())
      {
         mainEle.addElement(key).setText(data.get(key));
      }
      return rootEle.createCopy();
   }
   // ---------------------------------------------------------------------------

   /**
    * 把document转xml
    * 
    * @param document
    * @return
    */
   public static String asXML(org.dom4j.Node document)
   {
      OutputFormat format = new OutputFormat();
      format.setEncoding("UTF-8");
      try
      {
         StringWriter out = new StringWriter();
         StandaloneWriter writer = new StandaloneWriter(out, format);
         if (document instanceof Document == false)
            writer.startDocument();
         writer.write(document);
         if (document instanceof Document == false)
            writer.endDocument();
         writer.flush();
         return out.toString();
      }
      catch (Exception e)
      {
         throw new RuntimeException("IOException while generating textual " + "representation: " + e.getMessage());
      } // --------End Try--------
   }
   // ---------------------------------------------------------------------------

   /**
    * 把root节点下所有转xml
    * 
    * @param root
    * @return
    */
   public static String asXML(Element root)
   {
      Document document = DocumentHelper.createDocument();
      document.setRootElement(root);

      OutputFormat format = new OutputFormat();
      format.setEncoding("UTF-8");
      try
      {
         StringWriter out = new StringWriter();
         StandaloneWriter writer = new StandaloneWriter(out, format);

         writer.write(document);

         writer.flush();
         return out.toString();
      }
      catch (Exception e)
      {
         throw new RuntimeException("IOException while generating textual " + "representation: " + e.getMessage());
      } // --------End Try--------
   }

   // public static void main(String[] args) throws DocumentException,
   // UnsupportedEncodingException
   // {
   // String root = "test";
   // String main = "tttt";
   // String key = "key";
   // String value = "value";
   // Document document= DocumentHelper.createDocument();
   // Element rootElement = document.addElement(root);
   // Element mainElement = rootElement.addElement(main);
   // mainElement.addElement(key).setText(value);
   //
   // System.out.println(asXML(document));
   // }

   /**
    * 仅用与logout的xml
    * 
    * @param result
    * @return
    */
   public static Element createLogoutResponseXml(long result)
   {
      return createLogoutResponseElement(String.valueOf(result));
   }
   // ---------------------------------------------------------------------------

   public static Element createLogoutResponseElement(String result)
   {
      Element root = DocumentHelper.createElement(ROOT_ELEMENT);
      Element logout = root.addElement(LOGOUT_ELEMENT);
      logout.addElement(RESULT).setText(result);

      return root;
   }
   // ---------------------------------------------------------------------------

}

// -------------------------------------end class-----------------------------
// 构造头的writer
class StandaloneWriter extends XMLWriter
{

   public StandaloneWriter(StringWriter out, OutputFormat format)
   {
      super(out, format);
   }
   // ---------------------------------------------------------------------------

   protected void writeDeclaration() throws IOException
   {
      OutputFormat format = getOutputFormat();
      String encoding = format.getEncoding();
      if (!format.isSuppressDeclaration())
      {
         writer.write("<?xml version=\"1.0\"");
         if (encoding.equals("UTF8"))
         {
            if (!format.isOmitEncoding())
               writer.write(" encoding=\"UTF-8\"");
         }
         else
         {
            if (!format.isOmitEncoding())
               writer.write(" encoding=\"" + encoding + "\"");

         } // --------End If--------
         writer.write(" standalone=\"yes\"");
         writer.write("?>");
         if (format.isNewLineAfterDeclaration())
            println();
      } // --------End If--------
   }
}
