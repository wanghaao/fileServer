package fileserver.thread;

import fileserver.office.AsposeOfficeToPdfUtils;
import org.apache.commons.logging.impl.SimpleLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PWXFileToPdfThread extends AbstractOfficeProcessThread implements Runnable {

    private SimpleLog log = new SimpleLog("log");

    private String officeFileName;
    private String officePath;
    private File officeFile;
    private String toPdfDirectory;

    @Override
    public void run() {
        processOfficeFile();
    }


    public void processOfficeFile() {
        String pdfFileName = officeFileName.substring(0,officeFileName.lastIndexOf('.'))+".pdf";

        String pdfPath = toPdfDirectory+File.separator +pdfFileName;
        log.info("Thread : " + Thread.currentThread().getName() + " 转换 ： " + officePath + " 到 : " + pdfPath);

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(PWXFileToPdfThread.class.getResource("/").getPath()+"/properties/filesDirectory.properties"));
        } catch (IOException e) {
            log.info(e);
        }
        String imageDirectory = properties.getProperty("allImagesDirectory");
        try {
            AsposeOfficeToPdfUtils.officesToPdf(officePath,pdfPath,imageDirectory);
        } catch (Exception e) {
            log.info(e);
        }
    }
    public PWXFileToPdfThread(String officeFileName, String officePath, File officeFile, String toPdfDirectory) {
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
