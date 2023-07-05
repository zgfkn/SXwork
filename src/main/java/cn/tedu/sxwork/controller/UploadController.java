package cn.tedu.sxwork.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class UploadController {
    private String dirPath = "C:/Users/Administrator/Desktop/Hello World/my-obj/src/assets";
    @RequestMapping("/upload")
    public String upload(MultipartFile picFile) throws IOException {
        //得到原始文件夹名
        String fileName = picFile.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID()+suffix;
        File dirFile = new File(dirPath);
        if (!dirFile.exists()){
            dirFile.mkdirs();
        }
        String filePath = dirPath+"/"+fileName;
        picFile.transferTo(new File(filePath));
        return "/"+fileName;
    }

    @RequestMapping("/remove")
    public void remove(String name){
        String filePath = dirPath+name;
        new File(filePath).delete();//删除文件
    }
}
