package http.util;

import java.io.IOException;
import java.nio.charset.UnsupportedCharsetException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpUtil
{
   public static String postToServer(String url, String str)
   {
      CloseableHttpClient httpclient = HttpClients.createDefault();
      String responseBody = null;
      
      try
      {
         HttpPost httpget = new HttpPost(url);
         if (str != null)
         {
            StringEntity stringEntity = new StringEntity(str, "UTF-8");
            stringEntity.setContentType("application/x-www-form-urlencoded");
            httpget.setEntity(stringEntity);
         }
         // System.out.println("Executing request " + httpget.getRequestLine());
         // Create a custom response handler
         ResponseHandler<String> responseHandler = new ResponseHandler<String>()
         {
            public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException
            {
               int status = response.getStatusLine().getStatusCode();
               if (status >= 200 && status < 300)
               {
                  HttpEntity entity = response.getEntity();
                  return entity != null ? EntityUtils.toString(entity) : null;
               }
               else
               {
                  throw new ClientProtocolException("Unexpected response status: " + status);
            }
            }
         };
         responseBody = httpclient.execute(httpget, responseHandler);
//         System.out.println(responseBody);
      }
      catch (UnsupportedCharsetException e)
      {
         e.printStackTrace();
      }
      catch (ClientProtocolException e)
      {
         e.printStackTrace();
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      finally
      {
         try
         {
            httpclient.close();
         }
         catch (IOException e)
         {
            e.printStackTrace();
         }
      }

      return responseBody;
   }
 //---------------------------------------------------------------------------
   
   public static void main(String[] args)
   {
      System.out.println(postToServer("http://192.168.0.109:9980/loginms","loginid=admin&secret=admin"));
   }
}
