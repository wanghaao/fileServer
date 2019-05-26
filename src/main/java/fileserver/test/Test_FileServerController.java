package fileserver.test;

import fileserver.cache.FileCacheImpl;
import fileserver.cache.OfficeFileFormatConversion;
import fileserver.controller.FileReceiverImpl;
import org.apache.commons.logging.impl.SimpleLog;
import org.junit.Test;

import java.io.File;

public class Test_FileServerController {
    private SimpleLog log = new SimpleLog("log");

    @Test
    public void test_UploadImageAndOffices(){
        File file = new File("E:\\Documents\\Desktop\\SCUTEC.jpg");
        FileCacheImpl cache = new FileCacheImpl();
        System.out.println(cache.storeNewFile(file));
    }

    @Test
    public void test_getImageDataInBase64(){
        FileCacheImpl cache = new FileCacheImpl();
        System.out.println(cache.getSpecialFile("SCUTEC.jpg").getFileData());
    }

    @Test
    public void test_get_PPT_File_ImageFormatInBase64(){
        log.info("wanghao");
    }

    @Test
    public void test_OfficeToPdf(){
        File file = new File("E:\\Documents\\Desktop\\officeTest");
        String toDirectory = "E:\\Documents\\Desktop\\officeToDirectory";
        OfficeFileFormatConversion formatConversion = new OfficeFileFormatConversion();
        // 测试从本地文件夹加载
        //formatConversion.preProcessOfficeFile();
        // 测试新上传的文件
        //formatConversion.processOfficeFile(file,toDirectory);
    }

    @Test
    public void test_get_Word_File_FormatIn__(){
        String officePath = "E:\\Documents\\Desktop\\officeTest\\家庭背景证明.docx";
        String officePath2 = "E:\\Documents\\Desktop\\officeTest\\Chapter01.ppt";

        String pdfPath = "E:\\Documents\\Desktop\\officeToDirectory\\123.pdf";
        String imageDir = "E:\\Documents\\Desktop\\imageDirectory\\";
        File file = new File(officePath2);
        System.out.println(file.getPath());

//        try {
//            AsposeOfficeToPdfUtils.OfficesToPdf(officePath2,pdfPath,imageDir);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public static void main(String[] args){

        File file1 = new File("E:\\Documents\\Desktop\\officeDirectory\\Chapter01.ppt");

        File file2 = new File("E:\\Documents\\Desktop\\imageDirectory\\1\\1_1.png");

        FileReceiverImpl receiver = new FileReceiverImpl();

        System.out.println(receiver.createDepository("demo1"));

        receiver.createDirectory("demo1","11");
        receiver.createDirectory("demo1/11","22");
        System.out.println(receiver.searchDirectory("demo1/11"));
        //System.out.println(receiver.getImageDataInBase64("Chapter01_1.png","image"));
        System.out.println(receiver.getImageDataInBase64("week3.pdf","office"));

    }
}