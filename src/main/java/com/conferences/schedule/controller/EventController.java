package com.conferences.schedule.controller;

import com.conferences.schedule.model.Event;
import com.conferences.schedule.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    //service
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login() {
        // You can perform authentication here based on the provided credentials.
        // For example, you can compare the credentials with the ones in your database.
        // If authentication is successful, return a success response with a status code 200
        return ResponseEntity.ok("Login successful!");

        // If authentication fails, return a failure response with a status code 401 (Unauthorized)
        // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed!");
    }

    @GetMapping("/hello")
    public String hello_world() {
        return "Hello World";
    }

    //add event
    @PostMapping("/add")
    public ResponseEntity<String> addEvent(@RequestBody Event event) {
        eventService.addEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body("Event added successfully!");
    }

    @GetMapping("/events")
    public List<Event> getEvents() {
        return eventService.getEvents();
    }


    //getEventById
    @GetMapping("/event/{id}")
    public Event getEventById(@PathVariable int id) {
        return eventService.getEventById(id);
    }
    //getEvents

    //update event
    @PutMapping("/event")
    public Event updateEvent(@RequestBody Event event) {
        return eventService.updateEvent(event);
    }

    //delete an event
    @DeleteMapping("/event/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.status(HttpStatus.OK).body("Event deleted successfully.");
    }
}

