package com.firstProject.scheduleX.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeSheetPost {
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

    public TimeSheetPost() {}

    public TimeSheetPost(String begin, String end, int project, int activity, String description, int fixedRate, int hourlyRate, int user, boolean exported, boolean billable, String tags) {
        this.begin = begin;
        this.end = end;
        this.project = project;
        this.activity = activity;
        this.description = description;
        this.fixedRate = fixedRate;
        this.hourlyRate = hourlyRate;
        this.user = user;
        this.exported = exported;
        this.billable = billable;
        this.tags = tags;
    }
}
