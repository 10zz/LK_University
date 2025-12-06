package com.courseproject.mlkuniversity.main_ui_fragments.schedule_fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.courseproject.mlkuniversity.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ScheduleFragment extends Fragment
{

    ArrayList<ScheduleListItem> scheduleListItems = new ArrayList<>();

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        // Создается объект inflater и RecyclerView этого фрагмента.
        View rootView = inflater.inflate(R.layout.fragment_schedule, container,false);
        RecyclerView recyclerView = rootView.findViewById(R.id.list);
        // Заполняется массив значений списка.
        setInitialData();
        // Создаётся объект адаптера с передачей фрагмента и массива значений списка.
        ScheduleListAdapter adapter = new ScheduleListAdapter(rootView.getContext(), scheduleListItems);
        // Созданный адаптер задаётся для текущего фрагмента.
        recyclerView.setAdapter(adapter);
        
        return rootView;
    }


    // Тестовые значения.
    private void setInitialData()
    {
        // TODO: PHP скрипт с запросом данных согласно настройкам.
        for (int i = 0; i < 6; i++) // TODO: i - количество дней в интервале, который запросил пользователь.
        {
            ArrayList<ScheduleSubListItem> scheduleSubListItems = new ArrayList<>();
            for (int j = 0; j < 1; j++) // TODO: j - количество предметов в определённом дне.
            {
                // TODO: Здесь будет что-то наподобие scheduleSubListItems.add(new ScheduleSubListItem(response[i][j]));
                scheduleSubListItems.add(new ScheduleSubListItem("ЛПП", "Иванов ИИ", "ПИЭ11", "12:00 - 23:23", "2-333"));
                scheduleSubListItems.add(new ScheduleSubListItem("РМП", "Васин ИИ", "ПИЭ12", "13:00 - 13:23", "3-553"));
                scheduleSubListItems.add(new ScheduleSubListItem("Вареники", "Петров ИИ", "ПИ21", "15:00 - 23:23", "6-233"));
                scheduleSubListItems.add(new ScheduleSubListItem("Узбекистан", "Ермолов ИИ", "ПЭ12", "11:00 - 13:23", "1-323"));
            }
            // TODO: Здесь будет что-то наподобие scheduleListItems.add(new ScheduleListItem(convertToWeekday(date), date, scheduleSubListItems));
            if (i == 0) scheduleListItems.add(new ScheduleListItem("Понедельник", "11.12.2022", scheduleSubListItems));
            if (i == 1) scheduleListItems.add(new ScheduleListItem("Вторник", "12.12.2022", scheduleSubListItems));
            if (i == 2) scheduleListItems.add(new ScheduleListItem("Среда", "13.12.2022", scheduleSubListItems));
            if (i == 3) scheduleListItems.add(new ScheduleListItem("Четверг", "14.12.2022", scheduleSubListItems));
            if (i == 4) scheduleListItems.add(new ScheduleListItem("Gznybwf", "15.12.2022", scheduleSubListItems));
            if (i == 5) scheduleListItems.add(new ScheduleListItem("Ce,,jnf", "16.12.2022", scheduleSubListItems));
        }
    }
}