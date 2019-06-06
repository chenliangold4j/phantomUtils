package common.util;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;

public class Base64ImageUtil
{

	public static boolean Base64ToImage(String imgStr, String imgFilePath)
	{ // 对字节数组字符串进行Base64解码并生成图片
		boolean result =false;
		if (imgStr == null) // 图像数据为空
			return result ;

		Base64 decoder = new Base64();
		try
		{
			// Base64解码
			byte[] b = decoder.decode(imgStr);
			// for (int i = 0; i < b.length; ++i) {
			// if (b[i] < 0) {// 调整异常数据
			// b[i] += 256;
			// }
			// }

			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			
			result =true;

			return result;
		} catch (Exception e)
		{
			return result;
		}

	}

	public static void main(String[] args) throws Exception
	{
		String testpath = "c:\\test\\image";
		int i =testpath.lastIndexOf("\\");
		System.out.println(i);
		String last = testpath.substring(i-1);
		System.out.println(last);
	}
	// final Base64 base64 = new Base64();
	// final String text = "字串文字";
	// final byte[] textByte = text.getBytes("UTF-8");
	// //编码
	// final String encodedText = base64.encodeToString(textByte);
	// System.out.println(encodedText);
	// //解码
	// System.out.println(new String(base64.decode(encodedText), "UTF-8"));

}
