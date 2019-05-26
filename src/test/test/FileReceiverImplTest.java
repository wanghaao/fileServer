package test;

import fileserver.controller.FileReceiverImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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

        MockMultipartFile mockMultipartFile1 = new MockMultipartFile("test1.xlsx", new FileInputStream(new File("C:\\allFiles\\wanghao\\汪浩_科研能力加分表2016-2.xls")));

        MockMultipartFile mockMultipartFile2 = new MockMultipartFile("test2.png", new FileInputStream(new File("C:\\allFiles\\wanghao\\demo1\\4_1.png")));
        MockMultipartFile mockMultipartFile3 = new MockMultipartFile("test3.pdf", new FileInputStream(new File("C:\\allFiles\\wanghao\\demo1\\11.pdf")));

        fileReceiver.uploadImageAndOffices(mockMultipartFile1,"zhangyiwen");
        fileReceiver.uploadImageAndOffices(mockMultipartFile2,"zhangyiwen");
        fileReceiver.uploadImageAndOffices(mockMultipartFile3,"zhangyiwen");

    }

    @Test
    public void createDepository() {
        fileReceiver.createDepository("wang22");
    }

    @Test
    public void createDirectory() {
        fileReceiver.createDirectory("wang22","demo");
    }

    @Test
    public void searchDirectory() {
        fileReceiver.searchDirectory("wang");
    }

    @Test
    public void downloadFile() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        fileReceiver.downloadFile("wanghao/demo1/4_1.png",request,response);
    }

    @Test
    public void getImageDataInBase64() {
        fileReceiver.getImageDataInBase64("wanghao/demo1/4_1.png","image");
        fileReceiver.getImageDataInBase64("wanghao/demo1/11.pdf", "office");
    }

}