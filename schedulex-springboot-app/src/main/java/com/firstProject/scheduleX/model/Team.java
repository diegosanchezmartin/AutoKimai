package com.firstProject.scheduleX.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Team {
    public Teamlead teamlead;
    public ArrayList<User> users;
    public int id;
    public String name;
    public ArrayList<Member> members;
    public ArrayList<Customer> customers;
    public ArrayList<Project> projects;
    public ArrayList<Activity> activities;
    public String color;
}
