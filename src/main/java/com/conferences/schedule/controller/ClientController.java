package com.conferences.schedule.controller;

import com.conferences.schedule.model.Event;
import com.conferences.schedule.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//import com.conferences.schedule.basicauth.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/api/events")
@CrossOrigin(origins = "http://localhost:5173") // frontend URL

public class ClientController {
    //service
    private final EventService eventService;

    @Autowired
    public ClientController(EventService eventService) {
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


    @GetMapping ("/hello")
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
        return "Event deleted successfully.";
    }
}

