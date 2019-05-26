package fileserver.utils;

import fileserver.entity.FileEntity;
import fileserver.entity.ImageEntity;
import org.apache.commons.logging.impl.SimpleLog;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


import java.io.*;
import java.util.Properties;

public class FileProcessorUtils implements FileUtils{

    private FileProcessorUtils() {
    }
    private static SimpleLog log = new SimpleLog("log");
    // 图片文件的  base64 编码
    public static FileEntity imageToDataInBase64(File file){

        String fileName = file.getName();
        String filePath = getTheFilePathFromPropertiesFile("allImagesDirectory")+fileName;
        InputStream inputStream = null;
        byte[] data = new byte[1024 * 1024];
        try {
            inputStream = new FileInputStream(file);
            data = new byte[inputStream.available()];
            int len = 0;
            while (len !=-1){
                len = inputStream.read(data);
            }
        } catch (IOException e) {
            log.info(e);
        }finally {
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.info(e);
                }
            }
        }

        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return new ImageEntity(fileName,filePath,encoder.encode(data));
    }

    // 图片 base64 编码解码
    public static boolean dataToImageFromBase64(String imgStr, String filePath) {
        if (imgStr == null)
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            // 解密
            byte[] b = decoder.decodeBuffer(imgStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            out = new FileOutputStream(filePath);
            out.write(b);
            out.flush();
            return true;
        } catch (Exception e) {
            return false;
        }finally {
            if (out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.info(e);
                }
            }
        }
    }

    public static String getTheFilePathFromPropertiesFile(String fileDirectoryKey){
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(FileProcessorUtils.class.getResource("/").getPath()+"/properties/filesDirectory.properties"));

        } catch (IOException e) {
            log.info(e);
        }
        return properties.getProperty(fileDirectoryKey);
    }

    /**
     * 获取到 仓库 名称
     * @param depository
     * @return
     */
    public static String getAbsoluteFileName(String depository){
        String[] arr = depository.split("\\\\");
        int len = arr.length;
        StringBuilder name = new StringBuilder();
        for (int i = 2 ; i < len-1 ; i++){
            name.append(arr[i]+"-");
        }
        return name.toString();
    }
}
