package com.courseproject.mlkuniversity.main_ui_fragments.schedule_fragment;

import java.util.ArrayList;

public class ScheduleListItem
{
    private String day, date;
    private ArrayList<ScheduleSubListItem> scheduleSubListItems = new ArrayList<>();


    public ScheduleListItem(String day, String date, ArrayList<ScheduleSubListItem> scheduleSubListItems)
    {
        this.day = day;
        this.date = date;
        this.scheduleSubListItems = scheduleSubListItems;
    }

    public ArrayList<ScheduleSubListItem> getScheduleSubListItems() {
        return scheduleSubListItems;
    }

    public void setScheduleSubListItems(ArrayList<ScheduleSubListItem> scheduleSubListItems) {
        this.scheduleSubListItems = scheduleSubListItems;
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