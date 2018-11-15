package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestProxy implements InvocationHandler
{

   private Object object;
   
   
   
   public TestProxy(Object object)
   {
      super();
      this.object = object;
   }



   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
   {
      System.out.println("前");
      method.invoke(object, args);
      System.out.println("后");
      return null;
   }
   
   public static void main(String[] args)
   {
      ISender real = new Sender();

      //    我们要代理哪个真实对象，就将该对象传进去，最后是通过该真实对象来调用其方法的
      InvocationHandler handler = new TestProxy(real);

      /*
       * 通过Proxy的newProxyInstance方法来创建我们的代理对象，我们来看看其三个参数
       * 第一个参数 handler.getClass().getClassLoader() ，我们这里使用handler这个类的ClassLoader对象来加载我们的代理对象
       * 第二个参数realSubject.getClass().getInterfaces()，我们这里为代理对象提供的接口是真实对象所实行的接口，表示我要代理的是该真实对象，这样我就能调用这组接口中的方法了
       * 第三个参数handler， 我们这里将这个代理对象关联到了上方的 InvocationHandler 这个对象上
       */
      ISender proxyObj = (ISender)Proxy.newProxyInstance(handler.getClass().getClassLoader(), real
              .getClass().getInterfaces(), handler);
      
      System.out.println(proxyObj.getClass().getName());
      proxyObj.sleep();
      proxyObj.send();
   }

}
