package com.elancier.healthzone.Pojo;

/**
 * Created by Manikandan on 6/21/2017.
 */
public class ReportsPojo {

    private  String username;
    private  String userid;
    private  String detail;
    private  String date;
    private  String amount;
    private  String plan;
    private  String percentage;
    private  String name;
    private  String cusid;

    public String getCusid() {
        return cusid;
    }

    public void setCusid(String cusid) {
        this.cusid = cusid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    private  String mobile;

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String frombank;
    String tobank;
    String tid;

    public String getAptime() {
        return aptime;
    }

    public void setAptime(String aptime) {
        this.aptime = aptime;
    }

    public String getFrombank() {
        return frombank;
    }

    public void setFrombank(String frombank) {
        this.frombank = frombank;
    }

    public String getTobank() {
        return tobank;
    }

    public void setTobank(String tobank) {
        this.tobank = tobank;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    String aptime;

    double bonusamt;

    public double getBonusamt() {
        return bonusamt;
    }

    public void setBonusamt(double bonusamt) {
        this.bonusamt = bonusamt;
    }

    public double getNetamt() {
        return netamt;
    }

    public void setNetamt(double netamt) {
        this.netamt = netamt;
    }

    double netamt;

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    String bonus;

    public String getPqid() {
        return pqid;
    }

    public void setPqid(String pqid) {
        this.pqid = pqid;
    }

    public String getIbv() {
        return ibv;
    }

    public void setIbv(String ibv) {
        this.ibv = ibv;
    }



    private String pqid;
    String ibv;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
