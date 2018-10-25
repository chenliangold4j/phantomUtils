package encryptionAndDecryption;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionAndDecryption
{
//   java加密之AES/CBC/PKCS5Padding
   
  private static String sKey = "1234567890ABCDEF";
  private static int[] cKeys = {0xe0, 0x4f, 0xd0, 0x20, 0xea, 0x3a, 0x69, 0x10, 0xa2, 0xd8, 0x08, 0x00, 0x2b, 0x30, 0x30, 0x9d};
  static  String cKey = "keys";
  
  static String  testString = "9ZX3XeZxfAo5iAkN0Apj3b6fp68AWLKrKE52BYAzA25My+gJuZkeaTeRE4GvHbWvtv/Wl4gN\r\n" + 
           "Xw5ZaZbUef0GUnMYWiOOy8NkvZoR0avJTu47xVtzOJspNC3nywhRu8bsV2TTdQA6EeuTe4DM\r\n" + 
           "vuWIMDtqWJiHGxiDD32nR4X8ksIeibPUYYJzg083LVJHtpZuc239T0/hgM1cMZVyIrtNnPgb\r\n" + 
           "SV9M7Zdlf8XimU1NoOIjxN9EdciWvDZe/065nVED80z99oIqwcHGxFuNLWpF1VjNzl6w2s3X\r\n" + 
           "dsu//LqmHBzqaMCtxV/Izf6Xv9lcDGlgaUQoMthvaRN84NTOwLHjk3PhiM+fI/2SLFjID8I+\r\n" + 
           "C0Tip/cTOZuiXHT8IzOJuX4V5Wm/TgXcbHZXb2kFVr/7Moy4K9YqCihHNbdT24TX0qOM91wl\r\n" + 
           "NViGZQZ86bfUeyQzAzD8iSSgyspLrqDjKWtInD1T+mlar7WFKIgtUQI46ws=";
   
           
   public static String Encrypt(String sSrc, String sKey) throws Exception {  
      if (sKey == null) {  
          System.out.print("Key为空null");  
          return null;  
      }  
      // 判断Key是否为16位  
      if (sKey.length() != 16) {  
          System.out.print("Key长度不是16位");  
          return null;  
      }  
      byte[] raw = sKey.getBytes("utf-8");  
      SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"  
      IvParameterSpec iv = new IvParameterSpec(cKey.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度  
      cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);  
      byte[] encrypted = cipher.doFinal(sSrc.getBytes()); 
      String reuslt= Base64.getEncoder().encodeToString(encrypted);
      return reuslt;//此处使用BASE64做转码功能，同时能起到2次加密的作用。  
  }  
   
// 解密  
   public static String Decrypt(String sSrc, String sKey) throws Exception {  
       try {  
           // 判断Key是否正确  
           if (sKey == null) {  
               System.out.print("Key为空null");  
               return null;  
           }  
           // 判断Key是否为16位  
           if (sKey.length() != 16) {  
               System.out.print("Key长度不是16位");  
               return null;  
           }  
           byte[] raw = sKey.getBytes("utf-8");  
           SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
           Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
           IvParameterSpec iv = new IvParameterSpec(cKey.getBytes());  
           cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);  
           byte[] encrypted1 = Base64.getDecoder().decode(sSrc);//先用base64解密  
           try {  
               byte[] original = cipher.doFinal(encrypted1);  
               String originalString = new String(original);  
               return originalString;  
           } catch (Exception e) {  
               System.out.println(e.toString());  
               return null;  
           }  
       } catch (Exception ex) {  
           System.out.println(ex.toString());  
           return null;  
       }  
   }  
   
//   public static void main(String[] args) throws Exception {  
//      /* 
//       * 加密用的Key 可以用26个字母和数字组成，最好不要用保留字符，虽然不会错，至于怎么裁决，个人看情况而定 
//       * 此处使用AES-128-CBC加密模式，key需要为16位。 
//       */  

      // 需要加密的字串  
//      String cSrc = "{data:[{'name':'你好','age':20},{'name':'zd','age':18}]}";  
//      System.out.println(cSrc);  
//      // 加密  
//      long lStart = System.currentTimeMillis();  
//      String enString = Encrypt(cSrc, cKey);  
//      System.out.println("加密后的字串是：" + enString);  
//
//      long lUseTime = System.currentTimeMillis() - lStart;  
//      System.out.println("加密耗时：" + lUseTime + "毫秒");  
//      // 解密  
//      lStart = System.currentTimeMillis();  
//      String DeString = Decrypt(testString, cKey);  
//      System.out.println("解密后的字串是：" + DeString);  
//      lUseTime = System.currentTimeMillis() - lStart;  
//      System.out.println("解密耗时：" + lUseTime + "毫秒");  
//  }
   
   
   
   private static String hexString = "0123456789ABCDEF";
 
   /*
   * 将字符串编码成16进制数字,适用于所有字符（包括中文）
   */
   public static String encode(String str) {
   // 根据默认编码获取字节数组
   byte[] bytes = str.getBytes();
   StringBuilder sb = new StringBuilder(bytes.length * 2);
   // 将字节数组中每个字节拆解成2位16进制整数
   for (int i = 0; i < bytes.length; i++) {
   sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
   sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
   }
   return sb.toString();
   }
   
   /*
   * 将16进制数字解码成字符串,适用于所有字符（包括中文）
   */
   public static String decode(String bytes) {
   ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
   // 将每2位16进制整数组装成一个字节
   for (int i = 0; i < bytes.length(); i += 2)
   baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
   .indexOf(bytes.charAt(i + 1))));
   return new String(baos.toByteArray());
   }
   
   
   public static void main(String[] args) {
      System.out.println(encode("中文"));
      System.out.println(decode(encode("中文")));
       
      }

}
