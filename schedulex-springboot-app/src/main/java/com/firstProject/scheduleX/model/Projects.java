package com.firstProject.scheduleX.model;

import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;

@Data
public class Projects {
    public String parentTitle;
    public int customer;
    public int id;
    public String name;
    public Instant start;
    public Instant end;
    public String comment;
    public boolean visible;
    public ArrayList<MetaField> metaFields;
    public ArrayList<Team> teams;
    public String color;
}
