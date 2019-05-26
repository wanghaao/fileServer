package file_server.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.aspose.cells.License;
import com.aspose.words.Document;
import com.aspose.words.SaveFormat;

public class NewTest {

	public static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = file_server.office.AsposeOfficeToPdfUtils.class.getClassLoader().getResourceAsStream("\\main\\java\\file_server\\aspose\\license.xml");
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

	public static void main(String[] args) {
		// 验证License
        if (!getLicense()) {
            return;
        }

        try {
            long old = System.currentTimeMillis();
            Document doc = new Document("E:\\Documents\\Desktop\\officeTest\\家庭背景证明.docx");// 原始word路径
            File pdfFile = new File("E:\\Documents\\Desktop\\officeToDirectory\\123.pdf");// 输出路径
            FileOutputStream fileOS = new FileOutputStream(pdfFile);
            doc.save(fileOS, SaveFormat.PDF);
            long now = System.currentTimeMillis();
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
