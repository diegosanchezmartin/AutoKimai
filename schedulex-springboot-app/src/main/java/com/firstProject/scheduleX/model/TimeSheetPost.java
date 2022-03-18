package com.firstProject.scheduleX.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TimeSheetPost {
    private String begin;
    private String end;
    private int project;
    private int activity;
}
