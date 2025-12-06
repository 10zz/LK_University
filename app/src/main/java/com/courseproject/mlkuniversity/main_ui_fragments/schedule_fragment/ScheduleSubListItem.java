package com.courseproject.mlkuniversity.main_ui_fragments.schedule_fragment;

public class ScheduleSubListItem
{
    private String subject, teacher, group, timeInterval, auditory;


    public ScheduleSubListItem(String subject, String teacher, String group, String timeInterval, String auditory)
    {
        this.subject = subject;
        this.teacher = teacher;
        this.group = group;
        this.timeInterval = timeInterval;
        this.auditory = auditory;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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
}