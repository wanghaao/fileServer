package file_server.cache;

import file_server.thread.OfficeThreadPoolManager;
import file_server.thread.PWXFileToPdfThread;
import file_server.thread.PdfFileToImagesThread;
import file_server.utils.FileUtils;
import org.apache.commons.logging.impl.SimpleLog;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class OfficeFileFormatConversion implements FileFormatConversion, FileUtils {

    private static OfficeThreadPoolManager officeThreadPoolManager = new OfficeThreadPoolManager();
    private static int pageNumber = 0;
    private SimpleLog log = new SimpleLog("log");

    public OfficeFileFormatConversion() {
        // 预加载
    }
    /**
     * 预加载 office 为 pdf ，pdf - png
     */
    private void initialLoadOffice() {
        preProcessPdfToImages();
        preProcessOfficeFile();
    }

    private List<String> getAllOfficeFilesPath(String directory) {
        List<String> pptList = new ArrayList<String>();

        recursionSearch(new File(directory), pptList);

        return pptList;
    }
    /**
     * the all filePaths in the special directory for special suffixes
     *
     * @param file
     * @param list
     */
    private void recursionSearch(File file, List<String> list) {
        if (file.isFile()) {
            list.add(file.getPath());
        } else {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File file1 : files) {
                    recursionSearch(file1, list);
                }
            }
        }
    }

    /**
     * 直接将上传的office文件 -- pdf -- images
     *
     * @param file
     * @param toDirectory
     * @return 返回pdf文件转为 images 后的 文件夹 + 页数
     */
    public String processOfficeFile(File file, String toDirectory) {
        return officeToPdfByThreadPool(file, toDirectory);
    }

    public String processPdfFile(File file,String toDirectory){
        return pdfToImagesByThreadPool(file,toDirectory);
    }
    /**
     * 扫描本地的 office 文件 -- pdf
     *
     */
    private void preProcessOfficeFile() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(OfficeFileFormatConversion.class.getResource("/").getPath()+"/properties/filesDirectory.properties"));

        } catch (IOException e) {
            log.info(e);
        }
        String officeFromDirectory = properties.getProperty("allOfficeFilesDirectory");
        List<String> filePaths = getAllOfficeFilesPath(officeFromDirectory);

        String pdfDirectory = properties.getProperty("allPdfFilesDirectory");

        for (String path : filePaths) {
            File file = new File(path);
            officeToPdfByThreadPool(file, pdfDirectory);
        }
    }

    /*
     * 通过文件名的不同分别处理不一样的office文件
     *@parameter
     *@return 返回 office 转为 pdf 文件的路径
     */
    private String officeToPdfByThreadPool(File file, String toDirectory) {
        String fileName = file.getName();
        // 通过线程池的方式 -- office 的线程池
        PWXFileToPdfThread officeThread = new PWXFileToPdfThread(fileName, file.getPath(), file, toDirectory);
        officeThreadPoolManager.commitThread(officeThread);
        return toDirectory + "\\" + fileName.substring(0, fileName.lastIndexOf('.')) + ".pdf";
    }


    /**
     * 开启一个线程去操作循环遍历 pdf 并添加到 pool 再转换
     * 预处理 指定目录下的 pdf -- images
     *
     */
    private void preProcessPdfToImages() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(OfficeFileFormatConversion.class.getResource("/").getPath()+"/properties/filesDirectory.properties"));
        } catch (IOException e) {
            log.info(e);
        }
        String pdfFromDirectory = properties.getProperty("allPdfFilesDirectory");
        String imageToDirectory = properties.getProperty("allImagesDirectory");
        List<String> filePaths = getAllOfficeFilesPath(pdfFromDirectory);

        for (String path: filePaths
             ) {
            pdfToImagesByThreadPool(new File(path),imageToDirectory);
        }
    }

    /**
     * pdf 转换为 image，不需要等待线程完成，只需要回调获取到 dpf 的页数。
     *
     * @param pdfFile
     * @param toImageDirectory
     * @return directory + page的页数
     */
    private String pdfToImagesByThreadPool(File pdfFile, String toImageDirectory) {

        String fileName = pdfFile.getName();
        String filePath = pdfFile.getPath();

        PdfFileToImagesThread imagesThread = new PdfFileToImagesThread(fileName, filePath, toImageDirectory, pdfFile);
        // 通过线程池的方式 -- office 的线程池
        officeThreadPoolManager.commitThread(imagesThread);

        return toImageDirectory;
    }


    public int getPageNumber() {
        return pageNumber;
    }

}
