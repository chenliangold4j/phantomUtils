package threadlocal.util;

import org.omg.IOP.TAG_CODE_SETS;

public class test
{
   
   public static void main(String[] args) throws InterruptedException
   {
      Bank bank = new Bank();
      Transfer t = new Transfer(bank);
      Thread t1 = new Thread(t);
      t1.start();
      Thread t2 = new Thread(t);
      t2.start();
      t1.join();
      t2.join();
      System.out.println(bank.get());
   }
   
}

class Transfer implements Runnable{

   
   Bank bank;
   public Transfer(Bank bank) {
      this.bank = bank;
   }
   
   public void run()
   {
      for(int i = 0;i<10;i++) {
        bank.set();
        System.out.println(Thread.currentThread()+":"+bank.get());
      }
   }
}

class Bank {
   ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {

      @Override
      protected Integer initialValue()
      {
         return 100;
      }
      
   };
   
   public int get() {
      return threadLocal.get();
   }
   
   public void set() {
      threadLocal.set(threadLocal.get()+10);
   }
}
