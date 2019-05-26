package file_server.entity;

public class WordFileEntity extends FileEntity {
    @Override
    public int resetData(String newData) {
        return 0;
    }

    public WordFileEntity(String fileName, String filePath, String fileData) {
        super(fileName, filePath, fileData);
    }

    @Override
    public String getFileData() {
        return super.getFileData();
    }

    @Override
    public String getFileName() {
        return super.getFileName();
    }

    @Override
    public String getFilePath() {
        return super.getFilePath();
    }
}
