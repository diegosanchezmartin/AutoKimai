package com.firstProject.scheduleX.model;

import lombok.Data;

@Data
public class Activity {
    public int project;
    public int id;
    public String name;
    public String comment;
    public boolean visible;
    public String color;
}
