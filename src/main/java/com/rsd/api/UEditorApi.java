package com.rsd.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsd.utils.FileUploadState;
import com.rsd.utils.FileUploader;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tony
 * @data 2019-04-12
 * @modifyUser
 * @modifyDate
 */
@CrossOrigin
@Api(value = "UEditor", description = "UEditor")
@PropertySource(value = {"classpath:upload.properties"},encoding="utf-8")
@RestController
@RequestMapping("/UEditorApi")
public class UEditorApi {

    private static final Logger logger = LoggerFactory.getLogger(UEditorApi.class);

    @Value("${image_maxSize}")
    private String imageMaxSize;

    @Value("${image_ext}")
    private String imageManagerAllowFiles;

    @Value("${ueditorFileUrlPerfix}")
    private String imageUrlPrefix;

    @Value("${attach_maxSize}")
    private String fileMaxSize;

    @Value("${attach_ext}")
    private String fileAllowFiles;

    @Value("${ueditorFileUrlPerfix}")
    private String fileUrlPrefix;

    @ApiOperation(value = "config", notes = "config")
    @GetMapping(value="Service")
    public Map<String,Object> ueditorConfig(HttpServletRequest request){
        Map<String,Object> data = new HashMap<String,Object>();

        String actionType = request.getParameter( "action" );
        if("config".equals(actionType)){
            //----图片
            data.put("imageMaxSize",imageMaxSize);
            data.put("imageManagerAllowFiles",imageManagerAllowFiles.split(","));
            data.put("imageActionName", "uploadimage");
            data.put("imageFieldName", "upfile");
            data.put("imageCompressEnable", "true");
            data.put("imageCompressBorder", "1600");
            data.put("imageInsertAlign", "none");
            data.put("imageUrlPrefix", imageUrlPrefix);
            //----附件
            data.put("fileMaxSize", fileMaxSize);
            data.put("fileAllowFiles", fileAllowFiles.split(","));
            data.put("fileActionName","uploadfile");
            data.put("fileFieldName", "upfile");
            data.put("fileUrlPrefix", fileUrlPrefix);
        }

        return data;
    }

    @ApiOperation(value = "upload", notes = "upload")
    @PostMapping(value = "Service")
    public void ueditorUpload(HttpServletRequest request, MultipartFile upfile, HttpServletResponse response){
        Map<String,Object> data = new HashMap<String,Object>();
        String actionType = request.getParameter( "action" );
        FileUploadState state=null;
        if("uploadimage".equals(actionType)){
            state = FileUploader.validAndUpload(upfile, request, "image");
        }else if("uploadfile".equals(actionType)){
            state = FileUploader.validAndUpload(upfile, request, "attach");
        }

        if(state!=null && state.isSuccess()){
            data.put("state", "SUCCESS");
            data.put("url", state.getFullName());
            data.put("title", upfile.getName());
            data.put("original", upfile.getOriginalFilename());

        }else{
            logger.error("文件上传失败:("+actionType+")"+upfile.getOriginalFilename());
        }
        ObjectMapper mapper = new ObjectMapper();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
//        response.setHeader("content-type", "text/html");
        try{
            mapper.writeValue(response.getWriter(),data);
            response.getWriter().close();
        }catch(Exception ex){

        }
    }
}
