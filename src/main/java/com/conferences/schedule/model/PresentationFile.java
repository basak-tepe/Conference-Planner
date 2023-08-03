package com.conferences.schedule.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;

@Document(collection = "files")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PresentationFile {

    @Id
    private Long id; //this will be the event id - but hashed
    private String name;
    private Binary image;

    public PresentationFile(String name, Long id, Binary image) {
        this.name = name;
        this.id = id;
        this.image = image;
    }

    // Getters and setters
}
