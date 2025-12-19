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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;

import com.courseproject.mlkuniversity.HTTPRequests;
import com.courseproject.mlkuniversity.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class ScheduleFragment extends Fragment
{
    ArrayList<ScheduleListItem> scheduleListItems = new ArrayList<>();
    Button dateIntervalStartButton, dateIntervalEndButton;
    AutoCompleteTextView groupEditText, teacherEditText;
    View rootView;
    ScheduleListAdapter recycleViewAdapter;
    Calendar pickedDate = Calendar.getInstance();


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        // 1. Создается объект inflater и RecyclerView этого фрагмента.
        rootView = inflater.inflate(R.layout.fragment_schedule, container,false);
        RecyclerView recyclerView = rootView.findViewById(R.id.list);

        // 2. Привязка View-переменных.
        groupEditText = rootView.findViewById(R.id.groupEditText);
        teacherEditText = rootView.findViewById(R.id.teacherEditText);
        dateIntervalStartButton = rootView.findViewById(R.id.dateIntervalStartButton);
        dateIntervalEndButton = rootView.findViewById(R.id.dateIntervalEndButton);
        dateIntervalStartButton.setOnClickListener(dateButtonListener);
        dateIntervalEndButton.setOnClickListener(dateButtonListener);

        // 3. Создаётся объект адаптера с передачей фрагмента и массива значений списка.
        recycleViewAdapter = new ScheduleListAdapter(rootView.getContext(), scheduleListItems);

        // 4. Созданный адаптер задаётся для текущего фрагмента.
        recyclerView.setAdapter(recycleViewAdapter);

        // 5. Заполнение списков-подсказок для текстовых полей teacherEditText и groupEditText.
        HTTPRequests request = new HTTPRequests();
        JSONObject response = request.JSONGetRequest(rootView.getContext(), getString(R.string.teachers_request));
        fillAutoCompleteTextViewAdapterFromJSON(response, "teacher_name", teacherEditText);
        response = request.JSONGetRequest(rootView.getContext(), getString(R.string.group_request));
        fillAutoCompleteTextViewAdapterFromJSON(response, "group_name", groupEditText);

        // 6. Выпадение списков-подсказок при нажатии на teacherEditText и groupEditText.
        teacherEditText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                teacherEditText.showDropDown();
            }
        });
        groupEditText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                groupEditText.showDropDown();
            }
        });

        return rootView;
    }

    // Метод для заполнения списков-подсказок для AutoCompleteTextView из поля field объекта JSONObject.
    void fillAutoCompleteTextViewAdapterFromJSON(JSONObject responseObject, String field, AutoCompleteTextView textView)
    {
        try
        {
            ArrayList<String> list = new ArrayList<>();
            JSONArray responseArr = responseObject.getJSONArray("data");
            for (int i = 0; i < responseObject.getInt("count"); i++)
            {
                JSONObject object = responseArr.getJSONObject(i);
                list.add(object.getString(field).toString());
            }
            textView.setAdapter(new ArrayAdapter<String>(rootView.getContext(),  android.R.layout.simple_spinner_dropdown_item, list));
        }
        catch (JSONException e)
        {}
    }

    View.OnClickListener dateButtonListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            new DatePickerDialog(rootView.getContext(),
                    new DatePickerDialog.OnDateSetListener()
                    {
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                        {
                            // 1. Получение выбранной даты.
                            pickedDate.set(Calendar.YEAR, year);
                            pickedDate.set(Calendar.MONTH, monthOfYear);
                            pickedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                            // 2. Замена текста кнопки на текст выбранной даты.
                            Button curButton = (Button) v;
                            curButton.setText(DateUtils.formatDateTime(rootView.getContext(),
                                    pickedDate.getTimeInMillis(),
                                    DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_NUMERIC_DATE ));

                            // Если на кнопках dateIntervalStartButton и dateIntervalEndButton
                            // первый символ является цифрой (...на обеих кнопках выбраны даты).
                            if (dateIntervalStartButton.getText().toString().substring(0, 1).matches("[0-9].*") &&
                                    dateIntervalEndButton.getText().toString().substring(0, 1).matches("[0-9].*"))
                            {
                                // 3. Вызов метода заполнения scheduleListItems с передачей выбранных дат.
                                requestData(dateIntervalStartButton.getText().toString(), dateIntervalEndButton.getText().toString());
                                // 4. Обновление адаптера RecyclerView.
                                recycleViewAdapter.notifyDataSetChanged();
                            }
                        }
                    },
                    pickedDate.get(Calendar.YEAR),
                    pickedDate.get(Calendar.MONTH),
                    pickedDate.get(Calendar.DAY_OF_MONTH))
                    .show();
        }
    };


    private void requestData(String startDate, String endDate)
    {
        // 1. Очистка scheduleListItems.
        scheduleListItems.clear();

        // 2. Запрос расписания с передачей интервала времени, преподавателя и группы.
        HTTPRequests request = new HTTPRequests();
        // TODO: обработка ошибок
        JSONObject response = request.ScheduleGetRequest(rootView.getContext(), groupEditText.getText().toString(), teacherEditText.getText().toString(), startDate, endDate);
        try
        {
            // Запись JSON массива responseDataArray из полученного ответа сервера.
            JSONObject[] responseDataArray = new JSONObject[response.getInt("count")];
            for (int i = 0; i < response.getJSONArray("data").length(); i++)
                responseDataArray[i] = response.getJSONArray("data").getJSONObject(i);


            for (int i = 0; i < responseDataArray.length; i++)
            {
                // Проверка уникальности даты в scheduleListItems.
                boolean containsFlag = false;
                for (ScheduleListItem item : scheduleListItems)
                    if (item.getDate().equals(responseDataArray[i].getString("date")))
                        containsFlag = true;
                // Если дата не найдена scheduleListItems, она заносится в него.
                if (!containsFlag)
                {
                    // Парсинг даты (второй аргумент)
                    // в день недели в качестве первого аргумента и null вместо списка.
                    scheduleListItems.add(new ScheduleListItem(
                            LocalDate.parse(responseDataArray[i].getString("date"))
                                    .getDayOfWeek()
                                    .getDisplayName(TextStyle.FULL, new Locale("ru")),
                            responseDataArray[i].getString("date"),
                            null));
                }
                ArrayList<ScheduleSubListItem> scheduleSubListItems = new ArrayList<>();

                for (JSONObject object : responseDataArray)
                    if (object.getString("date").equals(scheduleListItems.get(i).getDate()))
                        scheduleSubListItems.add(new ScheduleSubListItem(object.getString("subject"),
                                object.getString("teacher"),
                                object.getString("group"),
                                object.getString("start_time") + " - " + object.getString("end_time"),
                                object.getString("room")));

                scheduleListItems.get(i).setScheduleSubListItems(scheduleSubListItems);
            }
        }
        catch (JSONException e)
        {}
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