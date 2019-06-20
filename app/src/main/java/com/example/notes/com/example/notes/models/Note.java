package com.example.notes.com.example.notes.models;

public class Note {

    String id;
    String title;
    String text;

    public Note(){
        this.id = "";
        this.title = "";
        this.title = "";
    }
    public Note(String id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
