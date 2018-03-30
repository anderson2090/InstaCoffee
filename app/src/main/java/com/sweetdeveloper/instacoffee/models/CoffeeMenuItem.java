package com.sweetdeveloper.instacoffee.models;


public class CoffeeMenuItem {

    private String name;
    private String image;
    private String description;
    private String key;
    private String price;

    public CoffeeMenuItem() {
    }

    public CoffeeMenuItem(String name, String image, String description, String price) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
    }

    public CoffeeMenuItem(String name, String image, String key, String price, String description) {
        this.name = name;
        this.image = image;
        this.key = key;
        this.price = price;
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
