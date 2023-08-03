package com.conferences.schedule.repository;


import com.conferences.schedule.model.PresentationFile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends MongoRepository<PresentationFile, Long> {
}
