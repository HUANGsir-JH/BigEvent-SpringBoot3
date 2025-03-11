package org.huang.bigevent.controller;

import org.huang.bigevent.pojo.Result;
import org.huang.bigevent.utils.AliyunOssPicturesUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {
    
    @PostMapping("/upload")
    public Result fileUpload(MultipartFile file){
        String filename = file.getOriginalFilename();
        filename = UUID.randomUUID().toString() + filename;
        try {
            String url = AliyunOssPicturesUtil.upload(filename, file.getInputStream());
            return Result.builder().code(0).message("上传成功").data(url).build();
        } catch (IOException e) {
            e.printStackTrace();
            return Result.builder().code(1).message("上传失败，稍后再试").build();
        }
    }
}
