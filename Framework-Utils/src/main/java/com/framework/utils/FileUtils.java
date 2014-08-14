package com.framework.utils;


import com.frameworkLog.factory.LogFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import org.slf4j.Logger;


/**
 *
 * @author Administrator
 */
public class FileUtils {

    private final static Logger logger = LogFactory.getInstance().getLogger(FileUtils.class);

    private FileUtils() {
    }

    public static String getAbsolutePath(String webRootPath, String url, String baseUrl) {
        int index = url.indexOf(baseUrl);
        String path = url.substring(index + baseUrl.length() - 1);
        return webRootPath + path;
    }

    public static String writeFileToDir(final byte[] contents, String fileName, final String filePath,String contextURL) {
        FileOutputStream fos = null;
        String uploadURL = "";
        boolean isSuccess = false;
        fileName = System.currentTimeMillis() + fileName;
        try {
            if (filePath != null && !filePath.isEmpty()) {
                File file = new File(filePath);
                if (file.exists()) {
                    isSuccess = true;
                } else {
                    isSuccess = file.mkdirs();
                }
            }
            if (isSuccess) {
                String path = filePath + File.separator + fileName;
                fos = new FileOutputStream(path);
                fos.write(contents);
                fos.flush();
                uploadURL = contextURL + File.separator + fileName;
            } else {
                logger.error("新建文件夹出错");
            }
        } catch (FileNotFoundException ex) {
            logger.error("文件无法找到", ex);
        } catch (IOException ex) {
            logger.error("写文件出错", ex);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ex) {
                logger.error("写文件出错", ex);
            }
        }
        return uploadURL;
    }

    public static File getFile(String uploadDir, String filePath) {
        File file = null;
        if (filePath != null && !filePath.isEmpty()) {
            int index = filePath.lastIndexOf(File.separator);
            if (index > -1) {
                String fileName = filePath.substring(index + 1);
                String path = uploadDir + File.separator + fileName;
                File tempFile = new File(path);
                if (tempFile.exists()) {
                    file = tempFile;
                }
            }
        }
        return file;

    }
    
    public static boolean deleteFile(String uploadDir, String filePath){
        boolean isSuccess = false;
         if (filePath != null && !filePath.isEmpty()) {
            int index = filePath.lastIndexOf(File.separator);
            if (index > -1) {
                String fileName = filePath.substring(index + 1);
                String path = uploadDir + File.separator + fileName;
                File file = new File(path);
                if (file.exists()) {
                    file.delete();
                    isSuccess = true;
                }else{
                    isSuccess = true;
                }
            }
        }
         return isSuccess;
    }
    
     public static boolean checkFileType(String fileName,String[] allowFiles) {
        Iterator<String> type = Arrays.asList(allowFiles).iterator();
        while (type.hasNext()) {
            String ext = type.next();
            if (fileName.toLowerCase().endsWith(ext)) {
                return true;
            }
        }
        return false;
    }
}
