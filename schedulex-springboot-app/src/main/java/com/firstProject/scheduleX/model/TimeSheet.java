package com.firstProject.scheduleX.model;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TimeSheet {
    private LocalDate begin;
    private LocalDate end;
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
