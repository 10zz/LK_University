package com.courseproject.mlkuniversity.main_ui_fragments.schedule_fragment;

public class ScheduleListItem
{
    private String day, date, subject, teacher, group, timeInterval, auditory;


    public ScheduleListItem(String day, String date, String subject, String teacher, String group,
                         String timeInterval, String auditory)
    {
        this.day = day;
        this.date = date;
        this.subject = subject;
        this.teacher = teacher;
        this.group = group;
        this.timeInterval = timeInterval;
        this.auditory = auditory;
    }


    public String getAuditory() {
        return auditory;
    }

    public void setAuditory(String auditory) {
        this.auditory = auditory;
    }

    public String getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}