package com.example.Hotelv2.models;

import jakarta.persistence.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * Сущность Отель
 */
@Entity
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long hotelID;
    String name;
    String description;
    Double price;
    String pathToPic;
    Long views;

    public Hotel(String name, String description, Double price, String pathToPic, Long views) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.pathToPic = pathToPic;
        this.views = views;
    }

    public Hotel() {
    }

    public Long getHotelID() {
        return hotelID;
    }

    public void setHotelID(Long hotelID) {
        this.hotelID = hotelID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPathToPic() {
        return pathToPic;
    }

    public void setPathToPic(String pathToPic) {
        this.pathToPic = pathToPic;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public void addViews() {
        views++;
    }
}
