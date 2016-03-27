package com.tide.bean;

public class ProductColor {
    private Integer id;

    private Integer productid;

    private String color;

    private String colorimg;

    private String colorcard;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColorimg() {
        return colorimg;
    }

    public void setColorimg(String colorimg) {
        this.colorimg = colorimg;
    }

    public String getColorcard() {
        return colorcard;
    }

    public void setColorcard(String colorcard) {
        this.colorcard = colorcard;
    }
}