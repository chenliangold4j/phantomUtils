package annotation.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class AnnotationUtil {

	@Test("ni hao")
	private String str= "test";
	
//	public static String getAnnotationValue(Field field,Class< ? extends Annotation> annotationClass) {
//		String result = null;
//		Annotation[] values = field.getAnnotationsByType(annotationClass);
//		for(Annotation annotation : values) {
//			if(annotation instanceof Test) {
//				 result = ((Test)annotation).value();
//			}
//		}
//		return result;
//	}
//	
//	public static void main(String[] args) {
//		Field[] fields = AnnotationUtil.class.getDeclaredFields();
//		
//		for(Field field:fields) 
//		{
//			String value = AnnotationUtil.getAnnotationValue(field, Test.class);
//			if(value != null)System.out.println(value);
//		}
//	}
//	
	
}
