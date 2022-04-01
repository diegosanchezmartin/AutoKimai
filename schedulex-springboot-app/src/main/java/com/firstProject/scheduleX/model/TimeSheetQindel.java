package com.firstProject.scheduleX.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class TimeSheetQindel {
    private int activity;
    private int project;
    private String begin;
    private String end;
    private int id;
}
