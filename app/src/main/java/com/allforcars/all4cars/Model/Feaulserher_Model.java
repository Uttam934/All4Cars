package com.allforcars.all4cars.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Feaulserher_Model implements Serializable {

    private String url_link;
   private String feaul_logo;
   private String feaul_stationname;
   private String feaul_stationaddress;
   private String feaul_stationkm;
   private String fld_admin_id;
   private String fld_name;
   private String fld_email;
   private String list_phone;
   private String list_image;
   private String category_names;
    private String listproduct_id;
    private String listproduct_name;
    private String list_product_category_names;
    private String list_product_image;
    private String product_price;
    private String list_category_id;
    private String list_category_names;
    private String lattitude;
    private String longitude;
    private String branchnumber;




    ArrayList<Feaulserher_Model> pro_list;


    public ArrayList<Feaulserher_Model> getPro_list() {
        return pro_list;
    }

    public void setPro_list(ArrayList<Feaulserher_Model> pro_list) {
        this.pro_list = pro_list;
    }



    public String getbranchnumber() {
        return branchnumber;
    }

    public void setbranchnumber(String branchnumber) {
        this.branchnumber = branchnumber;
    }

    public String getlattitude() {
        return lattitude;
    }

    public void setlattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getlongitude() {
        return longitude;
    }

    public void setlongitude(String longitude) {
        this.longitude = longitude;
    }



    public String geturl_link() {
        return url_link;
    }

    public void seturl_link(String url_link) {
        this.url_link = url_link;
    }



    public String getlist_category_names() {
        return list_category_names;
    }

    public void setlist_category_names(String list_category_names) {
        this.list_category_names = list_category_names;
    }

    public String getlist_category_id() {
        return list_category_id;
    }

    public void setlist_category_id(String list_category_id) {
        this.list_category_id = list_category_id;
    }
    public String getproduct_price() {
        return product_price;
    }

    public void setproduct_price(String product_price) {
        this.product_price = product_price;
    }
    public String getlist_product_image() {
        return list_product_image;
    }

    public void setlist_product_image(String list_product_image) {
        this.list_product_image = list_product_image;
    }


    public void setlist_product_category_names(String list_product_category_names) {
        this.list_product_category_names = list_product_category_names;
    }
    public String getlist_product_category_names() {
        return list_product_category_names;
    }
    public void setlistproduct_name(String listproduct_name) {
        this.listproduct_name = listproduct_name;
    }
    public String getlistproduct_name() {
        return listproduct_name;
    }

    public void setlistproduct_id(String listproduct_id) {
        this.listproduct_id = listproduct_id;
    }
    public String getlistproduct_id() {
        return listproduct_id;
    }






    public String getlist_phone() {
        return list_phone;
    }

    public void setlist_phone(String list_phone) {
        this.list_phone = list_phone;
    }

   public String getlist_image() {
        return list_image;
    }

    public void setlist_image(String list_image) {
        this.list_image = list_image;
    }

    public String getcategory_names() {
        return category_names;
    }

    public void setcategory_names(String category_names) {
        this.category_names = category_names;
    }

    public String getfld_admin_id() {
        return fld_admin_id;
    }

    public void setfld_admin_id(String fld_admin_id) {
        this.fld_admin_id = fld_admin_id;
    }

    public String getfld_name() {
        return fld_name;
    }

    public void setfld_name(String fld_name) {
        this.fld_name = fld_name;
    }

    public String getfld_email() {
        return fld_email;
    }

    public void setfld_email(String fld_email) {
        this.fld_email = fld_email;
    }




    public String getfeaul_logo() {
        return feaul_logo;
    }

    public void setfeaul_logo(String feaul_logo) {
        this.feaul_logo = feaul_logo;
    }

    public String getfeaul_stationname() {
        return feaul_stationname;
    }

    public void setfeaul_stationname(String feaul_stationname) {
        this.feaul_stationname = feaul_stationname;
    }

    public String getfeaul_stationaddress() {
        return feaul_stationaddress;
    }

    public void setfeaul_stationaddress(String feaul_stationaddress) {
        this.feaul_stationaddress = feaul_stationaddress;
    }


    public String getfeaul_stationkm() {
        return feaul_stationkm;
    }

    public void setfeaul_stationkm(String feaul_stationkm) {
        this.feaul_stationkm = feaul_stationkm;
    }




}
