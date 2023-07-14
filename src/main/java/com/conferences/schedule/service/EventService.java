package com.conferences.schedule.service;
import com.conferences.schedule.model.Event;

import java.util.List;

public interface EventService {
    Event addEvent(Event event);
    Event getEventById(long id);
    List<Event> getEvents();
    void deleteEvent(long id);
    Event updateEvent(Event event);

}
