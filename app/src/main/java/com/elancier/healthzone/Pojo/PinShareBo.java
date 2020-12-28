package com.elancier.healthzone.Pojo;

import java.io.Serializable;
import java.util.List;

public class PinShareBo implements Serializable {

    private String username;

    private List<String> pin_array;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getPin_array() {
        return pin_array;
    }

    public void setPin_array(List<String> pin_array) {
        this.pin_array = pin_array;
    }
}
