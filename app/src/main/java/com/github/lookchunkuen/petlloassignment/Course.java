package com.github.lookchunkuen.petlloassignment;

public class Course {
    private String name;
    private String profile;
    private String condition;
    private String body;
    private String color;
    private String location;
    private String adoptionFee;


    //An empty constructor
    public Course() {
    }

    //A constructor with param
    public Course(String name, String profile, String condition, String body, String color, String location, String adoptionFee) {
        this.name = name;
        this.profile = profile;
        this.condition = condition;
        this.body = body;
        this.color = color;
        this.location = location;
        this.adoptionFee = adoptionFee;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAdoptionFee() {
        return adoptionFee;
    }

    public void setAdoptionFee(String adoptionFee) {
        this.adoptionFee = adoptionFee;
    }




}