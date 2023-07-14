package com.conferences.schedule.service;
import com.conferences.schedule.model.Event
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EventService {
    public Event addEvent(Event event);
    public Event getEventById(long id);
    public List<Event> getEvents();
    public void deleteEvent(long id);

}
