package com.courseproject.mlkuniversity.main_ui_fragments.study_fragment;

public class StudyListItem
{
    private String header;
    private String description;
    // TODO: переменная, хранящая изображение для иконки.


    public StudyListItem(String header, String description)
    {
        this.header = header;
        this.description = description;
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


    // TODO: Геттер и сеттер для переменной, хранящей иконку.
}