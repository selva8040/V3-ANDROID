package com.elancier.healthzone.Pojo;

/**
 * Created by Manikandan on 7/18/2017.
 */
public class StockPojo {

    private String productname;
    private String purchase;
    private int sales;
    private int inhand;

    public int getInhand() {
        return inhand;
    }

    public void setInhand(int inhand) {
        this.inhand = inhand;
    }


    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getPurchase() {
        return purchase;
    }

    public void setPurchase(String purchase) {
        this.purchase = purchase;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

}
