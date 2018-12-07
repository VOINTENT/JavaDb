/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.andrey.domain;

import java.util.Date;

/**
 *
 * @author AEGrishin
 */
public class Registration {
    private String name;
    private Date date;
    private Integer countPositions;

    public Registration(String name, Date date, Integer countPositions) {
        this.name = name;
        this.date = date;
        this.countPositions = countPositions;
    }

    public Registration() {
        this.name = null;
        this.date = null;
        this.countPositions = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getCountPositions() {
        return countPositions;
    }

    public void setCountPositions(Integer countPositions) {
        this.countPositions = countPositions;
    }
}
