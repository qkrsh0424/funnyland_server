package com.funnyland.funnyland_server.controller.s3;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.funnyland.funnyland_server.annotation.RequiredLogin;
import com.funnyland.funnyland_server.service.s3.FileUploadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/fileupload")
public class S3UploadAPI {
    @Value("${app.external.address}")
    String address;

    @Autowired
    FileUploadService fileUploadService;

    // /api/fileupload/image
    @PostMapping("/image")
    @RequiredLogin
    public String FileUpload(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("file") MultipartFile[] files) throws IOException {
        String url = fileUploadService.upload(files);
        if (url.equals("FAILURE")) {
            return "{\"message\":\"failure\"}";
        }
        return "{\"message\":\"success\",\"imageUrl\":\"" + url + "\"}";
    }

    // /api/fileupload/external/image
    @PostMapping("/external/image")
    @RequiredLogin
    public String FileUploadExternalImage(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("file") MultipartFile file) {
        String url = fileUploadService.uploadToLocal(request, response, file);
        if(url==null){
            return "{\"message\":\"failure\"}";
        }
        return "{\"message\":\"success\",\"imageUrl\":\"" + url + "\"}";
    }
}
