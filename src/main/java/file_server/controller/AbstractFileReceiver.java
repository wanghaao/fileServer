package file_server.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

public abstract class AbstractFileReceiver {

    // 文件
    public abstract String uploadImageAndOffices(@Param("file") MultipartFile file, @Param("path")String path);
}
