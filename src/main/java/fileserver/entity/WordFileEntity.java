package fileserver.entity;

public class WordFileEntity extends FileEntity {
    @Override
    public int resetData(String newData) {
        return 0;
    }

    public WordFileEntity(String fileName, String filePath, String fileData) {
        super(fileName, filePath, fileData);
    }

}
