package com.roe.rmallservice.utils;


import com.roe.rmallservice.entity.Images;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.SocketException;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: HP
 * @Date: 2021/5/20 10:30
 * @Description: FTP工具类用于文件或图片上传
 **/

@Slf4j
public class FtpUtil {
    //ftp服务器ip地址
    private static final String FTP_HOST = "129.204.167.80";
    //端口号
    private static final int FTP_PORT = 21;
    //用户名
    private static final String FTP_USERNAME = "ftpuser";
    //密码
    private static final String FTP_PASSWORD = "roewudu12luoli";
    //图片路径
    private static final String FTP_BASEPATH = "images";

    private static String REGEX_CHINESE = "[\u4e00-\u9fa5]";// 中文正则

    public static FTPClient getFTPClient() {
        FTPClient ftpClient = null;
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(FTP_HOST, FTP_PORT);// 连接FTP服务器
            ftpClient.login(FTP_USERNAME, FTP_PASSWORD);// 登陆FTP服务器
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                log.info("未连接到FTP,用户名或密码错误!");
                ftpClient.disconnect();
            } else {
                log.info("FTP连接成功!");
            }
        } catch (SocketException e) {
            e.printStackTrace();
            log.info("FTP的IP地址可能错误，请正确配置。");
        } catch (IOException e) {
            e.printStackTrace();
            log.info("FTP的端口错误,请正确配置。");
        }
        return ftpClient;
    }

    /**
     * 上传文件到FTP上
     * @param originalFilename 图片名称
     * @param inputStream 图片输入流
     */
    public static String upload(String originalFilename, InputStream inputStream) {
        FTPClient ftpClient = null;
        String url = "";
        log.info("开始上传文件到FTP...");
        try {


            ftpClient = FtpUtil.getFTPClient();
            // 设置PassiveMode传输
            ftpClient.enterLocalPassiveMode();

            // 设置以二进制流的方式传输
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

            ftpClient.changeWorkingDirectory(FTP_BASEPATH);
            //正则去掉所有中文字符
            Pattern pat = Pattern.compile(REGEX_CHINESE);
            Matcher mat = pat.matcher(originalFilename);
            String filename = UUID.randomUUID() +mat.replaceAll("");
            log.info("上传生成的文件名{}",filename);
            ftpClient.storeFile(filename,inputStream);
            url = Constants.BASE_URL+filename;
            log.info("上传成功");
        } catch (Exception e) {
            e.printStackTrace();

        }finally{
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return url;
        }
    }

    /**
     * Ftp删除图片
     * @param images
     * @return
     */
    public static boolean remove(List<Images> images){
        FTPClient ftpClient = null;
        String url = "";
        log.info("开始删除文件FTP...");
        try {
            ftpClient = FtpUtil.getFTPClient();
            // 设置PassiveMode传输
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(FTP_BASEPATH);

            for(Images image: images){
                String path = image.getImageHttp().split("images/")[1];
                log.info("删除文件path:{}",path);
                boolean b = ftpClient.deleteFile(path);
                log.info("删除文件：{}",b);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }finally{
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }
    }

}
