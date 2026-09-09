package com.example.ac1;

import java.io.Serializable;

public class Book implements Serializable {
    private int id;
    private String title;
    private String author;
    private String category;
    private boolean isRead;

    public Book(int id, String title, String author, String category, boolean isRead) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isRead = isRead;
    }

    public Book(String title, String author, String category, boolean isRead) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.isRead = isRead;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
