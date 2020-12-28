package com.elancier.healthzone.Pojo;

public class Feedbackbo {

    String id, type, comment, username, name,time, whome, whomename, points;

    public Feedbackbo(String id, String type, String comment,String points,String time) {
        this.id = id;
        this.type = type;
        this.comment = comment;
        this.points = points;
        this.time = time;



    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String gettype() {
        return type;
    }

    public void settype(String type) {
        this.type = type;
    }

    public String gettime() {
        return time;
    }

    public void settime(String time) {
        this.time = time;
    }


    public String getcomment() {
        return comment;
    }

    public void setcomment(String comment) {
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWhome() {
        return whome;
    }

    public void setWhome(String whome) {
        this.whome = whome;
    }

    public String getWhomename() {
        return whomename;
    }

    public void setWhomename(String whomename) {
        this.whomename = whomename;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
