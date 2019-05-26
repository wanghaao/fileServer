package file_server.thread;


public class AbstractOfficeProcessThread implements Runnable {
//    private String fileName;
//    private String filePath;
//    private String toDirectory;
//    private File file;
//
//    public AbstractOfficeProcessThread(String fileName, String filePath, String toDirectory, File file) {
//        this.fileName = fileName;
//        this.filePath = filePath;
//        this.toDirectory = toDirectory;
//        this.file = file;
//    }

    @Override
    public void run() {
        processOfficeFile();
    }

    public void processOfficeFile(){

    }
}
