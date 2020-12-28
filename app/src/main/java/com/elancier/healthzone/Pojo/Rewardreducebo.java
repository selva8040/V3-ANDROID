package com.elancier.healthzone.Pojo;

public class Rewardreducebo {
    String date, open, username, reduce,comments;

    public Rewardreducebo(String id, String date, String visual_time, String username,String comments) {
        this.date = id;
        this.open = date;
        this.username = visual_time;
        this.reduce = username;
        this.comments= comments;

    }

    public String getdate() {
        return date;
    }


    public String getopen() {
        return open;
    }

    public void setDate(String date) {
        this.date = date;
    }




    public String getUsername() {
        return username;
    }
    public String getcomments() {
        return comments;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getreduce() {
        return reduce;
    }
}
