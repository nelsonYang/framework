package com.framework.service.handler;

import com.framework.context.ApplicationContext;
import com.framework.context.InvocationContext;
import com.framework.entity.FileEntity;
import com.framework.field.FieldHandler;
import com.framework.field.FieldManager;
import com.framework.fieldtype.FieldTypeHandler;
import com.framework.service.api.WorkHandler;
import com.framework.utils.FileUtils;
import com.frameworkLog.factory.LogFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;

/**
 *
 * @author Administrator
 */
public class UploadBatchMapParameterValidateWorkHandler implements WorkHandler {

    private final Logger logger = LogFactory.getInstance().getLogger(UploadBatchMapParameterValidateWorkHandler.class);
    private WorkHandler nextHandler;
    private String[] parameters;
    private String[] minorParameters;
    private String act;
    private String[] allowFiles = {".rar", ".doc", ".docx", ".zip", ".pdf", ".txt", ".swf", ".wmv", ".gif", ".png", ".jpg", ".jpeg", ".bmp", ".mp3", ".mp4"};

    public UploadBatchMapParameterValidateWorkHandler(WorkHandler nextHandler, String[] parameters, String[] minorParameters, String act) {
        this.nextHandler = nextHandler;
        this.parameters = parameters;
        this.minorParameters = minorParameters;
        this.act = act;
    }

    @Override
    public void execute() {

        //验证参数
        ApplicationContext applicationContext = ApplicationContext.CTX;
        InvocationContext invocationContext = applicationContext.getInvocationContext();
        Map<String, String[]> bathMapParameters = invocationContext.getBatchMapParameters();
        String[] values;
        FieldTypeHandler fieldTypeHandler;
        FieldManager fieldManager = applicationContext.getFieldManager();
        FieldHandler fieldHandler;
        Map<String, FieldHandler> fieldMap = fieldManager.getFieldMap(act);
        boolean isSuccess = true;
        for (String parameter : parameters) {
            fieldHandler = fieldMap.get(parameter);
            values = bathMapParameters.get(parameter);
            if (fieldHandler == null) {
                applicationContext.setEncryptCode(9004);
                bathMapParameters.clear();
                applicationContext.setMsg(parameter.concat("字段不存在"));
                isSuccess = false;
                break;
            } else {
                fieldTypeHandler = fieldHandler.getFieldTypeHandler();
                for (String value : values) {
                    if (!fieldTypeHandler.validate(value)) {
                        applicationContext.setEncryptCode(9004);
                        bathMapParameters.clear();
                        applicationContext.setMsg(parameter.concat("验证不通过"));
                        isSuccess = false;
                        break;
                    }
                }
            }
        }
        //验证次要参数
        for (String parameter : minorParameters) {
            fieldHandler = fieldMap.get(parameter);
            values = bathMapParameters.get(parameter);
            if (values != null) {
                fieldTypeHandler = fieldHandler.getFieldTypeHandler();
                for (String value : values) {
                    if (!fieldTypeHandler.validate(value)) {
                        applicationContext.setEncryptCode(9006);
                        bathMapParameters.clear();
                        applicationContext.setMsg(parameter.concat("验证不通过"));
                        isSuccess = false;
                        break;
                    }
                }
            }
        }
        HttpServletRequest request = applicationContext.getHttpServletRequest();
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(1024 * 1024);
        ServletFileUpload upload = new ServletFileUpload(factory);
        InputStream in = null;
        boolean isValidateFileName = true;
        try {
            List<FileItem> list = (List<FileItem>) upload.parseRequest(request);
            List<FileEntity> fileList = new ArrayList<FileEntity>(list.size());
            FileEntity fileEntity;
            for (FileItem item : list) {
                fileEntity = new FileEntity();
                if (!item.isFormField()) {
                    String value = item.getName();
                    int start = value.lastIndexOf("\\");
                    String filename = value.substring(start + 1);
                    if (FileUtils.checkFileType(filename, allowFiles)) {
                        fileEntity.setFileName(filename);
                        in = item.getInputStream();
                        byte[] buf = new byte[1024];
                        in.read(buf);
                        fileEntity.setContents(buf);
                        fileList.add(fileEntity);
                    } else {
                        isValidateFileName = false;
                        break;
                    }

                }
                invocationContext.setFileList(fileList);
            }
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
        if (isSuccess && isValidateFileName) {
            //验证参数
            nextHandler.execute();
        } else {
            if (!isValidateFileName) {
                applicationContext.fail(8000);
            }
        }

    }
}
