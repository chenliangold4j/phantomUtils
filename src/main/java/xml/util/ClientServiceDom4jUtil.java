package xml.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import reflect.ConverterUtil;

public class ClientServiceDom4jUtil
{

   /**
    * 通过xml获取document
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
    * 遍历对象的属性 生成element 其中会将时间格式转换 并对deviceNumber做了处理
    * 
    * @param elementName
    * @param obj
    * @return
    */
   public static Element getElementFromBeanFilterSn(String elementName, Object obj)
   {
      Element element = DocumentHelper.createElement(elementName);
      Field[] fields = obj.getClass().getDeclaredFields();
      String deviceNumber = null;
      String deviceNumberValue = null;
      for (Field field : fields)
      {
         try
         {
            field.setAccessible(true);
            if (Date.class.isAssignableFrom(field.getType()) && field.get(obj) != null)
            {
               Date date = (Date) field.get(obj);
               SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               String valString = formatter.format(date);
               String name = field.getName();
               if (name != null && valString != null)
                  element.addElement(name).setText(valString);
            }
            else if (field.getName().equals("device_number"))
            {
               deviceNumber = CommonConst.DEVICE_NUMBER;
               if (deviceNumber != null && deviceNumberValue != null)
               {
                  element.addElement(deviceNumber).setText(deviceNumberValue);
               }
            }
            else if (field.getName().equals("serial_number"))
            {
               deviceNumberValue = field.get(obj) != null ? field.get(obj).toString() : null;
               if (deviceNumber != null && deviceNumberValue != null)
               {
                  element.addElement(deviceNumber).setText(deviceNumberValue);
               }
            }
            else
            {
               String name = field.getName();
               String valString = field.get(obj) != null ? field.get(obj).toString() : null;
               if (name != null && valString != null)
                  element.addElement(name).setText(valString);
            }
         }
         catch (IllegalArgumentException e)
         {
            e.printStackTrace();
         }
         catch (IllegalAccessException e)
         {
            e.printStackTrace();
         }
      }
      return element;
   }

   @SuppressWarnings("unchecked")
   public static <T> T ElementConvertToBean(Element root, Class<T> clazz)
   {
      T result = null;
      try
      {
         result = clazz.newInstance();
      }
      catch (Exception e)
      {
         return result;
      }
      Field[] fields = clazz.getDeclaredFields();
      Iterator<Element> eleIterator = root.elementIterator();
      Element son = eleIterator.next();
      for (Iterator<Element> it = son.elementIterator(); it.hasNext();)
      {
         Element element = it.next();
         String name = element.getName();
         for (Field field : fields)
         {
            if (field.getName().equals(name))
            {
               String value = element.getText();
               Object obj = ConverterUtil.converterToBaseType(value, field.getType());
               if (obj != null)
               {
                  field.setAccessible(true);
                  try
                  {
                     field.set(result, obj);
                  }
                  catch (IllegalArgumentException e)
                  {
                     e.printStackTrace();
                  }
                  catch (IllegalAccessException e)
                  {
                     e.printStackTrace();
                  }
               }
            }
         }
      }
      return result;
   }
   

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
    * 从xml中获取公共bean 返回的格式详见bean
    * 
    * @param xml
    * @return
    * @throws UnsupportedEncodingException
    * @throws DocumentException
    */
   public static XMLCommonBean getXmlBeanFromXml(String xml) throws UnsupportedEncodingException, DocumentException
   {
      XMLCommonBean xmlCommonBean = new XMLCommonBean();
      Map<String, Object> map = null;
      Document document = parse(xml);
      Element root = document.getRootElement();
      if (root != null)
      {
         xmlCommonBean.setRoot(root.getName());// 设置root节点
         @SuppressWarnings("unchecked")
         Iterator<Element> eleIterator = root.elementIterator();
         Element son = eleIterator.next();
         if (son != null)
         {
            xmlCommonBean.setMain(son.getName());// 设置main节点
            map = getMapFromELement(son);
            xmlCommonBean.setDataMap(map);// 设置datamap数据
         } // --------End If--------
      } // --------End If--------
      return xmlCommonBean;
   }
   // ---------------------------------------------------------------------------

   /**
    * 从root节点中获取 xmlbean
    * 
    * @param element
    * @return
    * @throws UnsupportedEncodingException
    * @throws DocumentException
    */
   public static XMLCommonBean getXmlBeanFromXml(Element element) throws UnsupportedEncodingException, DocumentException
   {
      XMLCommonBean xmlCommonBean = new XMLCommonBean();
      Map<String, Object> map = null;
      Element root = element;
      if (root != null)
      {
         xmlCommonBean.setRoot(root.getName());// 设置root节点
         @SuppressWarnings("unchecked")
         Iterator<Element> eleIterator = root.elementIterator();
         Element son = eleIterator.next();
         if (son != null)
         {
            xmlCommonBean.setMain(son.getName());// 设置main节点
            map = getMapFromELement(son);
            xmlCommonBean.setDataMap(map);// 设置datamap数据
         } // --------End If--------
      } // --------End If--------
      return xmlCommonBean;
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
   public static Element createResponseXmlSingleElement(String root, String main, String key, Long i)
   {
      return createResponseXmlSingleElement(root, main, key, String.valueOf(i));
   }
   // ---------------------------------------------------------------------------

   /**
    * 主节点下只有单层的xml
    * 
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
      return rootEle;
   }
   // ---------------------------------------------------------------------------

   /**
    * 新增一个子节点
    */
   public static Element addSonElement(Element rootELe, String key, String value)
   {
      @SuppressWarnings("unchecked")
      Iterator<Element> eleIterator = rootELe.elementIterator();
      Element son = eleIterator.next();
      son.addElement(key).setText(value);
      return rootELe;
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
      if (document == null)
         return null;

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
