package file_server.thread;

import file_server.office.AsposeOfficeToPdfUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PWXFileToPdfThread extends AbstractOfficeProcessThread implements Runnable {

    private String officeFileName;
    private String officePath;
    private File officeFile;
    private String toPdfDirectory;

    @Override
    public void run() {
        processOfficeFile();
    }


    public void processOfficeFile() {
        String pdfFileName = officeFileName.substring(0,officeFileName.lastIndexOf("."))+".pdf";

        String pdfPath = toPdfDirectory+"\\" +pdfFileName;
        System.out.println("Thread : " + Thread.currentThread().getName() + " 转换 ： " + officePath + " 到 : " + pdfPath);

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(PWXFileToPdfThread.class.getResource("/").getPath()+"/properties/filesDirectory.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String imageDirectory = properties.getProperty("allImagesDirectory");
        try {
            AsposeOfficeToPdfUtils.OfficesToPdf(officePath,pdfPath,imageDirectory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public PWXFileToPdfThread(String officeFileName, String officePath, File officeFile, String toPdfDirectory) {
        //super(officeFileName,officePath,toPdfDirectory,officeFile);
        this.officeFileName = officeFileName;
        this.officeFile = officeFile;
        this.officePath = officePath;
        this.toPdfDirectory = toPdfDirectory;
    }



    public String getToDirectory() {
        return toPdfDirectory;
    }

    public void setToDirectory(String toPdfDirectory) {
        this.toPdfDirectory = toPdfDirectory;
    }

    public String getOfficeFileName() {
        return officeFileName;
    }

    public void setOfficeFileName(String officeFileName) {
        this.officeFileName = officeFileName;
    }

    public String getOfficePath() {
        return officePath;
    }

    public void setOfficePath(String officePath) {
        this.officePath = officePath;
    }

    public File getOfficeFile() {
        return officeFile;
    }

    public void setOfficeFile(File officeFile) {
        this.officeFile = officeFile;
    }

}
