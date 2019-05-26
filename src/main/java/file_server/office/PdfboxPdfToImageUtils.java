package file_server.office;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import file_server.cache.FileCacheImpl;
import file_server.utils.FileProcessorUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import com.lowagie.text.pdf.PdfReader;

public class PdfboxPdfToImageUtils implements OfficeUtil {
//    public static void main(String[] args) {
//        pdf2Image("E:\\Documents\\Desktop\\officeToDirectory\\123.pdf", "E:\\Documents\\Desktop\\officeToDirectory\\", 300);
//    }
    private static FileCacheImpl fileCache = new FileCacheImpl();
    // page 值应该是针对每一个线程是唯一的，所以在工具类里面是不可以的
    private int pageNumber = 0;
    /***
     * PDF文件转PNG图片，全部页数
     * @param PdfFilePath pdf完整路径
     * @param dstImgFolder 图片存放的文件夹
     * @param dpi dpi越大转换后越清晰，相对转换速度越慢
     * @return
     */
    public static void pdf2Image(String PdfFilePath, String dstImgFolder, int dpi,int tag) {

        System.out.println(PdfFilePath + " ------ " + dstImgFolder);
        File file = new File(PdfFilePath);
        try {
            String imgPDFPath = file.getParent();
            int dot = file.getName().lastIndexOf('.');
            String imagePDFName = file.getName().substring(0, dot); // 获取图片文件名
            String imgFolderPath = null;
            if (dstImgFolder.equals("")) {
                imgFolderPath = imgPDFPath + File.separator + imagePDFName;// 获取图片存放的文件夹路径
            } else {
                imgFolderPath = dstImgFolder + File.separator + imagePDFName;
            }

            if (createDirectory(imgFolderPath)) {

                PDDocument pdDocument = null;
                pdDocument = PDDocument.load(file);
                PDFRenderer renderer = new PDFRenderer(pdDocument);
                /* dpi越大转换后越清晰，相对转换速度越慢 */
                PdfReader reader = new PdfReader(PdfFilePath);
                int pages = reader.getNumberOfPages();
                StringBuffer imgFilePath = null;
                for (int i = 0; i < pages; i++) {
                    String imgFilePathPrefix = imgFolderPath + File.separator + FileProcessorUtils.getAbsoluteFileName(PdfFilePath) + imagePDFName;

                    imgFilePath = new StringBuffer();
                    imgFilePath.append(imgFilePathPrefix);
                    imgFilePath.append("_");
                    imgFilePath.append(String.valueOf(i + 1));
                    imgFilePath.append(".png");
                    File dstFile = new File(imgFilePath.toString());
                    // 新的文件，添加到记录里
                    addRecord(dstFile);
                    BufferedImage image = renderer.renderImageWithDPI(i, dpi);
                    ImageIO.write(image, "png", dstFile);
                }
                reader.close();
                pdDocument.close();
                System.out.println("PDF--PNG: "+PdfFilePath+" success!");

            } else {
                System.out.println("PDF--PNG: " + " create " + imgFolderPath + " failed.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(tag==1){
            file.delete();
        }
    }

    private static boolean createDirectory(String folder) {
        File dir = new File(folder);
        if (dir.exists()) {
            return true;
        } else {
            return dir.mkdirs();
        }
    }

    public static int calculatePageNumber(File pdfFile){
        PdfReader reader = null;
        try {
            reader = new PdfReader(pdfFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        int pages = reader.getNumberOfPages();
        reader.close();
        return pages;
    }
    public static void addRecord(File file){
        fileCache.recallAddRecord(file);
    }

}

