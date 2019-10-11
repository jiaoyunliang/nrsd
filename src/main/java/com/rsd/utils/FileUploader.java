package com.rsd.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传工具类
 *
 * @author yc
 * @version 1.0
 */
public class FileUploader {

    public static void main(String[] args) {
        String str = "/var/MedicineMebmer/upload/image/14435111478113642.jpg";
        if (isAbsolutePath(str)) {
            System.out.println("sdfsd");
        }
        System.out.println(str.substring(str.indexOf("/upload"), str.length()));

    }

    public static FileUploadState validAndUpload(MultipartFile file,
                                                 HttpServletRequest request, String filetype) {
        FileUploadState state = null;
        try {
            ConfigurationManager cm = ConfigurationManager.getManager("upload");
            long maxSize = cm.getLong(filetype + "_maxSize");
            String allowedexts = cm.getProperty(filetype + "_ext");
            String dir = cm.getProperty(filetype + "_dir");
            String globalStaticFileLocation = cm
                    .getProperty("global_static_file_location");
            String newLocation = globalStaticFileLocation + dir;

            state = FileUploader.valid(file, allowedexts, maxSize);

            if (state.isSuccess()) {
                state = FileUploader.saveFileFromInputStream(
                        file.getInputStream(), newLocation,
                        state.getNewFileName(), state.getOldFileName(), dir);
            }

        } catch (IOException e) {
            state = new FileUploadState();
            state.setSuccess(false);
            state.setMessage("IO错误");
        } catch (Exception e) {
            e.printStackTrace();
            state = new FileUploadState();
            state.setSuccess(false);
            state.setMessage("未找到上传配置文件：upload.properties");
        }

        return state;

    }

    /**
     * @author hdx
     * @Description 文件上传以及验证方法
     * @Date 2019/5/8 11:07
     * @Param [file]
     * @Return FileUploadState
     */
    public static FileUploadState validAndUpload(MultipartFile file) {
        FileUploadState state = null;
        String filetype="";
        try {
            ConfigurationManager cm = ConfigurationManager.getManager("upload");
            String fileName = file.getName();
            if (fileName != null) {
                boolean valid=checkPicture(file);
                if (valid){
                    filetype="image";
                }else{
                    filetype="attach";
                }
            }
            long maxSize = cm.getLong(filetype + "_maxSize");
            String allowedexts = cm.getProperty(filetype + "_ext");
            String dir = cm.getProperty(filetype + "_dir");
            String globalStaticFileLocation = cm
                    .getProperty("global_static_file_location");
            String newLocation = globalStaticFileLocation + dir;

            state = FileUploader.valid(file, allowedexts, maxSize);

            if (state.isSuccess()) {
                state = FileUploader.saveFileFromInputStream(
                        file.getInputStream(), newLocation,
                        state.getNewFileName(), state.getOldFileName(), dir);
            }

        } catch (IOException e) {
            state = new FileUploadState();
            state.setSuccess(false);
            state.setMessage("IO错误");
        } catch (Exception e) {
            e.printStackTrace();
            state = new FileUploadState();
            state.setSuccess(false);
            state.setMessage("未找到上传配置文件：upload.properties");
        }

        return state;

    }

    /**
     * @author hdx
     * @Description 判断是否是图片
     * @Date 2019/5/8 11:19
     * @Param
     * @Return
     */
    public static boolean checkPicture(MultipartFile file) {
        boolean valid;
        try {
            Image image = ImageIO.read(file.getInputStream());
            if (image == null) {
                valid = false;
            } else {
                valid = true;
            }
        } catch (IOException ex) {
            valid = false;
        }
        return valid;
    }

    /**
     * 生成文件保存路径,如果是相对路径刚加上当前应用的路径.
     *
     * @param request
     * @param path
     * @return
     */

    public static String makeFilePath(HttpServletRequest request, String path) {

        if (path == null) {
            return null;
        }

        if (isAbsolutePath(path)) {
            return path;
        }

        String realPath = request.getSession().getServletContext()
                .getRealPath("/");

        return realPath + path;

    }

