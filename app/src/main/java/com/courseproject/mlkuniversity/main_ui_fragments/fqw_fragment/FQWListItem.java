package com.courseproject.mlkuniversity.main_ui_fragments.fqw_fragment;

public class FQWListItem
{
    private String studentName, group, department, theme;
    // TODO: переменная, хранящая изображение для иконки.


    public FQWListItem(String studentName, String group, String department, String theme) {
        this.studentName = studentName;
        this.group = group;
        this.department = department;
        this.theme = theme;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    // TODO: Геттер и сеттер для переменной, хранящей иконку.
}