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
        scheduleListItems.add(new ScheduleListItem("Понедельник", "11.12.2022", "ЛПП", "Иванов ИИ", "ПИЭ11", "12:00 - 23:23", "2-333"));
        scheduleListItems.add(new ScheduleListItem("Вторник", "12.12.2022", "РМП", "Васин ИИ", "ПИЭ12", "13:00 - 13:23", "3-553"));
        scheduleListItems.add(new ScheduleListItem("Среда", "13.12.2022", "Вареники", "Петров ИИ", "ПИ21", "15:00 - 23:23", "6-233"));
        scheduleListItems.add(new ScheduleListItem("Четверг", "14.12.2022", "Узбекистан", "Ермолов ИИ", "ПЭ12", "11:00 - 13:23", "1-323"));
    }
}