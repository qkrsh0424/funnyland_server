package com.funnyland.funnyland_server.service.s3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
    private AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${app.external.assets.path}")
    private String assetsPath;

    @Value("${app.external.address}")
    private String address;
    
    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    public String upload(MultipartFile[] file) throws IOException {
        String imageDir = "/files";
        String bucketPath = bucket + imageDir;
        
        if(file.length<=0){
            return "FAILURE";
        }
        List<String> fileUrl = new ArrayList<>();
        for(int i = 0 ; i < file.length; i++){
            // String fileName = new Date().getTime() + file[i].getOriginalFilename();  // 파일 풀 네임
            int pos = file[i].getOriginalFilename().lastIndexOf(".");   // 파일의 마지막 . 인덱스를 기준으로 자른다.
            String ext = file[i].getOriginalFilename().substring( pos + 1 );    // 마지막 . 인덱스를 기준으로 뒷쪽 텍스트를 가져온다.
            String fileName = String.valueOf(new Date().getTime())+"-"+((int)(Math.random()*99999)+10000)+"."+ext;  // 최종 파일 네임 : {현재 시간}-{랜덤값}.{확장자명} 

            ObjectMetadata metadata = new ObjectMetadata(); // 에러코드 피하기 위함 : No content length specified for stream data. Stream contents will be buffered in memory and could result in out of memory errors.
            metadata.setContentLength(file[i].getSize());   // 위의 에러코드를 피하기 위해서는 contentLength를 지정해줘야 한다.

            s3Client.putObject(new PutObjectRequest(bucketPath, fileName, file[i].getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead))
                    ;

            // System.out.println(s3Client.getUrl(bucketPath, fileName).toString());
            fileUrl.add(s3Client.getUrl(bucketPath, fileName).toString());
        }
        
        // return String.valueOf(file.length);
        return fileUrl.get(0);
    }

    public String uploadToLocal(HttpServletRequest request, HttpServletResponse response, MultipartFile file) {
        StringBuilder url = new StringBuilder();

        String fileAddress = address;
        String bucketPath = "/uploads/image/";
        
        String originalfileName = file.getOriginalFilename();
        String fileName = String.valueOf(new Date().getTime())+"-"+((int)(Math.random()*99999)+10000)+originalfileName;  // 최종 파일 네임 : {현재 시간}-{랜덤값}{파일명}.{확장자명} 

        url.append(fileAddress);
        url.append(bucketPath);
        url.append(fileName);

        try{
            // String resourceSrc = request.getServletContext().getRealPath("/uploads/image");
            String resourceSrc = assetsPath + "image";
            System.out.println(resourceSrc);
            File dest = new File(resourceSrc+"/"+fileName);
            file.transferTo(dest);
        }catch(IOException e){
            System.out.println(e);
            return null;
        }
        return url.toString();
    }
}
