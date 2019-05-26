package file_server.entity;


public class ImageEntity extends FileEntity{

    public ImageEntity(String imagePath,String imageName,String imageDataInBase64) {
        super(imagePath,imageName,imageDataInBase64);
    }


    @Override
    public String getFileName() {
        return super.getFileName();
    }

    @Override
    public int resetData(String newData) {
        super.fileData = newData;
        return 0;
    }

    @Override
    public String getFilePath() {
        return super.getFilePath();
    }

    @Override
    public String getFileData() {
        return super.getFileData();
    }
}
