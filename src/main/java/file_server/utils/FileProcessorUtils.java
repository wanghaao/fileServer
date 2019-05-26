package file_server.utils;

import com.sun.jmx.snmp.Timestamp;
import file_server.entity.FileEntity;
import file_server.entity.ImageEntity;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.Properties;

public class FileProcessorUtils implements FileUtils{

    // 图片文件的  base64 编码
    public static FileEntity imageToDataInBase64(File file){

        String fileName = file.getName();
        String filePath = getTheFilePathFromPropertiesFile("allImagesDirectory")+fileName;
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(file);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
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
        try {
            // 解密
            byte[] b = decoder.decodeBuffer(imgStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(filePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 编码生成唯一一个文件名 -- 32个字节的字符串，不含后缀
    public static String theUniqueFileNameEncoder(Timestamp timestamp, File file){

        return timestamp.toString()+getTheFilePathFromPropertiesFile("allImagesDirectory")+file.getName().split(".")[0];
    }

    public static String getTheFilePathFromPropertiesFile(String fileDirectoryKey){
        Properties properties = new Properties();
        try {
//            properties.load(new FileInputStream("/classes/properties/filesDirectory.properties"));

            properties.load(new FileInputStream(FileProcessorUtils.class.getResource("/").getPath()+"/properties/filesDirectory.properties"));

        } catch (IOException e) {
            e.printStackTrace();
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
        String name = "";
        for (int i = 2 ; i < len-1 ; i++){
            name += arr[i]+"-";
        }
        return name;
    }
}
