package fileserver.entity;


public class ImageEntity extends FileEntity{

    public ImageEntity(String imagePath,String imageName,String imageDataInBase64) {
        super(imagePath,imageName,imageDataInBase64);
    }




    @Override
    public int resetData(String newData) {
        super.fileData = newData;
        return 0;
    }

}
