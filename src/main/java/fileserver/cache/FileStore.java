package fileserver.cache;

import java.io.File;

public interface FileStore {

    // 返回图片存储的路径
    String storeImageFileToDisk(String path, File file);

    // 返回 office 文件转 image 后的路径 + pages
    String storeOfficeFileToDisk(String toDirectory, File file);

    String storePdfFileToDisk(String toDirectory,File file);
}
