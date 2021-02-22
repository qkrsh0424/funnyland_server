package com.funnyland.funnyland_server.controller.s3;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.funnyland.funnyland_server.service.s3.FileUploadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/fileupload")
public class S3UploadAPI {
    @Autowired
    FileUploadService fileUploadService;

    // /api/fileupload/image
    @PostMapping("/image")
    public String FileUpload(
        HttpServletRequest request, 
        HttpServletResponse response, 
        @RequestParam("file") MultipartFile[] files
    ) throws IOException {
        String url = fileUploadService.upload(files);
        if(url.equals("FAILURE")){
            return "{\"message\":\"failure\"}";
        }
        return "{\"message\":\"success\",\"imageUrl\":\""+url+"\"}";
    }
}
