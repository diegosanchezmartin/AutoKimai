package com.firstProject.scheduleX.model;

import lombok.Data;

@Data
public class Customer {
    public int id;
    public String name;
    public String number;
    public String comment;
    public boolean visible;
    public String color;
}
