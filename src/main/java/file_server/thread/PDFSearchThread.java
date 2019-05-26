package file_server.thread;

public class PDFSearchThread extends AbstractOfficeProcessThread {
    private String pdfFileDirectory;
    private OfficeThreadPoolManager poolManager;
    private String imagesToDirectory;

    @Override
    public void run() {
        processOfficeFile();
    }

    @Override
    public void processOfficeFile() {

    }
    private String searchPdfByRecursion(){
        while (true){

        }
    }
    private void commitThread(AbstractOfficeProcessThread thread){
        poolManager.commitThread(thread);
    }

    public PDFSearchThread(String pdfFileDirectory, OfficeThreadPoolManager poolManager, String imagesToDirectory) {
        this.pdfFileDirectory = pdfFileDirectory;
        this.poolManager = poolManager;
        this.imagesToDirectory = imagesToDirectory;
    }

    public PDFSearchThread(String pdfFileDirectory, OfficeThreadPoolManager poolManager) {
        this.pdfFileDirectory = pdfFileDirectory;
        this.poolManager = poolManager;
    }

    public String getPdfFileDirectory() {
        return pdfFileDirectory;
    }

    public void setPdfFileDirectory(String pdfFileDirectory) {
        this.pdfFileDirectory = pdfFileDirectory;
    }

    public OfficeThreadPoolManager getPoolManager() {
        return poolManager;
    }

    public void setPoolManager(OfficeThreadPoolManager poolManager) {
        this.poolManager = poolManager;
    }

}
