package com.example.practicaapi.model;

public class Pokedex {

    private String id;
    private String name;
    private int height;
    private int weight;
    private Sprites sprites = new Sprites();

    public Pokedex(String id, String name, int height, int weight) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public int getHeight() {
        return height;
    }


    public int getWeight() {
        return weight;
    }


    public Sprites getSprites() {
        return sprites;
    }

}
