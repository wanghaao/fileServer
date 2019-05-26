package fileserver.controller;

import fileserver.cache.FileCacheImpl;
import fileserver.utils.DataToJsonUtils;
import fileserver.utils.FileProcessorUtils;
import org.apache.commons.logging.impl.SimpleLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Controller("fileReceiverImpl")
public class FileReceiverImpl extends AbstractFileReceiver{

    private SimpleLog log = new SimpleLog("log");
    private String mainDirectory;

    public String getMainDirectory() {
        if (mainDirectory==null){
            mainDirectory = FileProcessorUtils.getTheFilePathFromPropertiesFile("allFilesDirectory");
        }
        return mainDirectory;
    }

    /**
     * 上传 office文件  和 各种 图片文件
     * @param file
     * @param path
     * @return
     */
    @RequestMapping(value = "/upload/imagesAndOffices",method = RequestMethod.POST)
    @ResponseBody
    public String uploadImageAndOffices(@Param("file") MultipartFile file,@Param("path")String path) {
        FileCacheImpl fileCacheImpl  = new FileCacheImpl();
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件后缀
        String storePath = getMainDirectory() +File.separator+ path +File.separator+ fileName;

        log.info("存储路径: "+ storePath);
        // 存储在disk  ,插入 到 cache记录
        fileCacheImpl.storeNewFile(file,storePath);
        return "1";// 硬盘的真实文件名
    }

    /***
     * 创建 仓库 的目录
     * @param depository
     * @return
     */
    @RequestMapping(value = "/create/depository",method = RequestMethod.POST)
    @ResponseBody
    public String createDepository(@Param("depository") String depository ) {

        String depositoryDirectory = getMainDirectory()+ File.separator + depository;
        File dir = new File(depositoryDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
            return "1";
        }else{
            return "0";
        }
    }

    /**
     * 创建 仓库 下的文件夹
     * @param directories
     * @return
     */
    @RequestMapping(value = "/create/directory",method = RequestMethod.POST)
    @ResponseBody
    public String createDirectory(@Param("directories") String directories,@Param("newDirectory")String newDirectory ) {
        String depositoryDirectory = getMainDirectory()+ File.separator + directories + File.separator + newDirectory;
        File dir = new File(depositoryDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
            return "1";
        }else{
            return "0";
        }
    }

    /**
     * 查询 指定目录下的 文件列表,嵌套文件 的 路径
     * @param directories
     * @return
     */
    @RequestMapping(value = "/search/directory",method = RequestMethod.POST)
    @ResponseBody
    public String searchDirectory(@Param("directories") String directories) {
        String directory = getMainDirectory() +"\\"+ directories;
        File file = new File(directory);
        List<String> list = new ArrayList<String>();
        if(file.exists()) {
            File[] files = file.listFiles();
            for (File file0 : files) {
                list.add(file0.getName());
            }
        }
        return DataToJsonUtils.jsonNoStatusList(list);
    }

    /**
     *  返回 commonFile 的 流
     * @param filePath
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/download/commonFile",method = RequestMethod.POST)
    @ResponseBody
    public void downloadFile(@Param("filePath") String filePath, HttpServletRequest request, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin","*");
        response.addHeader("Access-Control-Allow-Methods","POST");
        response.addHeader("Access-Control-Allow-Headers","x-requested-with,content-type");

        String absolutePath = getMainDirectory() +File.separator+ filePath;
        File file = new File(absolutePath);
        //获取输入流
        BufferedInputStream bis = null;
        BufferedOutputStream out = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            //假如以中文名下载的话 
            String filename = file.getName();
            //转码，免得文件名中文乱码
            filename = URLEncoder.encode(filename, "UTF-8");
            //设置文件下载头  
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型    
            response.setContentType("multipart/form-data");
            out = new BufferedOutputStream(response.getOutputStream());
            int len = 0;
            while ((len = bis.read()) != -1) {
                out.write(len);
                out.flush();
            }
            out.close();
            bis.close();
        } catch (FileNotFoundException e) {
            log.info(e);
        } catch (UnsupportedEncodingException e) {
            log.info(e);
        } catch (IOException e) {
            log.info(e);
        }finally {
            if(bis!=null){
                try {
                    bis.close();
                } catch (IOException e) {
                    log.info(e);
                }
            }
            if (out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.info(e);
                }
            }

        }

    }

    /**
     * 图片直接返回 编码， office 返回所有 的路径，再次请求返回 编码
     * @param path
     * @param tag
     * @return
     */
    @RequestMapping(value = "/preView/files",method = RequestMethod.POST)
    @ResponseBody
    public String getImageDataInBase64(@Param("path") String path,@Param("tag") String tag){
        FileCacheImpl fileCacheImpl  = new FileCacheImpl();
        String realFileName = path.replaceAll("/","-");
        log.info(path + "--------"+realFileName);
        if(tag.equals("image")){
            return fileCacheImpl.getSpecialFile(realFileName).getFileData();
        }else if(tag.equals("office")){
            // office 的 文件夹
            String officeDirectory = realFileName.substring(realFileName.lastIndexOf('.')+1,realFileName.indexOf('.'));
            String officeImagesDirectory = getMainDirectory() + File.separator + officeDirectory;

            log.info(officeImagesDirectory);
            File file = new File(officeImagesDirectory);
            List<String> imagesList = new ArrayList<String>();
            if (file.exists()){
                File[] files = file.listFiles();
                for (File file0: files) {
                    if(file0.isFile()){
                        imagesList.add(file0.getName().replaceAll("-","/"));
                    }
                }
            }
            return DataToJsonUtils.jsonNoStatusList(imagesList);
        }else{
            return null;
        }
    }


}
