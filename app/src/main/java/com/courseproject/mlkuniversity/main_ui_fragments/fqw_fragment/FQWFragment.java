package com.courseproject.mlkuniversity.main_ui_fragments.fqw_fragment;

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
        // Создается объект inflater и RecyclerView этого фрагмента.
        View rootView = inflater.inflate(R.layout.fragment_fqw, container,false);
        RecyclerView recyclerView = rootView.findViewById(R.id.list);
        // Заполняется массив значений списка.
        setInitialData();
        // Создаётся объект адаптера с передачей фрагмента и массива значений списка.
        FQWListAdapter adapter = new FQWListAdapter(rootView.getContext(), fqwListItems);
        // Созданный адаптер задаётся для текущего фрагмента.
        recyclerView.setAdapter(adapter);

        return rootView;
    }


    // Тестовые значения.
    private void setInitialData()
    {
        // TODO: PHP скрипт с запросом данных.
        fqwListItems.add(new FQWListItem("Великобритания", "Лондон", "DRP", "BIG theme"));
        fqwListItems.add(new FQWListItem("Мексика", "Мехико", "DRP", "BIG theme"));
        fqwListItems.add(new FQWListItem("Норвегия", "Осло", "DRP", "BIG theme"));
        fqwListItems.add(new FQWListItem("Китай", "Пекин", "DRP", "BIG theme"));
        fqwListItems.add(new FQWListItem("Япония", "Токио", "DRP", "BIG theme"));
    }
}