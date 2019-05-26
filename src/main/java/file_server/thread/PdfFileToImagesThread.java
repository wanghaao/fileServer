package file_server.thread;

import file_server.office.PdfboxPdfToImageUtils;

import java.io.File;

public class PdfFileToImagesThread extends AbstractOfficeProcessThread {

    private String pdfFileName;
    private String pdfFilePath;
    private String toImagesDirectory;
    private File pdfFile;

    public PdfFileToImagesThread(String pdfFileName, String pdfFilePath, String toImagesDirectory, File pdfFile) {
        //super(pdfFileName,pdfFilePath,toImagesDirectory,pdfFile);
        this.pdfFile = pdfFile;
        this.pdfFileName =pdfFileName;
        this.pdfFilePath = pdfFilePath;
        this.toImagesDirectory = toImagesDirectory;
    }

    @Override
    public void run() {
        processOfficeFile();
    }

    @Override
    public void processOfficeFile() {
        PdfboxPdfToImageUtils.pdf2Image(pdfFilePath,toImagesDirectory,300,0);
    }

    public String getPdfFileName() {
        return pdfFileName;
    }

    public void setPdfFileName(String pdfFileName) {
        this.pdfFileName = pdfFileName;
    }

    public String getPdfFilePath() {
        return pdfFilePath;
    }

    public void setPdfFilePath(String pdfFilePath) {
        this.pdfFilePath = pdfFilePath;
    }

    public String getToImagesDirectory() {
        return toImagesDirectory;
    }

    public void setToImagesDirectory(String toImagesDirectory) {
        this.toImagesDirectory = toImagesDirectory;
    }

    public File getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(File pdfFile) {
        this.pdfFile = pdfFile;
    }
}
