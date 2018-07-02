package com.example.eduard.mindummy;

import java.io.Serializable;

/**
 * Created by eduard on 28/06/18.
 */

public class Thing implements Serializable {
    private String name;
    private String val1;
    private String val2;
    private String val3;
    private String val4;

    public Thing(String name, String val1, String val2, String val3,String val4) {
        this.name = name;
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
        this.val4 = val4;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVal3() {
        return val3;
    }

    public void setVal3(String val3) {
        this.val3 = val3;
    }

    public String getVal2() {
        return val2;
    }

    public void setVal2(String val2) {
        this.val2 = val2;
    }

    public String getVal1() {
        return val1;
    }

    public void setVal1(String val1) {
        this.val1 = val1;
    }

    public String getVal4() {
        return val4;
    }

    public void setVal4(String val4) {
        this.val4 = val4;
    }
}
