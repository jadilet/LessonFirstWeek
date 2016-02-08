package com.developer.artisla.lessonfirstweek.model;

import android.content.Intent;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by artisla on 2/1/16.
 */
public class Company implements Serializable {
    private Integer id;
    private String title;
    private String description;
    private String imagePath;
    private String created_at;

    public Company(){}

    public Company(String title, String description, String imagePath) {
        this.imagePath = imagePath;
        this.title = title;
        this.description = description;
    }

    public Company(int id, String title, String description, String imagePath) {
        this.imagePath = imagePath;
        this.id = id;
        this.description = description;
        this.title = title;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCreated_at(){
        return this.created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) { this.id = id; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
