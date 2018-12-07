/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.andrey.domain;

/**
 *
 * @author AEGrishin
 */
public class Seller {
    private String name;     
    private String area;        
    private String nameProduct;   
    private Integer price;
    private Integer count;

    public Seller(String name, String area, String nameProduct, Integer price, Integer count) {
        this.name = name;
        this.area = area;
        this.nameProduct = nameProduct;
        this.price = price;
        this.count = count;
    }

    public Seller() {
        this.name = null;
        this.area = null;
        this.nameProduct = null;
        this.price = null;
        this.count = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
