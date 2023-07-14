package com.conferences.schedule.controller;

import com.conferences.schedule.model.Event;
import com.conferences.schedule.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientController {
    //service
    @Autowired
    private EventService eventService;

    @RequestMapping("/")
    public String hello_world(){
        return "Hello World";
    }

    //add event
    @PostMapping("/add")
    public String addEvent(@RequestBody Event event){
    eventService.addEvent(event);
    return "Event added successfully!";
    }

    //getEventById
    @RequestMapping("/event/{id}")
    public Event getEventById(@PathVariable("id") int id){
        return eventService.getEventById(id);
    }
    //getEvents
    @RequestMapping("/events")
    public List<Event> getEvents(){
        return eventService.getEvents();
    }

    //update event
    @PutMapping("/event")
    public Event updateEvent(@RequestBody Event event){
        return eventService.updateEvent(event);
    }

    //delete an event

    @DeleteMapping("/event/{id}")
    public String deleteEvent(@PathVariable("id") long id){
        eventService.deleteEvent(id);
        return "Event deleted succesfully.";
    }
}

