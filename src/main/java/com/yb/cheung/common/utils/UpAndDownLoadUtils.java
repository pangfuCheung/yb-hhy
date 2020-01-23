package com.yb.cheung.common.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UpAndDownLoadUtils {

    public List<String> getRelPathByUpload(HttpServletRequest request){
        List<File> files = getRealPathByUpload(request);
        List<String> fileList = new ArrayList<>();
        for(File file : files) {
            fileList.add(getRelPath(request) + "static/upload/"+file.getName());
        }
        return fileList;
    }

    public List<File> getRealPathByUpload(HttpServletRequest request){
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;//1、将请求进行转义
        Map<String, MultipartFile> files = multipartHttpServletRequest.getFileMap();//2、获取同一表单提交过来的所有文件
        //3、在真实路径创建文件
        File dir = new File(getRealPath(request) + "static/upload/");
        if(!dir.exists()) {
            dir.mkdirs();
        }
        List<File> fileList = new ArrayList<>();//4、将上传的文件的相对地址保存在一个列表中(客户端只能请求服务器的相对地址)
        for(MultipartFile file : files.values()) {  //5、在服务器的绝对地址下新建文件,并将上传的文件复制过去,将相对路径保存进List列表中,服务器的相对路径和绝对路径是相互映射的，客户端只能请求相对路径
            File targetFile = new File(getRealPath(request) + "static/upload/" + file.getOriginalFilename());
            if(!targetFile.exists()) {
                try {
                    targetFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    file.transferTo(targetFile);
                    fileList.add(targetFile);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileList;
    }


    public ResponseEntity<byte[]> downLoad(HttpServletRequest request, String templateName)throws IOException{
        String realPath = getRealPath(request)+"/static/template/"+templateName;
        File file = new File(realPath);
        byte[] body = null;
        InputStream is = new FileInputStream(file);
        body = new byte[is.available()];
        is.read(body);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=" + file.getName());
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
        return entity;
    }

    public String getRelPath(HttpServletRequest request) {
        String path = request.getContextPath();
        //System.out.println(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/");
        return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    }

    private String getRealPath(HttpServletRequest request){
        //return "D:\\tomcat\\";
        //System.out.println(request.getSession().getServletContext().getRealPath("/").replace("\\", "/"));
        return request.getSession().getServletContext().getRealPath("/").replace("\\", "/");
    }


}

