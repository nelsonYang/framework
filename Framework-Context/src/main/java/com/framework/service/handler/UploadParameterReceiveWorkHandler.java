package com.framework.service.handler;

import com.framework.context.ApplicationContext;
import com.framework.context.InvocationContext;
import com.framework.entity.FileEntity;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.service.api.WorkHandler;
import com.framework.utils.FileUtils;
import com.frameworkLog.factory.LogFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;

/**
 *
 * @author Administrator
 */
public class UploadParameterReceiveWorkHandler implements WorkHandler {

    private final Logger logger = LogFactory.getInstance().getLogger(UploadParameterReceiveWorkHandler.class);
    private WorkHandler nextHandler;
    private String[] allowFiles = {".rar", ".doc", ".docx", ".zip", ".pdf", ".txt", ".swf", ".wmv", ".gif", ".png", ".jpg", ".jpeg", ".bmp", ".mp3", ".mp4"};

    public UploadParameterReceiveWorkHandler(WorkHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void execute() {

        //验证参数
        ApplicationContext applicationContext = ApplicationContext.CTX;
        InvocationContext invocationContext = applicationContext.getInvocationContext();
        HttpServletRequest request = applicationContext.getHttpServletRequest();
        String contentType = request.getContentType();
        logger.debug("contentType=" + contentType);
        boolean isSuccess = false;
        boolean isValidateFileName = true;
        if (contentType == null || contentType.toLowerCase().contains("application/x-www-form-urlencoded")) {
            isSuccess = true;
        } else {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(1024 * 1024);
            ServletFileUpload upload = new ServletFileUpload(factory);
            InputStream in = null;
            try {
                List<FileItem> list = (List<FileItem>) upload.parseRequest(request);
                List<FileEntity> fileList = new ArrayList<FileEntity>(list.size());
                FileEntity fileEntity;
                String value;
                String fieldName;
                for (FileItem item : list) {
                    fieldName = item.getFieldName();
                    logger.debug("fieldName=" + fieldName);
                    if (!"request".equals(fieldName)) {
                        fileEntity = new FileEntity();
                        String fileUrl = item.getName();
                        logger.debug("fileUrl=" + fileUrl);
                        if (fileUrl != null) {
                            if (fileUrl.contains("\\")) {
                                int start = fileUrl.lastIndexOf("\\");
                                String filename = fileUrl.substring(start + 1);
                                if (FileUtils.checkFileType(filename, allowFiles)) {
                                    fileEntity.setFileName(filename);
                                    in = item.getInputStream();
                                    logger.debug("item.size=".concat(String.valueOf(item.getSize())));
                                    byte[] buf = new byte[(int) item.getSize()];
                                    in.read(buf);
                                    fileEntity.setContents(buf);
                                    fileList.add(fileEntity);
                                } else {
                                    isValidateFileName = false;
                                    break;
                                }
                            } else {
                                if (FileUtils.checkFileType(fileUrl, allowFiles)) {
                                    fileEntity.setFileName(fileUrl);
                                    in = item.getInputStream();
                                    logger.debug("item.size=".concat(String.valueOf(item.getSize())));
                                    byte[] buf = new byte[(int) item.getSize()];
                                    in.read(buf);
                                    fileEntity.setContents(buf);
                                    fileList.add(fileEntity);
                                } else {
                                    isValidateFileName = false;
                                    break;
                                }
                            }
                        }
                    } else {
                        in = item.getInputStream();
                        byte[] buf = new byte[(int)item.getSize()];
                        logger.debug("item.size=".concat(String.valueOf(item.getSize())));
                        in.read(buf);
                        value = new String(buf, "UTF-8");
                        request.setAttribute(fieldName, value);
                    }
                }
                invocationContext.setFileList(fileList);
                isSuccess = true;
            } catch (Exception ex) {
                logger.error("上出文件出现错误", ex);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException ex) {
                        logger.error("上出文关闭出现错误", ex);
                    }
                }
            }
        }
        if (isSuccess) {
            nextHandler.execute();
        } else {
            if (!isValidateFileName) {
                applicationContext.getResponseWriter(ResponseTypeEnum.NO_DATA_JSON).toWriteErrorMsg(8000, "参数验证错误");
            }
        }
    }
}
