package com.elancier.healthzone.Pojo;

import java.util.ArrayList;

/**
 * Created by HP PC on 24-07-2017.
 */

public class GeneologyBo {

    String tusername;

    String tname;

    String tdesign;

    String tibv;

    public ArrayList<Genosubbo> gensublist;

    public String getTgbv() {
        return tgbv;
    }

    public void setTgbv(String tgbv) {
        this.tgbv = tgbv;
    }

    public String getTusername() {
        return tusername;
    }

    public void setTusername(String tusername) {
        this.tusername = tusername;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTdesign() {
        return tdesign;
    }

    public void setTdesign(String tdesign) {
        this.tdesign = tdesign;
    }

    public String getTibv() {
        return tibv;
    }

    public void setTibv(String tibv) {
        this.tibv = tibv;
    }

    String tgbv;
}
