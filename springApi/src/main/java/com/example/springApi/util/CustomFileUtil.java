package com.example.springApi.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Log4j2
@RequiredArgsConstructor
public class CustomFileUtil {

    @Value("${com.example.upload.path}")
    private String uploadPath;

    @PostConstruct
    public void init(){
        File tempFolder = new File(uploadPath);

        if(tempFolder.exists() == false){
            tempFolder.mkdirs();
        }
        uploadPath = tempFolder.getAbsolutePath();
        log.info("upload path: " + uploadPath);
    }

    public void deleteFile(List<String> fileNames) {

        if(fileNames == null || fileNames.size() == 0) {
            return;
        }

        //썸네일 확인

        fileNames.forEach(fileName -> {

            String thumbnailFileName = "s_"+fileName;


            Path filePath = Paths.get(uploadPath, fileName);
            Path thumbnailPath = Paths.get(uploadPath, "s_" + thumbnailFileName);

            try {
                Files.deleteIfExists(filePath);
                Files.deleteIfExists(thumbnailPath);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    public ResponseEntity<Resource> getFile(String fileName) {
        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);

        if(!resource.exists()) {
            resource = new FileSystemResource(uploadPath + File.separator + "noImage.jpeg");
        }

        HttpHeaders headers = new HttpHeaders();

        try{
            headers.add("Content-Type", Files.probeContentType( resource.getFile().toPath() ));
        } catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    public List<String> saveFiles(List<MultipartFile> files) throws RuntimeException{

        if(files == null || files.size() == 0){
            return List.of();
        }

        List<String> uploadNames = new ArrayList<>();

        for(MultipartFile multipartFile : files){
            String saveName = UUID.randomUUID().toString()+"_"+multipartFile.getOriginalFilename();

            Path savePath = Paths.get(uploadPath,saveName);

            try{
                Files.copy(multipartFile.getInputStream(),savePath);
                String contentType = multipartFile.getContentType();
                if(contentType != null &&  contentType.startsWith("image")){
                    Path thumbnailPath = Paths.get(uploadPath,"s_"+saveName);
                    Thumbnails.of(savePath.toFile()).size(200,200).toFile(thumbnailPath.toFile());
                }
                uploadNames.add(saveName);
            }catch (IOException e){
                throw new RuntimeException(e.getMessage());
            }
        }



        return uploadNames;
    }

    public void deleteFiles(List<String> fileNames) {
        if(fileNames == null || fileNames.size() == 0){
            return;
        }
        fileNames.forEach(fileName -> {
            //썸네일이 있는지 확인하고 삭제
            String thumbnailFileName = "s_" + fileName;
            Path thumbnailPath = Paths.get(uploadPath, thumbnailFileName);
            Path filePath = Paths.get(uploadPath, fileName);

            try {
                Files.deleteIfExists(filePath);
                Files.deleteIfExists(thumbnailPath);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }
}
