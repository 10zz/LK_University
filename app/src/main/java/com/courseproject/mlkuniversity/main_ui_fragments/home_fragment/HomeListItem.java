package com.courseproject.mlkuniversity.main_ui_fragments.home_fragment;

public class HomeListItem
{
    private String title;
    private String description;


    public HomeListItem(String name, String description)
    {
        this.title=name;
        this.description = description;
    }


    public String getTitle()
    {
        return this.title;
    }


    public void setTitle(String title)
    {
        this.title = title;
    }


    public String getDescription()
    {
        return this.description;
    }


    public void setDescription(String description)
    {
        this.description = description;
    }
}