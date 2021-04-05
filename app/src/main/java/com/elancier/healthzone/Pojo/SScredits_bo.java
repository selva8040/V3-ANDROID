package com.elancier.healthzone.Pojo;

public class SScredits_bo {
    String date, open, username, reduce,comments,tid,transaction,bank;

    public SScredits_bo(String id, String date, String visual_time, String username, String comments,String tid,
                        String transaction,String bank) {
        this.date = id;
        this.open = date;
        this.username = visual_time;
        this.reduce = username;
        this.comments= comments;
        this.tid=tid;
        this.transaction=transaction;
        this.bank=bank;

    }

    public String getdate() {
        return date;
    }

    public String gettid() {
        return tid;
    }


    public String gettransaction() {
        return transaction;
    }

    public String getbank() {
        return bank;
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
