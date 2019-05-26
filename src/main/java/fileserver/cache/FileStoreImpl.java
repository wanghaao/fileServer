package fileserver.cache;

import fileserver.utils.FileProcessorUtils;
import org.apache.commons.logging.impl.SimpleLog;

import java.io.*;

public class FileStoreImpl implements FileStore {

    private OfficeFileFormatConversion fileFormatConversion = new OfficeFileFormatConversion();

    private static FileCacheImpl fileCache = new FileCacheImpl();
    private SimpleLog log = new SimpleLog("log");

    private String getFileStoreDirectory(String fileFormatTag){
        return FileProcessorUtils.getTheFilePathFromPropertiesFile(fileFormatTag);
    }

    /**
     * 仓库文件  针对 图片的一个文件夹，做 缓存
     * @param file
     * @return
     */
    public String storeFileToDisk(File file){
        String suffix = file.getName().substring(file.getName().lastIndexOf('.')+1);
        if (suffix.equals("jpg") || suffix.equals("png") || suffix.equals("bmp")){
            String toDirectory = getFileStoreDirectory("allImagesDirectory");
            storeImageFileToDisk(toDirectory,file);
            return toDirectory+file.getName();
        }else if(suffix.equals("pdf")){
            String toDirectory = getFileStoreDirectory("allImagesDirectory");
            return storePdfFileToDisk(toDirectory,file);
        }else {
            // 也存到一个 固定的 文件夹下面
            String toDirectory = file.getPath().substring(0,file.getPath().lastIndexOf('\\'));
            return storeOfficeFileToDisk(toDirectory,file);
        }
    }

    // 接收的文件数据 到 disk存储,文件名是 绝对路径
    @Override
    public String storeImageFileToDisk(String toDirectory,File file) {
        String absoluteFileName = file.getPath().substring(12).replaceAll("\\\\","-");
        // 绝对路径的 后面 的所有作为 文件名
        log.info("转存到固定文件夹，并更改文件名为:" + absoluteFileName);
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            fos = new FileOutputStream(new File(toDirectory +File.separator+ absoluteFileName));
            fis = new FileInputStream(file);

            byte[] bytes = new byte[1024 * 1024];
            int len=0;
            while((len=fis.read(bytes))!=-1){
                fos.write(bytes, 0, len);
            }
        } catch (IOException e) {
            log.info(e);
        }finally {
            if (fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    log.info(e);
                }
            }
            if (fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    log.info(e);
                }
            }
        }
        File file1 = new File(toDirectory +File.separator+ absoluteFileName);
        addRecord(file1);
        return toDirectory;
    }

    @Override
    public String storeOfficeFileToDisk(String toDirectory, File file) {
        return fileFormatConversion.processOfficeFile(file,toDirectory);
    }

    @Override
    public String storePdfFileToDisk(String toDirectory, File file) {
        return fileFormatConversion.processPdfFile(file,toDirectory);
    }

    public static void addRecord(File file){
        fileCache.recallAddRecord(file);
    }

}
