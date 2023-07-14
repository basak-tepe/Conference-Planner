package com.conferences.schedule.service;

import com.conferences.schedule.model.Event;
import com.conferences.schedule.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return eventRepository.save(event);
    }
}
