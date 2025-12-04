package com.courseproject.mlkuniversity.main_ui_fragments.schedule_fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;

import com.courseproject.mlkuniversity.HTTPRequests;
import com.courseproject.mlkuniversity.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class ScheduleFragment extends Fragment
{
    ArrayList<ScheduleListItem> scheduleListItems = new ArrayList<>();

    View rootView;
    AutoCompleteTextView groupEditText, teacherEditText;
    Button dateIntervalStartButton, dateIntervalEndButton;


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        // TODO: Заполнения списков AutoCompleteTextView.
        // TODO: Обновление recyclerView при обновлении расписания по PHP запросу.

        // Создается объект inflater и RecyclerView этого фрагмента.
        rootView = inflater.inflate(R.layout.fragment_schedule, container,false);
        RecyclerView recyclerView = rootView.findViewById(R.id.list);

        groupEditText = rootView.findViewById(R.id.groupEditText);
        teacherEditText = rootView.findViewById(R.id.teacherEditText);
        dateIntervalStartButton = rootView.findViewById(R.id.dateIntervalStartButton);
        dateIntervalEndButton = rootView.findViewById(R.id.dateIntervalEndButton);

        dateIntervalStartButton.setOnClickListener(buttonListener);
        dateIntervalEndButton.setOnClickListener(buttonListener);

        // Заполняется массив значений списка.
        setInitialData();
        // Создаётся объект адаптера с передачей фрагмента и массива значений списка.
        ScheduleListAdapter adapter = new ScheduleListAdapter(rootView.getContext(), scheduleListItems);
        // Созданный адаптер задаётся для текущего фрагмента.
        recyclerView.setAdapter(adapter);
        
        return rootView;
    }

    Calendar calendar = Calendar.getInstance();

    View.OnClickListener buttonListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if (v.getId() == R.id.dateIntervalStartButton)
                new DatePickerDialog(rootView.getContext(),
                        dateStartPicker,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            else if (v.getId() == R.id.dateIntervalEndButton)
                new DatePickerDialog(rootView.getContext(),
                        dateEndPicker,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            else
                System.out.println("Unknown button");
        }
    };

    DatePickerDialog.OnDateSetListener dateStartPicker = new DatePickerDialog.OnDateSetListener()
    {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            dateIntervalStartButton.setText(DateUtils.formatDateTime(rootView.getContext(),
                    calendar.getTimeInMillis(),
                    DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));
        }
    };

    DatePickerDialog.OnDateSetListener dateEndPicker = new DatePickerDialog.OnDateSetListener()
    {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            dateIntervalEndButton.setText(DateUtils.formatDateTime(rootView.getContext(),
                    calendar.getTimeInMillis(),
                    DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));
        }
    };


    private void setInitialData()
    {
        /*HTTPRequests request = new HTTPRequests();
        JSONObject[] response = request.ScheduleGetRequest(this.getContext(),
                groupEditText.getText().toString(),
                teacherEditText.getText().toString(),
                dateIntervalStartButton.getText().toString(),
                dateIntervalEndButton.getText().toString());

        for (JSONObject object : response)
            try
            {
                scheduleListItems.add(new ScheduleListItem(
                        object.getString("day"),
                        object.getString("date"),
                        object.getString("subject"),
                        object.getString("teacher"),
                        object.getString("group"),
                        object.getString("start_time") + " - " + object.getString("end_time"),
                        object.getString("auditory")
                ));
            }
            catch (JSONException e)
            {
                throw new RuntimeException(e);
            }*/
        // Тестовые значения.
        scheduleListItems.add(new ScheduleListItem("Понедельник", "11.12.2022", "ЛПП", "Иванов ИИ", "ПИЭ11", "12:00 - 23:23", "2-333"));
        scheduleListItems.add(new ScheduleListItem("Вторник", "12.12.2022", "РМП", "Васин ИИ", "ПИЭ12", "13:00 - 13:23", "3-553"));
        scheduleListItems.add(new ScheduleListItem("Среда", "13.12.2022", "Вареники", "Петров ИИ", "ПИ21", "15:00 - 23:23", "6-233"));
        scheduleListItems.add(new ScheduleListItem("Четверг", "14.12.2022", "Узбекистан", "Ермолов ИИ", "ПЭ12", "11:00 - 13:23", "1-323"));
    }
}