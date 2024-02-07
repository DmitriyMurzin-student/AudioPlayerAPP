package com.example.lacosteplayer;

public class AudioList {
    private String name;
    private String autor;
    private int imageResurse;

    public AudioList(String name, String autor, int imageResurse) {
        this.name = name;
        this.autor = autor;
        this.imageResurse = imageResurse;
    }

    public String getName() {
        return name;
    }

    public String getAutor() {
        return autor;
    }
    public int getImageResurse(){
        return imageResurse;
    }
}
