package com.firstProject.scheduleX.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSheetGet{
    public int activity;
    public int project;
    public Instant begin;
    public Instant end;
    public int id;

}