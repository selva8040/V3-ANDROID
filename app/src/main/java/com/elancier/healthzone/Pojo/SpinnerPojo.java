package com.elancier.healthzone.Pojo;

import java.util.ArrayList;

/**
 * Created by Manikandan on 7/19/2017.
 */
public class SpinnerPojo {

    private String category;

    private String subcat;
    private String pname;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    String categoryname;

    public ArrayList<SubCategory> subcatlist;

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcat() {
        return subcat;
    }

    public void setSubcat(String subcat) {
        this.subcat = subcat;
    }

}
