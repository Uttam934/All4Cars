package com.allforcars.all4cars.Model;

public class Histroyorder_Model {

    public String order_number;
    public String product_price;
    public String product_qty;
    public String orderdate;
    public String paymettype;
    public String status;
    public String subtotoal;
    public String totdiscount;
    public String totoalpayamt;
    public String vender_id;
    public String hide_btn;



    public String gethide_btn() {
        return hide_btn;
    }
    public void sethide_btn(String hide_btn) {
        this.hide_btn = hide_btn;
    }

    public String getvender_id() {
        return vender_id;
    }
    public void setvender_id(String vender_id) {
        this.vender_id = vender_id;
    }

    public String getsubtotoal() {
        return subtotoal;
    }
    public void setsubtotoal(String subtotoal) {
        this.subtotoal = subtotoal;
    }

    public String gettotdiscount() {
        return totdiscount;
    }
    public void settotdiscount(String totdiscount) {
        this.totdiscount = totdiscount;
    }

    public String gettotoalpayamt() {
        return totoalpayamt;
    }
    public void settotoalpayamt(String totoalpayamt) {
        this.totoalpayamt = totoalpayamt;
    }


    public String getpaymettype() {
        return paymettype;
    }
    public void setpaymettype(String paymettype) {
        this.paymettype = paymettype;
    }


   public String getstatus() {
        return status;
    }
    public void setstatus(String status) {
        this.status = status;
    }


    public String getorder_number() {
        return order_number;
    }
    public void setorder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getproduct_price() {
        return product_price;
    }
    public void setproduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getproduct_qty() {
        return product_qty;
    }
    public void setproduct_qty(String product_qty) {
        this.product_qty = product_qty;
    }

    public String getorderdate() {
        return orderdate;
    }
    public void setorderdate(String orderdate) {
        this.orderdate = orderdate;
    }
}
