package com.firstProject.scheduleX.model;

import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;

@Data
public class TimeSheetGet{
    public int activity;
    public int project;
    public int user;
    public ArrayList<Object> tags;
    public int id;
    public Instant begin;
    public Instant end;
    public int duration;
    public Object description;
    public double rate;
    public double internalRate;
    public boolean exported;
    public boolean billable;
    public ArrayList<Object> metaFields;

}