package com.example.exam_gestion;


import com.google.common.base.Strings;

public class Exam  {
    private int id ;
    private String level;
    private String section;
    private int groupe;
    private String module;
    private int room ;
    private String  date ;
    private String   time;
    private int duration ;

    public Exam(int id, String level, String section, int groupe, String module, int room, String date, String time, int duration) {
        this.id = id;
        this.level = level;
        this.section = section;
        this.groupe = groupe;
        this.module = module;
        this.room = room;
        this.date = date;
        this.time = time;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public String getLevel() {
        return level;
    }

    public String getSection() {
        return section;
    }

    public int getGroupe() {
        return groupe;
    }

    public String getModule() {
        return module;
    }

    public int getRoom() {
        return room;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getDuration() {
        return duration;
    }
}
