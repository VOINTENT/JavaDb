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
public class Supplier {
    private String type;
    private String supplier;

    public Supplier(String type, String supplier) {
        this.type = type;
        this.supplier = supplier;
    }

    public Supplier() {
        this.type = null;
        this.supplier = null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
    
    
}
