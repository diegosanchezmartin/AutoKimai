package com.firstProject.scheduleX.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class Projects {
    public String parentTitle;
    public int customer;
    public int id;
    public String name;
    public Date start;
    public Date end;
    public String comment;
    public boolean visible;
    public ArrayList<MetaField> metaFields;
    public ArrayList<Team> teams;
    public String color;
}
