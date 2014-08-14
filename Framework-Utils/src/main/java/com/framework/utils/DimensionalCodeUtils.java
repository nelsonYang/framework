package com.framework.utils;


import com.frameworkLog.factory.LogFactory;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import org.slf4j.Logger;


/**
 *
 * @author nelson
 */
public class DimensionalCodeUtils {
    private DimensionalCodeUtils(){}
    private static final Logger logger = LogFactory.getInstance().getLogger(DimensionalCodeUtils.class);

    public static String getDimensionalCode(String content, String uploadDir, String uploadURL, String user) {
        String url = null;
        try {
           // content = new String(content.getBytes("UTF-8"), "ISO-8859-1");
            String fileName = user + "_" + System.currentTimeMillis() + "_dimentionalCode.jpg";
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

            Map hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            // //内容所使用编码 
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400, hints);
            String path = uploadDir + File.separator + fileName;
            File file = new File(path);
            url = MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file, uploadURL, fileName);
        } catch (Exception e) {
            logger.error("二维码出错",e);
            e.printStackTrace();
        }
        return url;
    }


    static class MatrixToImageWriter {

        private static final int BLACK = 0xFF000000;
        private static final int WHITE = 0xFFFFFFFF;

        private MatrixToImageWriter() {
        }

        public static BufferedImage toBufferedImage(BitMatrix matrix) {
            int width = matrix.getWidth();
            int height = matrix.getHeight();
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
                }
            }
            return image;
        }

        public static String writeToFile(BitMatrix matrix, String format, File file, String uploadURL, String fileName)
                throws IOException {
            String url = null;
            BufferedImage image = toBufferedImage(matrix);
            if (!ImageIO.write(image, format, file)) {
                throw new IOException("Could not write an image of format " + format + " to " + file);
            } else {
                url = uploadURL + File.separator + fileName;
            }
            return url;
        }

        public static void writeToStream(BitMatrix matrix, String format, OutputStream stream)
                throws IOException {
            BufferedImage image = toBufferedImage(matrix);
            if (!ImageIO.write(image, format, stream)) {
                throw new IOException("Could not write an image of format " + format);
            }
        }
    }
}
