package com.example.dinesh.eventapp;

public class MyDataSetGet {


    String name,from,till,location,image_url;

    public MyDataSetGet(){

    }

    public MyDataSetGet(String name, String from, String till, String location, String image_url) {
        this.name = name;
        this.from = from;
        this.till = till;
        this.location = location;
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTill() {
        return till;
    }

    public void setTill(String till) {
        this.till = till;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }




    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }


}
