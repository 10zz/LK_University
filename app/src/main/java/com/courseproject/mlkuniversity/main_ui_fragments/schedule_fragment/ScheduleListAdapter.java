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

public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.ViewHolder>
{
    private final LayoutInflater inflater;
    private final List<ScheduleListItem> scheduleListItems;


    ScheduleListAdapter(Context context, List<ScheduleListItem> scheduleListItems)
    {
        this.scheduleListItems = scheduleListItems;
        this.inflater = LayoutInflater.from(context);
    }


    // Возвращает объект ViewHolder, который будет хранить данные по одному объекту ScheduleListItem.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.list_item_schedule, parent, false);
        return new ViewHolder(view);
    }


    // Выполняет привязку объекта ViewHolder к объекту ScheduleListItem по определенной позиции.
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        ScheduleListItem scheduleListItem = scheduleListItems.get(position);
        holder.dayText.setText(scheduleListItem.getDay());
        holder.dateText.setText(scheduleListItem.getDate());

        ScheduleSubListAdapter subAdapter = new ScheduleSubListAdapter(inflater.getContext(), scheduleListItem.getScheduleSubListItems());
        holder.SubItemRecycleView.setAdapter(subAdapter);

    }


    // Возвращает количество объектов в списке.
    @Override
    public int getItemCount()
    {
        return scheduleListItems.size();
    }


    // Использует определенные в list_item_schedule.xml элементы View.
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        final TextView dayText, dateText;
        final RecyclerView SubItemRecycleView;

        ViewHolder(View view)
        {
            super(view);
            dayText = view.findViewById(R.id.dayText);
            dateText = view.findViewById(R.id.dateText);
            SubItemRecycleView = view.findViewById(R.id.list);
        }
    }
}