package xeger.util;

import nl.flotsam.xeger.Xeger;

public class XegerUtil
{
     static String getXegerString(String regex) {
       Xeger generator = new Xeger(regex); 
       String result = generator.generate(); 
       return result;
    }
    
   public static void main(String[] args)
   {
       for(int i=0;i<30;i++)
      System.out.println(getXegerString("[a-z]{5,8}"));
   }
}
