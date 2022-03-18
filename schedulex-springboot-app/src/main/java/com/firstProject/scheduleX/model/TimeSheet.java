package com.firstProject.scheduleX.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TimeSheet {
    private LocalDate begin;
    private LocalDate end;
    private int project;
    private int activity;
}
