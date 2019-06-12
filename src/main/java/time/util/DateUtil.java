package time.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil
{
      
   public static String USTimeToYMDHMS(String uStimeString)
   {
      Date date = parse(uStimeString, "EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String timeFormat = sdf.format(date);
      return timeFormat;
   }

   public static Date parse(String str, String pattern, Locale locale)
   {
      if (str == null || pattern == null)
      {
         return null;
      }
      try
      {
         return new SimpleDateFormat(pattern, locale).parse(str);
      }
      catch (ParseException e)
      {
         e.printStackTrace();
      }
      return null;
   }

   public static String format(Date date, String pattern, Locale locale)
   {
      if (date == null || pattern == null)
      {
         return null;
      }
      return new SimpleDateFormat(pattern, locale).format(date);
   }
   
}
