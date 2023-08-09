package com.conferences.schedule.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;



@Document(collection = "events")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    private Long id;
    private String title;
    private String description;
    private String presenter;
    private String date;
    private String time;
    private LocalDateTime startTime;
    private String displayTime;
    private String fileName;
    private String location;
    private String eventType; //either conference, meeting, or activity

    // Constructors, getters, and setters
}