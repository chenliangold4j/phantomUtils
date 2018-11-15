package reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtil
{
   int test= 1111;
   
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
   
   public static Field getFieldFormObj(String filedName,Object obj) throws IllegalArgumentException, IllegalAccessException 
   {
      Class<?> clazz = obj.getClass();
      Field[] fields = clazz.getDeclaredFields();
      Field result = null;
      for (Field field : fields)
      {
         if( field.getName().equals(filedName)) {
            field.setAccessible(true);
            System.out.println(field.get(obj).toString());
            break;
         }
      }
      return result;
   }
   
   @SuppressWarnings("null")
   public static String getFieldStringFormObj(String filedName,Object obj) throws IllegalArgumentException, IllegalAccessException 
   {
      Class<?> clazz = obj.getClass();
      Field[] fields = clazz.getDeclaredFields();
      Field result = null;
      for (Field field : fields)
      {
         if( field.getName().equals(filedName)) {
            field.setAccessible(true);
            System.out.println(field.get(obj).toString());
            break;
         }
      }
      return result.get(obj).toString();
   }
   
   
   
   public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
   {
     ReflectUtil reflectUtil = new ReflectUtil();
     Method method = getMethodFormClass("getFieldFormObj", ReflectUtil.class);
     Object result = method.invoke(reflectUtil, "test",reflectUtil);
     
   }

}
