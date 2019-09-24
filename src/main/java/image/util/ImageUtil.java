package image.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil
{

   //裁剪jpg 或其他格式照片
   public static byte[] cutBitmapIncrease(byte[] image, int x, int y, int width, int height, double factor) throws IOException
   {
      ByteArrayInputStream in = new ByteArrayInputStream(image);
      BufferedImage bufferedImage = ImageIO.read(in);
      int maxWidth = bufferedImage.getWidth();
      int maxHight = bufferedImage.getHeight();
      int sx = x - (int) ((double) width * factor);
      int sy = y - (int) ((double) height * factor);
      int sw = width + (int) ((double) width * factor * 2.0D);
      int sh = height + (int) ((double) height * factor * 2.0D);
      int rx = sx < 0 ? 0 : sx;
      int ry = sy < 0 ? 0 : sy;
      int rw = sw > maxWidth - rx ? maxWidth - rx : sw;
      int rh = sh > maxHight - ry ? maxHight - ry : sh;
      BufferedImage resultimage = cropImage(bufferedImage, rx, ry, rw + rx, rh + ry);
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      ImageIO.write(resultimage, "jpg", byteArrayOutputStream);
      return byteArrayOutputStream.toByteArray();
   }
   
   public static BufferedImage cropImage(BufferedImage bufferedImage, int startX, int startY, int endX, int endY)
   {
      int width = bufferedImage.getWidth();
      int height = bufferedImage.getHeight();
      if (startX == -1)
      {
         startX = 0;
      }
      if (startY == -1)
      {
         startY = 0;
      }
      if (endX == -1)
      {
         endX = width - 1;
      }
      if (endY == -1)
      {
         endY = height - 1;
      }
      BufferedImage result = new BufferedImage(endX - startX, endY - startY, 4);
      for (int x = startX; x < endX; ++x)
      {
         for (int y = startY; y < endY; ++y)
         {
            int rgb = bufferedImage.getRGB(x, y);
            result.setRGB(x - startX, y - startY, rgb);
         }
      }
      return result;
   }
}
