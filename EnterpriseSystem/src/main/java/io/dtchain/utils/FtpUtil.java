package io.dtchain.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;

@Component
public class FtpUtil {
	 
    //ftp服务器ip地址
    private static final String FTP_ADDRESS = "192.168.119.1";
    //端口号
    private static final int FTP_PORT = 21;
    //用户名
    private static final String FTP_USERNAME = "Engine";
    //密码
    private static final String FTP_PASSWORD = "990165";
    //图片路径
    public final String FTP_BASEPATH = "/file/";
 
    public boolean uploadFile(String originFileName, InputStream input) {
        boolean success = false;
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("utf-8");
        try {
            int reply;
            ftp.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
            ftp.login("anonymous", null);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.makeDirectory(FTP_BASEPATH);
            ftp.changeWorkingDirectory(FTP_BASEPATH);
            ftp.storeFile(originFileName, input);
            input.close();
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }
}
