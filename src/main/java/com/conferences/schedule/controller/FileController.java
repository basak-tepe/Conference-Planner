package com.conferences.schedule.controller;

import com.conferences.schedule.model.PresentationFile;
import com.conferences.schedule.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/events/files")
public class FileController {
    @Autowired
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/add")
    public void addFile(@RequestParam MultipartFile file) throws Exception{
        fileService.addFile(file);
    }

}