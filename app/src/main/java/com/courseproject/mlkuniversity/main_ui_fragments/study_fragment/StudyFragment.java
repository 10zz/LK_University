package com.courseproject.mlkuniversity.main_ui_fragments.study_fragment;

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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class StudyFragment extends Fragment
{
    ArrayList<StudyListItem> studyListItems = new ArrayList<>();


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        // 1. Создается объект inflater и RecyclerView этого фрагмента.
        View rootView = inflater.inflate(R.layout.fragment_study, container,false);
        RecyclerView recyclerView = rootView.findViewById(R.id.list);
        // 2. Заполняется массив значений списка.
        requestData();
        // 3. Создаётся объект адаптера с передачей фрагмента и массива значений списка.
        StudyListAdapter adapter = new StudyListAdapter(rootView.getContext(), studyListItems);
        // 4. Созданный адаптер задаётся для текущего фрагмента.
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private void requestData()
    {
        /*HTTPRequests request = new HTTPRequests();
        JSONObject[] responseJSON = request.JSONGetRequest(this.getContext(), getString(R.string.study_request));

        for (JSONObject object : responseJSON)
            try {
                studyListItems.add(new StudyListItem(object.getString("header"),
                        object.getString("description"),
                        request.BitmapGetRequest(this.getContext(), object.getString("icon_path"))
                ));
            }
            catch (JSONException e)
            {
                throw new RuntimeException(e);
            }*/
    }
}