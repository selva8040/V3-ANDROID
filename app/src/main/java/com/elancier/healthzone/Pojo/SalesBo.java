package com.elancier.healthzone.Pojo;

import java.util.ArrayList;

/**
 * Created by HP PC on 25-07-2017.
 */

public class SalesBo {

    String sname;

    String sdate;

    String sprice;

    String smobile;

    String totbonus;

    String tottds;

    String totsc;

    String tottr;
    String pqid;
    String storeid;
    String amount;
    String bv;
    String cusid;

    public String getPqid() {
        return pqid;
    }

    public void setPqid(String pqid) {
        this.pqid = pqid;
    }

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBv() {
        return bv;
    }

    public void setBv(String bv) {
        this.bv = bv;
    }

    public String getCusid() {
        return cusid;
    }

    public void setCusid(String cusid) {
        this.cusid = cusid;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    String customer;

    public String getTotbonus() {
        return totbonus;
    }

    public void setTotbonus(String totbonus) {
        this.totbonus = totbonus;
    }

    public String getTottds() {
        return tottds;
    }

    public void setTottds(String tottds) {
        this.tottds = tottds;
    }

    public String getTotsc() {
        return totsc;
    }

    public void setTotsc(String totsc) {
        this.totsc = totsc;
    }

    public String getTottr() {
        return tottr;
    }

    public void setTottr(String tottr) {
        this.tottr = tottr;
    }

    public String getTotreciev() {
        return totreciev;
    }

    public void setTotreciev(String totreciev) {
        this.totreciev = totreciev;
    }

    String totreciev;

    public ArrayList<OverallBonusSubBo> bonussublist;

    public boolean isSaleselect() {
        return saleselect;
    }

    public void setSaleselect(boolean saleselect) {
        this.saleselect = saleselect;
    }

    boolean saleselect;

    public ArrayList<ProductBo> salesprodicts;

    public String getSmobile() {
        return smobile;
    }

    public void setSmobile(String smobile) {
        this.smobile = smobile;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getSprice() {
        return sprice;
    }

    public void setSprice(String sprice) {
        this.sprice = sprice;
    }


}
