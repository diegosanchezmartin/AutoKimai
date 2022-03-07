package com.firstProject.scheduleX.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class User {
    public int id;
    public String alias;
    public String username;
    public String accountNumber;
    public boolean enabled;
    public String color;
}
