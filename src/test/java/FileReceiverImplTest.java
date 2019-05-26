package test;

import fileserver.controller.FileReceiverImpl;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;

public class FileReceiverImplTest {

    FileReceiverImpl fileReceiver = new FileReceiverImpl();
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getMainDirectory() {

    }

    @Test
    public void uploadImageAndOffices() throws IOException {

        MockMultipartFile mockMultipartFile1 = new MockMultipartFile("test.xlsx", new FileInputStream(new File("/home/admin/test.xlsx")));

        MockMultipartFile mockMultipartFile2 = new MockMultipartFile("test.png", new FileInputStream(new File("/home/admin/test.png")));
        MockMultipartFile mockMultipartFile3 = new MockMultipartFile("test.pdf", new FileInputStream(new File("/home/admin/test.pdf")));

        fileReceiver.uploadImageAndOffices(mockMultipartFile1,"wang");
        fileReceiver.uploadImageAndOffices(mockMultipartFile2,"wang");
        fileReceiver.uploadImageAndOffices(mockMultipartFile3,"wang");

    }

    @Test
    public void createDepository() {
        fileReceiver.createDepository("wang");
    }

    @Test
    public void createDirectory() {
        fileReceiver.createDirectory("wang/demo","/dd");
    }

    @Test
    public void searchDirectory() {
        fileReceiver.searchDirectory("wang");
    }

    @Test
    public void downloadFile() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        fileReceiver.downloadFile("wang/123.txt",request,response);
    }

    @Test
    public void getImageDataInBase64() {
        fileReceiver.getImageDataInBase64("wang/aa.png","image");
        fileReceiver.getImageDataInBase64("wang/aa.pdf", "office");
    }

}