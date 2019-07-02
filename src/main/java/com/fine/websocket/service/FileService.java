package com.fine.websocket.service;

import com.fine.websocket.config.FileConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {
    //文件的存储地址
    private Path path;

    @Autowired
    public FileService(FileConfig fileConfig) {
        this.path= Paths.get(fileConfig.getDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //存储文件
    public String storeFile(MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
        Path targetdir = this.path.resolve(fileName);
            Files.copy(file.getInputStream(), targetdir, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    //加载文件
    public Resource loadResource(String fileName) throws MalformedURLException {
        Path filePath = this.path.resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        return resource;
    }
}
