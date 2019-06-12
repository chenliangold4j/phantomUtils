package encryption.and.decryption;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class SHA1Util
{

   public static void main(String[] args) throws UnsupportedEncodingException
   {
      String str = "1aniOUpLGBz/AAyrLl/XHcMzfypIVFY4LUw0MDAtQkk0MC0w";
      Base64 decoder = new Base64();
      byte[] bytes = decoder.decode(str);
      // zF4KdOi2pPGIwlODJDIVhDe+heU=;

      System.out.println(decoder.encodeToString(SHA1(bytes)));

   }

   public static byte[] SHA1(byte[] data)
   {
      MessageDigest digest = null;
      try
      {
         digest = MessageDigest.getInstance("SHA-1");
      }
      catch (NoSuchAlgorithmException e)
      {
         e.printStackTrace();
         return null;
      }
      digest.update(data);
      byte[] messageDigest = digest.digest();
      return messageDigest;
   }
}
