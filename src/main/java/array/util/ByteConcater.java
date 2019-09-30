package array.util;

import java.util.Arrays;

public class ByteConcater
{
   
   private byte[] array = null;
   
   
   public void putArray(byte[] bytes,int start,int length) {
      if(array == null) {
         array = new byte[length];
         System.arraycopy(bytes, start, array, 0, length);
      }else {
         byte[] result = new byte[array.length + length];
         System.arraycopy(array, 0, result, 0, array.length);
         System.arraycopy(bytes,start, result, array.length, length);
         this.array = result;
      }
   }
   
   public byte[] getArray() {
      return array;
   }
   
}

