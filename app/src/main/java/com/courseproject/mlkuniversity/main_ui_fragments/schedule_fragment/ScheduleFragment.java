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
    Button dateIntervalStartButton, dateIntervalEndButton;
    AutoCompleteTextView groupEditText, teacherEditText;
    View rootView;
    ScheduleListAdapter adapter;
    Calendar pickedDate = Calendar.getInstance();


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        // TODO: Календарь-диалог кнопок не работает и список не отрисовывается полностью
        // 1. Создается объект inflater и RecyclerView этого фрагмента.
        rootView = inflater.inflate(R.layout.fragment_schedule, container,false);

        groupEditText = rootView.findViewById(R.id.groupEditText);
        teacherEditText = rootView.findViewById(R.id.teacherEditText);
        dateIntervalStartButton = rootView.findViewById(R.id.dateIntervalStartButton);
        dateIntervalEndButton = rootView.findViewById(R.id.dateIntervalEndButton);
        dateIntervalStartButton.setOnClickListener(buttonListener);
        dateIntervalEndButton.setOnClickListener(buttonListener);

        RecyclerView recyclerView = rootView.findViewById(R.id.list);
        // 2. Заполняется массив значений списка.
        // 3. Создаётся объект адаптера с передачей фрагмента и массива значений списка.
        adapter = new ScheduleListAdapter(rootView.getContext(), scheduleListItems);
        recyclerView.setNestedScrollingEnabled(false);
        // 4. Созданный адаптер задаётся для текущего фрагмента.
        recyclerView.setAdapter(adapter);
        
        return rootView;
    }

    View.OnClickListener buttonListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            new DatePickerDialog(rootView.getContext(),
                    new DatePickerDialog.OnDateSetListener()
                    {
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                        {
                            pickedDate.set(Calendar.YEAR, year);
                            pickedDate.set(Calendar.MONTH, monthOfYear);
                            pickedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                            Button curButton = (Button) v;
                            curButton.setText(DateUtils.formatDateTime(rootView.getContext(),
                                    pickedDate.getTimeInMillis(),
                                    DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_NUMERIC_DATE ));

                            if (dateIntervalStartButton.getText().toString().substring(0, 1).matches("[0-9].*") &&
                                    dateIntervalEndButton.getText().toString().substring(0, 1).matches("[0-9].*"))
                            {
                                requestData(dateIntervalStartButton.getText().toString(), dateIntervalEndButton.getText().toString());
                                adapter.notifyDataSetChanged();
                            }
                        }
                    },
                    pickedDate.get(Calendar.YEAR),
                    pickedDate.get(Calendar.MONTH),
                    pickedDate.get(Calendar.DAY_OF_MONTH))
                    .show();
        }
    };

    // Тестовые значения.
    private void requestData(String startDate, String endDate)
    {
        scheduleListItems.clear();
        scheduleListItems.add(new ScheduleListItem(" ", " ", null));

        HTTPRequests request = new HTTPRequests();
        JSONObject[] response = request.ScheduleGetRequest(this.getContext(), groupEditText.getText().toString(), teacherEditText.getText().toString(), startDate, endDate);
                for (int i = 0; i < response.length; i++)
                    try {
                        boolean containsFlag = false;
                        for (ScheduleListItem item : scheduleListItems)
                            if (item.getDate().equals(response[i].getString("day"))) containsFlag = true;
                        if (!containsFlag)
                            scheduleListItems.add(new ScheduleListItem(
                                    response[i].getString("day"),
                                    response[i].getString("day"),
                                    null));

                        ArrayList<ScheduleSubListItem> scheduleSubListItems = new ArrayList<>();

                        for (JSONObject object : response)
                            if (object.getString("day").equals(scheduleListItems.get(i).getDay()))
                                scheduleSubListItems.add(new ScheduleSubListItem(object.getString("subject"),
                                        object.getString("teacher"),
                                        object.getString("group"),
                                        object.getString("start_time") + " - " + object.getString("end_time"),
                                        object.getString("room")));

                        scheduleListItems.get(i).setScheduleSubListItems(scheduleSubListItems);
                    }
                    catch (JSONException e)
                    {
                        throw new RuntimeException(e);
                    }
                // Тестовые данные.
                    /*for (int i = 0; i < 6; i++)
                    {
                        ArrayList<ScheduleSubListItem> scheduleSubListItems = new ArrayList<>();
                        for (int j = 0; j < 1; j++)
                        {
                            scheduleSubListItems.add(new ScheduleSubListItem("ЛПП", "Иванов ИИ", "ПИЭ11", "12:00 - 23:23", "2-333"));
                            scheduleSubListItems.add(new ScheduleSubListItem("РМП", "Васин ИИ", "ПИЭ12", "13:00 - 13:23", "3-553"));
                            scheduleSubListItems.add(new ScheduleSubListItem("Вареники", "Петров ИИ", "ПИ21", "15:00 - 23:23", "6-233"));
                            scheduleSubListItems.add(new ScheduleSubListItem("Узбекистан", "Ермолов ИИ", "ПЭ12", "11:00 - 13:23", "1-323"));
                        }
                        if (i == 0) scheduleListItems.add(new ScheduleListItem("Понедельник", "11.12.2022", scheduleSubListItems));
                        if (i == 1) scheduleListItems.add(new ScheduleListItem("Вторник", "12.12.2022", scheduleSubListItems));
                        if (i == 2) scheduleListItems.add(new ScheduleListItem("Среда", "13.12.2022", scheduleSubListItems));
                        if (i == 3) scheduleListItems.add(new ScheduleListItem("Четверг", "14.12.2022", scheduleSubListItems));
                        if (i == 4) scheduleListItems.add(new ScheduleListItem("Gznybwf", "15.12.2022", scheduleSubListItems));
                        if (i == 5) scheduleListItems.add(new ScheduleListItem("Ce,,jnf", "16.12.2022", scheduleSubListItems));
                    }*/
    }
}