package com.courseproject.mlkuniversity.main_ui_fragments.home_fragment;

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


public class HomeFragment extends Fragment
{
    ArrayList<HomeListItem> homeListItems = new ArrayList<>();


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        // 1. Создается объект inflater и RecyclerView этого фрагмента.
        View rootView = inflater.inflate(R.layout.fragment_home, container,false);
        RecyclerView recyclerView = rootView.findViewById(R.id.list);
        // 2. Заполняется массив значений списка.
        requestData();
        // 3. Создаётся объект адаптера с передачей фрагмента и массива значений списка.
        HomeListAdapter adapter = new HomeListAdapter(rootView.getContext(), homeListItems);
        // 4. Созданный адаптер задаётся для текущего фрагмента.
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private void requestData()
    {
        // TODO
        /*HTTPRequests request = new HTTPRequests();
        JSONObject[] responseJSON = request.JSONGetRequest(this.getContext(), getString(R.string.home_request));

        for (JSONObject object : responseJSON)
            try {
                homeListItems.add(new HomeListItem(object.getString("title"),
                        object.getString("description")
                ));
            }
            catch (JSONException e)
            {
                throw new RuntimeException(e);
            }*/
        // Тестовые значения.
        homeListItems.add(new HomeListItem("Бразилия", "Бразилиа"));
        homeListItems.add(new HomeListItem("Аргентина", "Буэнос-Айрес"));
        homeListItems.add(new HomeListItem("Колумбия", "Богота"));
        homeListItems.add(new HomeListItem("Уругвай", "Монтевидео"));
        homeListItems.add(new HomeListItem("Чили", "Сантьяго"));
    }
}