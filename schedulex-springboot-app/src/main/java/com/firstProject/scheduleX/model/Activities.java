package com.firstProject.scheduleX.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Activities {
    public String parentTitle;
    public int project;
    public int id;
    public String name;
    public String comment;
    public boolean visible;
    public ArrayList<MetaField> metaFields;
    public ArrayList<Team> teams;
    public String color;
}
