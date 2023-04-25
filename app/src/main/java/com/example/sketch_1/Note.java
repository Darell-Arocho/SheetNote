package com.example.sketch_1;

import com.google.firebase.Timestamp;

// Note class representing a note with a title, content and timestamp
public class Note {
    String title; // title of the note
    String content; // content of the note
    Timestamp timestamp; // timestamp when the note was created or last modified

    public Note() {
        // Default constructor required for calls to DataSnapshot.getValue(Note.class)
    }

    // Getter method for the title of the note
    public String getTitle() {
        return title;
    }

    // Setter method for the title of the note
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter method for the content of the note
    public String getContent() {
        return content;
    }

    // Setter method for the content of the note
    public void setContent(String content) {
        this.content = content;
    }

    // Getter method for the timestamp of the note
    public Timestamp getTimestamp() {
        return timestamp;
    }

    // Setter method for the timestamp of the note
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
