package file_server.entity;


public abstract class FileEntity {

    String fileName;
    String filePath;
    String fileData;

    public FileEntity() {
    }

    public FileEntity(String fileName, String filePath, String fileData) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileData = fileData;
    }

    // 重置数据
    public abstract int resetData(String newData);

    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


}
