package com.elancier.healthzone.Pojo;

import java.util.ArrayList;

/**
 * Created by HP PC on 21-07-2017.
 */

public class SubCategory {

    String sname;

    public ArrayList<ProductBo> prolist;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    String sid;
}
