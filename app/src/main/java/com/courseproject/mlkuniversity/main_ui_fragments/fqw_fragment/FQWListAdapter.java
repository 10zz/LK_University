package com.courseproject.mlkuniversity.main_ui_fragments.fqw_fragment;

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

public class FQWListAdapter extends RecyclerView.Adapter<FQWListAdapter.ViewHolder>
{
    private final LayoutInflater inflater;
    private final List<FQWListItem> fqwListItems;


    FQWListAdapter(Context context, List<FQWListItem> fqwListItems)
    {
        this.fqwListItems = fqwListItems;
        this.inflater = LayoutInflater.from(context);
    }


    // Возвращает объект ViewHolder, который будет хранить данные по одному объекту FQWListItem.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.list_item_fqw, parent, false);
        return new ViewHolder(view);
    }


    // Выполняет привязку объекта ViewHolder к объекту FQWListItem по определенной позиции.
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        FQWListItem fqwListItem = fqwListItems.get(position);
        holder.studentNameView.setText(fqwListItem.getStudentName());
        holder.themeView.setText(fqwListItem.getTheme());
        holder.departmentView.setText(fqwListItem.getDepartment());
        holder.groupView.setText(fqwListItem.getGroup());
        // TODO: сеттер для иконки.
    }


    // Возвращает количество объектов в списке.
    @Override
    public int getItemCount()
    {
        return fqwListItems.size();
    }


    // Использует определенные в list_item_fqw.xml элементы View.
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        final TextView studentNameView, themeView, departmentView, groupView;
        final ImageView studentPicture;


        ViewHolder(View view)
        {
            super(view);
            // TODO: присваивание иконки из переменной с иконкой.
            studentNameView = view.findViewById(R.id.studentNameText);
            themeView = view.findViewById(R.id.themeText);
            departmentView = view.findViewById(R.id.departmentText);
            groupView = view.findViewById(R.id.groupText);
            studentPicture = view.findViewById(R.id.studentPicture);
        }
    }
}