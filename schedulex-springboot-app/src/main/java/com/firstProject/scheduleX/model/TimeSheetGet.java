package com.firstProject.scheduleX.model;

import java.util.ArrayList;
import java.util.Date;

public class TimeSheetGet{
    public int activity;
    public int project;
    public int user;
    public ArrayList<Object> tags;
    public int id;
    public Date begin;
    public Date end;
    public int duration;
    public Object description;
    public double rate;
    public double internalRate;
    public boolean exported;
    public boolean billable;
    public ArrayList<Object> metaFields;

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public ArrayList<Object> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Object> tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getInternalRate() {
        return internalRate;
    }

    public void setInternalRate(double internalRate) {
        this.internalRate = internalRate;
    }

    public boolean isExported() {
        return exported;
    }

    public void setExported(boolean exported) {
        this.exported = exported;
    }

    public boolean isBillable() {
        return billable;
    }

    public void setBillable(boolean billable) {
        this.billable = billable;
    }

    public ArrayList<Object> getMetaFields() {
        return metaFields;
    }

    public void setMetaFields(ArrayList<Object> metaFields) {
        this.metaFields = metaFields;
    }
}