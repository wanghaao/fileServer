package file_server.cache;

import file_server.utils.FileProcessorUtils;

import java.io.*;

public class FileStoreImpl implements FileStore {

    private OfficeFileFormatConversion fileFormatConversion = new OfficeFileFormatConversion();
//    private OfficeFileFormatConversion fileFormatConversion;
//    @Resource(name = "officeFileFormatConversion")
//    public void setFileFormatConversion(OfficeFileFormatConversion fileFormatConversion) {
//        this.fileFormatConversion = fileFormatConversion;
//    }
    private static FileCacheImpl fileCache = new FileCacheImpl();

    private String getFileStoreDirectory(String fileFormatTag){
        String toDirectory = FileProcessorUtils.getTheFilePathFromPropertiesFile(fileFormatTag);
        return toDirectory;
    }

    /**
     * 仓库文件  针对 图片的一个文件夹，做 缓存
     * @param file
     * @return
     */
    public String storeFileToDisk(File file){
        String suffix = file.getName().substring(file.getName().lastIndexOf(".")+1);
        if (suffix.equals("jpg") || suffix.equals("png") || suffix.equals("bmp")){
            String toDirectory = getFileStoreDirectory("allImagesDirectory");
            storeImageFileToDisk(toDirectory,file);
            return toDirectory+file.getName();
        }else if(suffix.equals("pdf")){
            String toDirectory = getFileStoreDirectory("allImagesDirectory");
            String path = storePdfFileToDisk(toDirectory,file);
            return path;
        }else {
            // 也存到一个 固定的 文件夹下面
            String toDirectory = file.getPath().substring(0,file.getPath().lastIndexOf("\\"));
            String filePathList = storeOfficeFileToDisk(toDirectory,file);
            return filePathList;
        }
    }

    // 接收的文件数据 到 disk存储,文件名是 绝对路径
    @Override
    public String storeImageFileToDisk(String toDirectory,File file) {
        String absoluteFileName = file.getPath().substring(12).replaceAll("\\\\","-");
        // 绝对路径的 后面 的所有作为 文件名
        System.out.println("转存到固定文件夹，并更改文件名为:" + absoluteFileName);
        try {
            FileOutputStream fos = new FileOutputStream(new File(toDirectory +"\\"+ absoluteFileName));
            FileInputStream fis = new FileInputStream(file);

            byte[] bytes = new byte[1024 * 1024];
            int len=0;
            while((len=fis.read(bytes))!=-1){
                fos.write(bytes, 0, len);
            }
            fis.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file1 = new File(toDirectory +"\\"+ absoluteFileName);
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

    /*
     * @return 写入disk后返回到 缓存中的链接记录
     */
    public String insertIntoCacheRecord(){

        return "";
    }

    public static void addRecord(File file){
        fileCache.recallAddRecord(file);
    }

}
