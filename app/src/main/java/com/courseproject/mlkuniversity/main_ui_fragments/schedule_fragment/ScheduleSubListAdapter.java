package com.courseproject.mlkuniversity.main_ui_fragments.schedule_fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.courseproject.mlkuniversity.R;

import java.util.List;

public class ScheduleSubListAdapter extends RecyclerView.Adapter<ScheduleSubListAdapter.ViewHolder>
{
    private final LayoutInflater inflater;
    private final List<ScheduleSubListItem> scheduleSubListItems;


    ScheduleSubListAdapter(Context context, List<ScheduleSubListItem> scheduleSubListItems)
    {
        this.scheduleSubListItems = scheduleSubListItems;
        this.inflater = LayoutInflater.from(context);
    }


    // Возвращает объект ViewHolder, который будет хранить данные по одному объекту ScheduleSubListItem.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.list_sub_item_schedule, parent, false);
        return new ViewHolder(view);
    }


    // Выполняет привязку объекта ViewHolder к объекту ScheduleSubListItem по определенной позиции.
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        ScheduleSubListItem scheduleSubListItem = scheduleSubListItems.get(position);
        holder.subjectTitleText.setText(scheduleSubListItem.getSubject());
        holder.timeIntervalText.setText(scheduleSubListItem.getTimeInterval());
        holder.teacherText.setText(scheduleSubListItem.getTeacher());
        holder.auditoryText.setText(scheduleSubListItem.getAuditory());
        holder.groupText.setText(scheduleSubListItem.getGroup());
    }


    // Возвращает количество объектов в списке.
    @Override
    public int getItemCount()
    {
        return scheduleSubListItems.size();
    }


    // Использует определенные в list_item_schedule.xml элементы View.
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        final TextView subjectTitleText, timeIntervalText, teacherText, auditoryText, groupText;


        ViewHolder(View view)
        {
            super(view);
            subjectTitleText = view.findViewById(R.id.subjectTitleText);
            timeIntervalText = view.findViewById(R.id.timeIntervalText);
            teacherText = view.findViewById(R.id.teacherText);
            auditoryText = view.findViewById(R.id.auditoryText);
            groupText = view.findViewById(R.id.groupText);
        }
    }
}
