package file_server.office;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.aspose.cells.Workbook;
import com.aspose.slides.Presentation;
import com.aspose.words.Document;
import com.aspose.words.SaveFormat;

public class AsposeOfficeToPdfUtils implements OfficeUtil{

	public static boolean getLicense(String classPath) {
		boolean result = false;
        try {
        	InputStream is = new FileInputStream(AsposeOfficeToPdfUtils.class.getResource("/").getPath()+"/office/license.xml");
            Class clazz = Class.forName(classPath);
            Object obj = clazz.newInstance();
            clazz.getMethod("setLicense", InputStream.class).invoke(obj, is);
            result = true;
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
	}

	/**
	 *
	 * @param filePath
	 * @param pdfPath
	 * @throws Exception
	 */
	public static void OfficesToPdf(String filePath, String pdfPath,String imageDirectory) throws Exception {

		if (filePath.endsWith("doc") || filePath.endsWith("docx")) {
			wordToPdf(filePath, pdfPath,imageDirectory);
		} else if (filePath.endsWith("xls") || filePath.endsWith("xlsx")) {
			excelToPDF(filePath, pdfPath,imageDirectory);
		} else if (filePath.endsWith("ppt") || filePath.endsWith("pptx")) {
			pptToPDF(filePath, pdfPath,imageDirectory);
		} else {
			throw new RuntimeException("非word Office 文件");
		}
	}


	/**
	 * @param pdfPath
	 * @throws Exception
	 */
	private static void wordToPdf(String wordPath,String pdfPath,String imageDirectory) {
        // 验证License
        if (!getLicense("com.aspose.words.License")) {
            return;
        }
        try {
            long old = System.currentTimeMillis();
            Document doc = new Document(wordPath);// 原始word路径
            File pdfFile = new File(pdfPath);// 输出路径
            FileOutputStream fileOS = new FileOutputStream(pdfFile);
            doc.save(fileOS, SaveFormat.PDF);

            // pdf 到 png
			PdfboxPdfToImageUtils.pdf2Image(pdfPath,imageDirectory,200,1);
			long now = System.currentTimeMillis();
			System.out.println("ppt--pdf--png: "+pdfPath+",cost time：" + ((now - old) / 1000.0) + " seconds."); // 转化用时
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	/**
	 * 
	 * @param pdfPath
	 * @throws Exception
	 */
	private static void excelToPDF(String filePath, String pdfPath,String imageDirectory) throws Exception {
		if (!getLicense("com.aspose.cells.License")) { // 验证License 若不验证则转化出的pdf文档会有水印产生
			return;
		}
		try {
			long old = System.currentTimeMillis();
			Workbook wb = new Workbook(filePath);// 原始excel路径
			FileOutputStream fileOS = new FileOutputStream(new File(pdfPath));
			wb.save(fileOS, com.aspose.cells.SaveFormat.PDF);
			fileOS.close();

			// pdf 到 png1
			PdfboxPdfToImageUtils.pdf2Image(pdfPath,imageDirectory,200,1);
			long now = System.currentTimeMillis();
			System.out.println("ppt--pdf--png: "+",cost time：" + ((now - old) / 1000.0) + " seconds."); // 转化用时
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * @param pdfPath
	 * @throws Exception
	 */
	private static void pptToPDF(String filePath, String pdfPath,String imageDirectory) throws Exception {
		if (!getLicense("com.aspose.slides.License")) { // 验证License 若不验证则转化出的pdf文档会有水印产生
			return;
		}
		try {
			long old = System.currentTimeMillis();
			Presentation pres = new Presentation(filePath);
			FileOutputStream fileOS = new FileOutputStream(new File(pdfPath));
			pres.save(fileOS,com.aspose.slides.SaveFormat.Pdf);
			fileOS.close();

			// pdf 到 png
			PdfboxPdfToImageUtils.pdf2Image(pdfPath,imageDirectory,200,1);
			long now = System.currentTimeMillis();
			System.out.println("ppt--pdf--png: "+",cost time：" + ((now - old) / 1000.0) + " seconds."); // 转化用时
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
