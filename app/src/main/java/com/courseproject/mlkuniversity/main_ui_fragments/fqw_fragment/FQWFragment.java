package com.courseproject.mlkuniversity.main_ui_fragments.fqw_fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.courseproject.mlkuniversity.HTTPRequests;
import com.courseproject.mlkuniversity.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FQWFragment extends Fragment
{
    ArrayList<FQWListItem> fqwListItems = new ArrayList<>();


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        // 1. Создается объект inflater и RecyclerView этого фрагмента.
        View rootView = inflater.inflate(R.layout.fragment_fqw, container,false);
        RecyclerView recyclerView = rootView.findViewById(R.id.list);
        // 2. Заполняется массив значений списка.
        setInitialData();
        // 3. Создаётся объект адаптера с передачей фрагмента и массива значений списка.
        FQWListAdapter adapter = new FQWListAdapter(rootView.getContext(), fqwListItems);
        // 4. Созданный адаптер задаётся для текущего фрагмента.
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private void setInitialData()
    {
        // TODO: PHP скрипт с запросом данных.
        HTTPRequests request = new HTTPRequests();
        JSONObject response = request.fqwPostRequest(this.getContext());

        try
        {
            JSONArray responseArr = response.getJSONArray("data");
            for (int i = 0; i < responseArr.length(); i++)
            {
                JSONObject object = responseArr.getJSONObject(i);
                fqwListItems.add(new FQWListItem(object.getString("student"),
                        object.getString("group"),
                        object.getString("department"),
                        object.getString("theme")));
            }
        }
        catch (JSONException e)
        {
            throw new RuntimeException(e);
        }

        // Тестовые значения.
        /*fqwListItems.add(new FQWListItem("Великобритания", "Лондон", "DRP", "BIG theme"));
        fqwListItems.add(new FQWListItem("Мексика", "Мехико", "DRP", "BIG theme"));
        fqwListItems.add(new FQWListItem("Норвегия", "Осло", "DRP", "BIG theme"));
        fqwListItems.add(new FQWListItem("Китай", "Пекин", "DRP", "BIG theme"));
        fqwListItems.add(new FQWListItem("Япония", "Токио", "DRP", "BIG theme"));*/
    }
}