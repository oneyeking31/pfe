package com.example.exam_gestion;

public class Presence {
    private String name ;
    private int presence ;

    public Presence(String name, int presence) {
        this.name = name;
        this.presence = presence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int isPresence() {
        return presence;
    }

    public void setPresence(int presence) {
        this.presence = presence;
    }
}
