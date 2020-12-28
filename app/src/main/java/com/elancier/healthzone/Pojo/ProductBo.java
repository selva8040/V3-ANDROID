package com.elancier.healthzone.Pojo;

/**
 * Created by HP PC on 21-07-2017.
 */

public class ProductBo {

    String pname;

    String pid;

    String pmrp;

    String pqty;

    public String getPtotal() {
        return ptotal;
    }

    public void setPtotal(String ptotal) {
        this.ptotal = ptotal;
    }

    public String getPqty() {
        return pqty;
    }

    public void setPqty(String pqty) {
        this.pqty = pqty;
    }

    String ptotal;

    public String getPstock() {
        return pstock;
    }

    public void setPstock(String pstock) {
        this.pstock = pstock;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPmrp() {
        return pmrp;
    }

    public void setPmrp(String pmrp) {
        this.pmrp = pmrp;
    }

    String pstock;
}
