package proxy;

public class Sender implements ISender
{

   public void send()
   {
      System.out.println("送");
   }

   public void sleep()
   {
      System.out.println("休息");
   }

}
