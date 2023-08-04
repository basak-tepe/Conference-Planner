
package com.conferences.schedule.service;
import com.conferences.schedule.model.PresentationFile;
import com.conferences.schedule.repository.FileRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@Service
public class FileServiceImpl implements FileService{

    @Autowired
    private final FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Override
    public void addFile(MultipartFile file) throws IOException {
        DBObject dbObject = new BasicDBObject();
        dbObject.put("fileName",file.getOriginalFilename());
        dbObject.put("contentType",file.getContentType());
        dbObject.put("size",file.getSize());
        dbObject.put("userId","12345");
        gridFsTemplate.store(file.getInputStream(),file.getOriginalFilename(),dbObject);
    }
    public InputStream downloadFile(String fileName) throws IOException {
        GridFsResource resource = gridFsTemplate.getResource(fileName);
        return Objects.requireNonNull(resource).getInputStream();
    }
}
