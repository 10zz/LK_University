package com.courseproject.mlkuniversity.main_ui_fragments.study_fragment;

import android.graphics.Bitmap;

public class StudyListItem
{
    private String header;
    private String description;
    private Bitmap icon;


    public StudyListItem(String header, String description, Bitmap icon)
    {
        this.header = header;
        this.description = description;
        this.icon = icon;
    }

    public String getHeader()
    {
        return this.header;
    }

    public void setHeader(String header)
    {
        this.header = header;
    }

    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }
}