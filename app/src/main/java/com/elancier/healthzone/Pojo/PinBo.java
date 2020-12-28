package com.elancier.healthzone.Pojo;

/**
 * Created by HP PC on 07-03-2018.
 */

public class PinBo {

    String pindate;

    String pinno;
    String pintype;
    String pinstorename;
    String pinstatus;
    String pinid;
    String saleid;
    String ptype;


    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    boolean select;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDate() {
        return date;
    }

    public void settype(String ptype) {
        this.ptype = ptype;
    }

    public String gettype() {
        return ptype;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWhom() {
        return whom;
    }

    public void setWhom(String whom) {
        this.whom = whom;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    String level;
    String date;
    String whom;
    String amount;

    public String getSaleid() {
        return saleid;
    }

    public void setSaleid(String saleid) {
        this.saleid = saleid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    String username;

    public String getPindate() {
        return pindate;
    }

    public void setPindate(String pindate) {
        this.pindate = pindate;
    }

    public String getPinno() {
        return pinno;
    }

    public void setPinno(String pinno) {
        this.pinno = pinno;
    }

    public String getPintype() {
        return pintype;
    }

    public void setPintype(String pintype) {
        this.pintype = pintype;
    }

    public String getPinstorename() {
        return pinstorename;
    }

    public void setPinstorename(String pinstorename) {
        this.pinstorename = pinstorename;
    }

    public String getPinstatus() {
        return pinstatus;
    }

    public void setPinstatus(String pinstatus) {
        this.pinstatus = pinstatus;
    }

    public String getPinid() {
        return pinid;
    }

    public void setPinid(String pinid) {
        this.pinid = pinid;
    }

    public String getPindatetime() {
        return pindatetime;
    }

    public void setPindatetime(String pindatetime) {
        this.pindatetime = pindatetime;
    }

    String pindatetime;

}
