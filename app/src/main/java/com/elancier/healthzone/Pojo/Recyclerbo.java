package com.elancier.healthzone.Pojo;

public class Recyclerbo
{
    String id, type, url, desc, total_reward, count, selectedval;

    public Recyclerbo(String id, String type, String url, String desc, String total_reward, String count) {
        this.id = id;
        this.type = type;
        this.url = url;
        this.desc = desc;
        this.total_reward = total_reward;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTotal_reward() {
        return total_reward;
    }

    public void setTotal_reward(String total_reward) {
        this.total_reward = total_reward;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getSelectedval() {
        return selectedval;
    }

    public void setSelectedval(String selectedval) {
        this.selectedval = selectedval;
    }
}
