package com.conferences.schedule.service;

import com.conferences.schedule.model.Event;
import com.conferences.schedule.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


//create,read,update, delete operations
@Service
public class EventServiceImpl implements EventService {
    //repository
    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event addEvent(Event event) {
        //event.setId(Long.valueOf(UUID.randomUUID().toString().split("-")[0]));
        return eventRepository.save(event);
    }

    @Override
    public Event getEventById(long id) {
        return eventRepository.findById(id).get();
    }

    @Override
    public List<Event> getEvents() {
        return (List<Event>) eventRepository.findAll();
    }

    @Override
    public void deleteEvent(long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public Event updateEvent(Event event) {
        Event existingEvent = eventRepository.findById(event.getId()).get();
        existingEvent.setId(event.getId());
        existingEvent.setDescription(event.getDescription());
        existingEvent.setTitle(event.getTitle());
        existingEvent.setEndTime(event.getEndTime());
        existingEvent.setStartTime(event.getStartTime());
        return eventRepository.save(existingEvent);
    }
}
