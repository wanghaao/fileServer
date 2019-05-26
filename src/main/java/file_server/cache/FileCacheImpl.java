package file_server.cache;

import file_server.entity.FileEntity;
import file_server.exception.CacheRequestException;
import file_server.utils.FileEntityHashArray;
import file_server.utils.FileProcessorUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;

public class FileCacheImpl implements FileCache {
    // 自己的数据结构
    private static FileEntityHashArray<String, FileEntity> fileEntityHashArray = new FileEntityHashArray<String, FileEntity>();
    // 所有的文件记录
    private static HashMap<String,FileListNode> allFilesRecords = new HashMap<String, FileListNode>();

    private FileStoreImpl fileStoreImpl = new FileStoreImpl();
//    private FileStoreImpl fileStoreImpl;
//    @Resource(name = "fileStoreImpl")
//    public void setFileStoreImpl(FileStoreImpl fileStoreImpl) {
//        this.fileStoreImpl = fileStoreImpl;
//    }

    private static String allImagesDirectory  = "";
    public FileCacheImpl() {
    }
    // 仅一次
    static {
        initialCache();
    }
    // 实例化 类的时候，先去加载指定文件目录下的所有图片
    static final void initialCache(){// 通过配置文件的方式，获取指定的目录
        String path = FileProcessorUtils.getTheFilePathFromPropertiesFile("allImagesDirectory");

        allImagesDirectory = path;

        File file = new File(path);
        findAllFiles(file);
    }

    static void findAllFiles(File file){
        if(file.isFile()){
            // 文件记录存放
            allFilesRecords.put(file.getName(),new FileListNode(file.getPath(),1));

            // 预读取一部分文件
            FileEntity fileEntity = FileProcessorUtils.imageToDataInBase64(file);
            /*
            **********************************此处可以优化，降低置换次数，并且数组满*****************
             */
            fileEntityHashArray.put(fileEntity.getFileName(),fileEntity);
        }else {
            if(file.exists() && file.isDirectory()){
                File[] files = file.listFiles();
                for(File file1 : files){
                    findAllFiles(file1);
                }
            }
        }
    }
    @Override
    public boolean hashArrayExistCache(String fileName) {
        return fileEntityHashArray.whetherExistKey(fileName);
    }

    @Override
    public boolean recordsExistMemory(String fileName) {
        return allFilesRecords.get(fileName)==null ? false : true;
    }

    @Override
    public FileEntity getSpecialFile(String fileName) {
        if (recordsExistMemory(fileName)){// 内存存在 ?
            if(hashArrayExistCache(fileName)){// 缓存存在 ?
                return fileEntityHashArray.get(fileName);
            }else{// 从磁盘读取,置换内存中的
                return exchangeEntityFromCache(fileName, reloadFromMemory(fileName));
            }
        }else {
            //return null;
            throw new CacheRequestException("The requested resource is not in the memory!");
        }
    }

    @Override
    public FileEntity exchangeEntityFromCache(String preFileName, File newFile) {
       return fileEntityHashArray.put(preFileName,FileProcessorUtils.imageToDataInBase64(newFile));
    }

    private static File reloadFromMemory(String preFileName){
        return new File(allFilesRecords.get(preFileName).getFilePath());
    }

    public void removeFromList(String fileName){
        allFilesRecords.remove(fileName);
    }

    public void addToList(String fileName){
        allFilesRecords.put(fileName,new FileListNode(allImagesDirectory+fileName,1));
    }

    /**
     * 存 图片文件的 ，存储后需要添加到缓存
     * @param imageFile
     * @return
     */
    public String storeNewFile(File imageFile){
        String imagePath = fileStoreImpl.storeFileToDisk(imageFile);
        addToList(imagePath);
        int capacity = fileEntityHashArray.getElementNumber();
        return imagePath;
    }
    /*
    存office的，图片路径在存储后不直接添加到 缓存链表
     */
    public String storeNewFile(File officeFile,String path){
        String officePathList = fileStoreImpl.storeFileToDisk(officeFile);
        return officePathList;
    }

    /**
     * 存 一般文件
     * @param commonFile
     * @param nos
     * @return
     */
    public String storeNewFile(MultipartFile commonFile, String path, String nos){
        File dest = new File(path);
        try {
            commonFile.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 先存 在仓库，再转存到一个固定文件夹做 缓存 操作
        String paths = fileStoreImpl.storeFileToDisk(dest);
        return paths;
    }

    public void recallAddRecord(File file){
        allFilesRecords.put(file.getName(),new FileListNode(file.getPath(),1));
    }

}
