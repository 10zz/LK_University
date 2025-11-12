package com.courseproject.mlkuniversity.main_ui_fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.courseproject.mlkuniversity.R;

import org.jetbrains.annotations.NotNull;


public class ScheduleFragment extends Fragment {

    @Nullable
    @org.jetbrains.annotations.Nullable

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        return (ViewGroup) inflater.inflate(R.layout.fragment_schedule, container, false);
    }
}