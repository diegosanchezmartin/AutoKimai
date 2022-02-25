package com.firstProject.scheduleX.TimeSheet;

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

    public TimeSheet() {
    }
    public TimeSheet(String begin, String end, int project, int activity, String description, int fixedRate, int hourlyRate, int user, boolean exported, boolean billable, String tags) {
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

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFixedRate() {
        return fixedRate;
    }

    public void setFixedRate(int fixedRate) {
        this.fixedRate = fixedRate;
    }

    public int getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(int hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "TimeSheet{" +
                "begin='" + begin + '\'' +
                ", end='" + end + '\'' +
                ", project='" + project + '\'' +
                ", activity='" + activity + '\'' +
                ", description='" + description + '\'' +
                ", fixedRate='" + fixedRate + '\'' +
                ", hourlyRate='" + hourlyRate + '\'' +
                ", user='" + user + '\'' +
                ", exported='" + exported + '\'' +
                ", billable='" + billable + '\'' +
                ", tags='" + tags + '\'' +
                '}';
    }
}
