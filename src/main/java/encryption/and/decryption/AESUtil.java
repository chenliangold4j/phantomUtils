package encryption.and.decryption;

import java.io.UnsupportedEncodingException;

/**
 * Created by hdwang on 2019/1/17.
 */

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;


/**
 *  加密工具类
 */
public class AESUtil {

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";//默认的加密算法

    /**
     * AES 加密操作
     *
     * @param content 待加密内容
     * @param password 加密密码
     * @param iv 使用CBC模式，需要一个向量iv，可增加加密算法的强度
     * @return 加密数据
     */
    public static byte[] encrypt(String content, String password,String iv) {
        try {
            //创建密码器
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            //密码key(超过16字节即128bit的key，需要替换jre中的local_policy.jar和US_export_policy.jar，否则报错：Illegal key size)
            SecretKeySpec keySpec = new SecretKeySpec(password.getBytes("utf-8"),KEY_ALGORITHM);

            //向量iv
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes("utf-8"));

            //初始化为加密模式的密码器
            cipher.init(Cipher.ENCRYPT_MODE,keySpec,ivParameterSpec);

            //加密
            byte[] byteContent = content.getBytes("utf-8");
            byte[] result = cipher.doFinal(byteContent);

            return result;
        } catch (Exception ex) {
           System.out.println(ex.getMessage());
        }
        return null;
    }

    /**
     * AES 解密操作
     *
     * @param content 密文
     * @param password 密码
     * @param iv 使用CBC模式，需要一个向量iv，可增加加密算法的强度
     * @return 明文
     */
    public static String decrypt(byte[] content, byte[] password,byte[] iv) {

        try {
            //创建密码器
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            //密码key
            SecretKeySpec keySpec = new SecretKeySpec(password,KEY_ALGORITHM);

            //向量iv
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            //初始化为解密模式的密码器
            cipher.init(Cipher.DECRYPT_MODE,keySpec,ivParameterSpec);

            //执行操作
            byte[] result = cipher.doFinal(content);

            return new String(result,"utf-8");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException
   {
      String content= "9ZX3XeZxfAo5iAkN0Apj3b6fp68AWLKrKE52BYAzA25My+gJuZkeaTeRE4GvHbWvtv/Wl4gNXw5ZaZbUef0GUuajb38c9SjiaFj+hPl2L+wslB0FBXCbpUaf1KmyN1mtWjtcqRnYc5cvVyHaS4C4uTVmyjTfwZgwS6aO0QnI/IUYrsyzjUlDoKE71DENuLrzDeST68/tNDBB2HhSGqH59Jpck40ipmGpMIxjR88P6rUxpDAHYN5BFzbDxTPJHmBASdAZUAThv7TN8qzAfh+c4q3/9U+zIaKkZL5H+12YpkY0XUw2r1RPzw1TjV+bRfOfq2XojWOFQYSjxM4ldcpTirY1bWD3ZLnjW7MDihWzltbTyaP4AoBSDrRy/u9Yz5LwC4oos2adIjkh8kVnPQy/SstV6HKHdj+c/QeD0jKX9mDp53+FOGmiEvThXnLNlrzx4gL4+3mgmv0SQxivinBXjmFSDJOQcl8Y8chuJPUZhuo=";
      Base64 decoder = new Base64();
      byte[] datas = decoder.decode(content);
      byte[] iv = new byte[] { (byte) 0xe0, 0x4f, (byte) 0xd0, 0x20, (byte) 0xea, 0x3a, 0x69, 0x10, (byte) 0xa2, (byte) 0xd8, 0x08, 0x00, 0x2b, 0x30, 0x30,  (byte) 0x9d };
      byte[]  mkey = "1234567890ABCDEF".getBytes("UTF-8");
      System.out.println(decrypt(datas, mkey, iv));
   }


}