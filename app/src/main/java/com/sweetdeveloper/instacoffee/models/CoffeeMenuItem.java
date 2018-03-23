package com.sweetdeveloper.instacoffee.models;


public class CoffeeMenuItem {

    private String name;
    private String image;
    private String description;

    public CoffeeMenuItem() {
    }

    public CoffeeMenuItem(String name, String image, String description) {
        this.name = name;
        this.image = image;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
