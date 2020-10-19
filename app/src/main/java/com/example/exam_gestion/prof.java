package com.example.exam_gestion;

import java.io.Serializable;

public class prof  implements Serializable {
    String name ;
    private boolean isChecked = false;



    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }



    public prof(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
