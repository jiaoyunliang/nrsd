package com.rsd.enterprise;

import com.rsd.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

/**
 * @author admin
 * @ClassName hdx
 * @Description 上传文件控制器
 * @Date 2019/5/8 10:32
 * @Version 1.0
 **/
@RestController
@RequestMapping("/enterprise")
public class EnterpriseUploadFileApi {
    private static final Logger logger = LoggerFactory.getLogger(EnterpriseUploadFileApi.class);
    @Autowired
    private MessageManager messageManager;

    @RequestMapping("/file/upload")
    @ResponseBody
    public HashMap<String, Object> upload(MultipartFile file) {
        JsonOut<FileUploadState> jsonOut = new JsonOut();
        HashMap<String, Object> data = new HashMap<String, Object>();
        FileUploadState state = null;
        try {
            state = FileUploader.validAndUpload(file);
            if (state != null && state.isSuccess()) {
                data.put("url", state.getFullName());
                data.put("oldFile", state.getOldFileName());
                data.put("newFile", state.getNewFileName());
            } else {
                data.put("state", "Fail");
                data.put("msg", "文件写入失败");
            }
            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).addDataName("data").addData(data).build();
        } catch (Exception ex) {
            logger.error("文件写入失败" + ex.getMessage());
            ex.printStackTrace();
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION).addMessage(messageManager.getMessage("ERROR.0006")).build();
        }
    }
}
