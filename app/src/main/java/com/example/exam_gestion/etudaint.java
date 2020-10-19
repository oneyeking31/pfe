package com.example.exam_gestion;

public class etudaint {

    private String name ;
    private int groupeid;
    private  int id ;
    private int presence ;
    private boolean isChecked = false;


    public etudaint(String name) {
        this.name = name;
        this.id = id;
    }
    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGroupe() {
        return groupeid;
    }

    public void setGroupe(int groupeid) {
        this.groupeid = groupeid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int isPresence() {
        return presence;
    }

    public void setPresence(int presence) {
        this.presence = presence;
    }
}
