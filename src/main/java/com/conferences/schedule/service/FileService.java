package com.conferences.schedule.service;


import com.conferences.schedule.model.PresentationFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    void addFile(MultipartFile file) throws IOException;
}
