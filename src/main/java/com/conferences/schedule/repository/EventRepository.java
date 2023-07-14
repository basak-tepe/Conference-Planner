package com.conferences.schedule.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.conferences.schedule.model.Event;

@Repository
public interface EventRepository extends CrudRepository<Event,Long> {


}
