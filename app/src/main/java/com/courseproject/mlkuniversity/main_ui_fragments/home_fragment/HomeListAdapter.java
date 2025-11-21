package com.courseproject.mlkuniversity.main_ui_fragments.home_fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.courseproject.mlkuniversity.R;

import java.util.List;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ViewHolder>
{
    private final LayoutInflater inflater;
    private final List<HomeListItem> homeListItems;


    HomeListAdapter(Context context, List<HomeListItem> homeListItems)
    {
        this.homeListItems = homeListItems;
        this.inflater = LayoutInflater.from(context);
    }


    // Возвращает объект ViewHolder, который будет хранить данные по одному объекту HomeListItem.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        View view = inflater.inflate(R.layout.list_item_home, parent, false);
        return new ViewHolder(view);
    }


    // Выполняет привязку объекта ViewHolder к объекту HomeListItem по определенной позиции.
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        HomeListItem homeListItem = homeListItems.get(position);
        holder.titleView.setText(homeListItem.getTitle());
        holder.descriptionView.setText(homeListItem.getDescription());
    }


    // Возвращает количество объектов в списке.
    @Override
    public int getItemCount()
    {
        return homeListItems.size();
    }


    // Использует определенные в list_item_home.xml элементы управления.
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        final TextView titleView, descriptionView;
        ViewHolder(View view)
        {
            super(view);
            titleView = view.findViewById(R.id.titleText);
            descriptionView = view.findViewById(R.id.descriptionText);
        }
    }
}
