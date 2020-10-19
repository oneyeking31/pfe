package com.example.exam_gestion;

public class Module {

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private boolean isChecked = false;
    String name ;
    String profresponsable;

    public Module(String name, String profresponsable) {
        this.name = name;
        this.profresponsable = profresponsable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfresponsable() {
        return profresponsable;
    }

    public void setProfresponsable(String profresponsable) {
        this.profresponsable = profresponsable;
    }
}
