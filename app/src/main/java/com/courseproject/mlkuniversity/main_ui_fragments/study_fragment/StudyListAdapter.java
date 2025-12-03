package com.courseproject.mlkuniversity.main_ui_fragments.study_fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.courseproject.mlkuniversity.R;

import java.util.List;

public class StudyListAdapter extends RecyclerView.Adapter<StudyListAdapter.ViewHolder>
{
    private final LayoutInflater inflater;
    private final List<StudyListItem> studyListItems;


    StudyListAdapter(Context context, List<StudyListItem> studyListItems)
    {
        this.studyListItems = studyListItems;
        this.inflater = LayoutInflater.from(context);
    }


    // Возвращает объект ViewHolder, который будет хранить данные по одному объекту StudyListItem.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.list_item_study, parent, false);
        return new ViewHolder(view);
    }


    // Выполняет привязку объекта ViewHolder к объекту StudyListItem по определенной позиции.
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        StudyListItem studyListItem = studyListItems.get(position);
        holder.headerView.setText(studyListItem.getHeader());
        holder.descriptionView.setText(studyListItem.getDescription());
        holder.iconView.setImageBitmap(studyListItem.getIcon());
        // TODO: сеттер для иконки.
    }


    // Возвращает количество объектов в списке.
    @Override
    public int getItemCount()
    {
        return studyListItems.size();
    }


    // Использует определенные в list_item_study.xml элементы View.
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        final TextView headerView, descriptionView;
        final ImageView iconView;


        ViewHolder(View view)
        {
            super(view);
            headerView = view.findViewById(R.id.headerText);
            descriptionView = view.findViewById(R.id.descriptionText);
            iconView = view.findViewById(R.id.icon);
        }
    }
}