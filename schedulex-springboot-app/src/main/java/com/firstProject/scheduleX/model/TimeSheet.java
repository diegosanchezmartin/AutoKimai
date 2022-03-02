package com.firstProject.scheduleX.model;

import lombok.Data;

@Data
public class TimeSheet {
    private String begin;
    private String end;
    private int project;
    private int activity;
    private String description;
    private int fixedRate;
    private int hourlyRate;
    private int user;
    private boolean exported;
    private boolean billable;
    private String tags;
}
