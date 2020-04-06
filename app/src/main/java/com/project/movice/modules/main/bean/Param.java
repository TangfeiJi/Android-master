package com.project.movice.modules.main.bean;

import java.io.Serializable;

public class Param implements Serializable {

    private String key;
    private Object value;

    public Param() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
