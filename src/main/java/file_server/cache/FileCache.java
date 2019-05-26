package file_server.cache;

import file_server.entity.FileEntity;

import java.io.File;

public interface FileCache {

    /**
     * 是否存在于 hashArray 里面，是否存在在缓存
     * @param fileName
     * @return
     */
    public boolean hashArrayExistCache(String fileName);

    /**
     * 是否存在 这一个 记录
     * @param fileName
     * @return
     */
    boolean recordsExistMemory(String fileName);

    public FileEntity getSpecialFile(String fileName);

    public FileEntity exchangeEntityFromCache(String preFileName, File newFile);

}