    /**
     * 传入路径，返回是否是绝对路径，是绝对路径返回true，反之
     *
     * @param path
     * @return
     */
    public static boolean isAbsolutePath(String path) {
        if (path.startsWith("/") || path.indexOf(":") > 0) {
            return true;
        }
        return false;
    }
    /**
     * @author hdx
     * @Description 文件上传并简单验证
     * @Date 2019/5/8 11:29
     * @Param [file, allowedexts, maxSize]
     * @Return FileUploadState
     */
    public static FileUploadState valid(MultipartFile file, String allowedexts,
                                        long maxSize) {

        FileUploadState state = new FileUploadState();

        String[] allowedext = allowedexts.split(",");
        // 文件验证
        // 上传后的文件名称
        String fileName = "" + System.currentTimeMillis()
                + StringUtils.generateIdentifyingCode(4);

        // 判断上传文件是否为空
        if (file.isEmpty()) {
            state.setSuccess(false);
            state.setMessage("上传失败:上传文件为空！");
            return state;
        }

        // 判断是否允许的文件后缀名
        String oldName = file.getOriginalFilename();
        state.setOldFileName(oldName);
        boolean flag = false;
        for (String str : allowedext) {
            oldName = oldName.toLowerCase();
            if (oldName.endsWith(str)) {
                flag = true;
                fileName = fileName + "." + str;
            }
        }
        if (!flag) {
            state.setSuccess(false);
            state.setMessage("上传失败:不是指定的上传格式！");
            return state;
        }

        if (file.getSize() > maxSize) {
            state.setSuccess(false);
            state.setMessage("上传失败:文件大小超过规定上传大小！");
            return state;
        }

        state.setSuccess(true);
        state.setNewFileName(fileName);
        return state;
    }

    /**
     * @author hdx
     * @Description 保存文件
     * @Date 2019/5/8 11:30
     * @Param [stream, path, newFileName, oldFileName, dir]
     * @Return FileUploadState
     */
    public static FileUploadState saveFileFromInputStream(InputStream stream,
                                                          String path, String newFileName, String oldFileName, String dir) {
        FileUploadState state = new FileUploadState();
        try {
            File f = new File(
                    org.springframework.util.StringUtils.cleanPath(path
                            + newFileName));
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            f.createNewFile();

            FileOutputStream fs = new FileOutputStream(path + File.separator
                    + newFileName);
            byte[] buffer = new byte[1024 * 1024];
            int byteread = 0;
            while ((byteread = stream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
                fs.flush();
            }
            fs.close();
            stream.close();
            state.setPath(dir);
            state.setNewFileName(newFileName);
            state.setOldFileName(oldFileName);
            state.setSuccess(true);
        } catch (IOException e) {
            state.setMessage("");
            state.setSuccess(false);

        }
        return state;
    }

    /**
     * 获取图片操作尺寸
     *
     * @param imgSize
     * @return
     */
    public static Integer[] findSize(String imgSize) {
        Integer[] size = new Integer[2];
        String[] pam = imgSize.split("x");

        size[0] = Integer.valueOf(pam[0]);
        size[1] = Integer.valueOf(pam[1]);

        return size;
    }

    /**
     * 获取图片名称方法
     *
     * @param imgName
     * @return
     */
    public static String[] findImgName(String imgName) {

        String[] name = imgName.split(",");

        return name;
    }

    /**
     * 根据参数获取图片长宽和名称
     *
     * @param imgInfo
     * @return
     */
    public static Map<String, String> findAutomaticPam(String imgInfo) {
        Map<String, String> map = new HashMap<String, String>();

        String[] img = imgInfo.split("-");
        if (img.length > 1) {
            map.put("imgName", img[1]);
        } else {
            map.put("imgName", "");
        }

        String[] pam = img[0].split("x");
        if (pam.length > 1) {
            map.put("length", pam[0]);
            map.put("width", pam[1]);
        } else {
            map.put("length", "");
            map.put("width", "");
        }

        return map;
    }

}
