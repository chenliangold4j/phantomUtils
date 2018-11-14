package reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtil
{
   
   public static Method getMethodFormClass(String methodName,Class<?> clazz) 
   {
      Method[] methods =  clazz.getMethods();
      Method result = null;
      for (Method method : methods)
      {
         if( method.getName().equals(methodName)) {
            result = method;
            System.out.println(methodName);
            break;
         }
      }
      return result;
   }
   
   public static Object InvokeMethod(String methodName,Class<?> clazz,Object instance, Object... args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException 
   {
      Method[] methods =  clazz.getMethods();
      Method target = null;
      for (Method method : methods)
      {
         if( method.getName().equals(methodName)) {
            target = method;
            System.out.println(methodName);
            break;
         }
      }
      Object result = target.invoke(instance,args);
      return result;
   }
   
   
   
   public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
   {
     ReflectUtil reflectUtil = new ReflectUtil();
     Method method = getMethodFormClass("getMethodFormClass", ReflectUtil.class);
     Object result = method.invoke(ReflectUtil.class, "equals",ReflectUtil.class);
   }

}
