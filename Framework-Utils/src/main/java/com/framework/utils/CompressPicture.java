package com.framework.utils;

import com.frameworkLog.factory.LogFactory;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;


public class CompressPicture {

    private CompressPicture() {
    }
    private static final Logger logger = LogFactory.getInstance().getLogger(CompressPicture.class);

    public static String compress(String filePath, String ext, String user, String uploadDir, String uploadURL, float quality, int width, int height, int type) {
        File scaleImageFile = null;
        String fileName;
        // 生成缩略图
        try {
            if (type == 1) {
                fileName = user + "_" + System.currentTimeMillis() + "_middle";
                File orignalFile = new File(filePath);
                InputStream is = new FileInputStream(orignalFile);// 通过文件名称读取
                BufferedImage buff = ImageIO.read(is);
                int imgWidth = buff.getWidth();
                int imgHeight = buff.getHeight();
                uploadDir = uploadDir + fileName + "." + ext;
                uploadURL = uploadURL + fileName + "." + ext;
                scaleImageFile = new File(uploadDir);
                Thumbnails.of(filePath).size(imgWidth / 2, imgHeight / 2).outputQuality(quality).toFile(scaleImageFile);
                is.close();
            } else {
                fileName = user + "_" + System.currentTimeMillis() + "_small";
                uploadDir = uploadDir + fileName + "." + ext;
                uploadURL = uploadURL + fileName + "." + ext;
                scaleImageFile = new File(uploadDir);
                Thumbnails.of(filePath).size(width, height).outputQuality(quality).toFile(scaleImageFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("压缩图片出错", e);
        }
        return uploadURL;

    }
}
