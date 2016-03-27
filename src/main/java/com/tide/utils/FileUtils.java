package com.tide.utils;

import com.aliyun.oss.OSSClient;
import com.tide.wechat.token.TokenProxy;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

/**
 * 文件相关操作
 * Created by wengliemiao on 15/12/16.
 */
public class FileUtils {

    // 图片计数
    public static Integer imgCount = 1;

    // 存储图片文件夹
    public static String basePath = "upload/";

    // 阿里云 OSS 相关配置项
//    private static String accessKeyId = "MvkuyV8YQn1qCuUg";
//    private static String accessKeySecret = "MNxPY2UD8ZrF6BO1PptjRPTlrfqG51";
//    private static String bucketName = "tide";

    private static String accessKeyId = "Qod0JnDTKMQNLztp";
    private static String accessKeySecret = "XKEgAbCK0WJCfXwVZ8G3hDH600DmJe";

    private static String endpoint = "oss-cn-hangzhou.aliyuncs.com";
    private static String bucketName = "crowfunding";

    /**
     * 保存文件
     * @param file
     * @param session
     * @return
     */
    public static String saveFile(MultipartFile file, HttpSession session) {
        String serverPath = session.getServletContext().getRealPath("");

        // 生成新的文件名称
        String sNewName = getImgNameByType(file.getContentType(), imgCount ++);

        //  保存图片文件夹
        String saveDirectoryPath = serverPath + "/" + basePath;
        File saveDirectory = new File(saveDirectoryPath);
        if(!saveDirectory.exists()) {
            saveDirectory.mkdirs();
        }

        // 保存文件到服务器
//        saveImgFile(file, serverPath, "/" + basePath + sNewName);
        // 保存文件到 阿里云OSS
        Object obj = saveImgToOSS(file, basePath + sNewName);

        return basePath + sNewName;
    }

    /**
     * 保存文件到 阿里云OSS
     * @param file
     * @param fileName
     * @return
     */
    public static Object saveImgToOSS(MultipartFile file, String fileName) {
        try {
            return saveImgToOSS(file.getInputStream(), fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
//        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
//        try {
//            InputStream is = file.getInputStream();
//            return client.putObject(bucketName, fileName, is);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "";
    }

    /**
     * 保存文件到 阿里云OSS
     * @param is
     * @param fileName
     * @return
     */
    public static Object saveImgToOSS(InputStream is, String fileName) {
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        return client.putObject(bucketName, fileName, is);
    }

    /**
     * 保存图片到服务器
     *
     * @param imgFile
    //     * @param serverPath
     * @param savePath
     */
    public static void saveImgFile(MultipartFile imgFile, String serverPath, String savePath) {
        try {
            File mainFile = new File(serverPath + savePath);
            imgFile.transferTo(mainFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据图片类型, 系统时间, 本次操作上传图片计数 生成图片名称
     * @param imgType
     * @param imgCount
     * @return
     */
    public static String getImgNameByType(String imgType, Integer imgCount) {
        // 生成新的图片名称: 日期+从小到大顺序(从1开始)
        String suffix = DataUtils.getSuffixByType(imgType);
        String newImgName = new Date().getTime() + "-" + imgCount + suffix;
        return newImgName;
    }

    /**
     * 删除服务器文件
     * @param filePath
     * @return
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if(file.isFile() && file.exists()) {
            return file.delete();
        }
        return true;
    }

    /**
     * 删除 OSS 文件
     * @param filename
     * @return
     */
    public static boolean deleteOSSFile(String filename) {
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        client.deleteObject(bucketName, filename);
        return true;
    }

    /**
     * 从微信服务器下载图片
     * @param mediaId
     * @return
     */
    public static String downloadImgFromWechat(String mediaId) {
        String downFileApi = "http://file.api.weixin.qq.com/cgi-bin/media/get?";
        StringBuffer url = new StringBuffer(downFileApi);
        String token = TokenProxy.getAccessToken();
        url.append("access_token=" + token + "&");
        url.append("media_id=" + mediaId);

        System.out.println(url);

        try {
            URL u = new URL(url.toString());
            HttpURLConnection htCon = (HttpURLConnection) u.openConnection();

            int code = htCon.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                String fileName = getImgNameByType("image/jpeg", imgCount ++);
                String filePath = basePath + fileName;
                saveImgToOSS(htCon.getInputStream(), filePath);
                return filePath;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 返回文件绝对路径
     * @param session
     * @param filePath
     * @return
     */
    public static String getFileRealPath(HttpSession session, String filePath) {
        HttpServletRequest request = null ;
//        request.getServletPath()
        return session.getServletContext().getRealPath("")+"/"+filePath;
    }
}
