package com.conferences.schedule.repository;

import com.conferences.schedule.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends MongoRepository<Event, Long> {
}
