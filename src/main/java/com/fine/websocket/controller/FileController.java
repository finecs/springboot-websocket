package com.fine.websocket.controller;

import com.fine.websocket.domain.File;
import com.fine.websocket.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件上传与下载
 * 单文件、多文件
 */

@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    //单文件上传
    @PostMapping("/upload")
    public File uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileService.storeFile(file);
        String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/").path(fileName).toUriString();
        return new File(fileName, downloadUrl, file.getContentType(), file.getSize());
    }

    //多文件上传
    @PostMapping("/uploadMultiple")
    public List<File> multipleFiles(@RequestParam("files") MultipartFile[] files){
        return Arrays.stream(files).map(this::uploadFile).collect(Collectors.toList());
    }

    //下载
    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws MalformedURLException {
        Resource resource = fileService.loadResource(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename() + "\"")
                .body(resource);
    }

}
