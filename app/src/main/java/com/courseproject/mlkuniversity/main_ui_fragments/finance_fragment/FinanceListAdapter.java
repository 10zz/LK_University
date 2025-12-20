package com.courseproject.mlkuniversity.main_ui_fragments.finance_fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.courseproject.mlkuniversity.R;

import java.util.List;

public class FinanceListAdapter extends RecyclerView.Adapter<FinanceListAdapter.ViewHolder>
{
    private final LayoutInflater inflater;
    private final List<FinanceListItem> financeListItems;
    private final Drawable depositIcon, withdrawIcon;


FinanceListAdapter(Context context, List<FinanceListItem> financeListItems)
    {
        this.financeListItems = financeListItems;
        this.inflater = LayoutInflater.from(context);
        depositIcon = AppCompatResources.getDrawable(context, R.drawable.green_up_arrow);
        withdrawIcon = AppCompatResources.getDrawable(context, R.drawable.red_bottom_arrow);
    }


    // Возвращает объект ViewHolder, который будет хранить данные по одному объекту FinanceListItem.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        View view = inflater.inflate(R.layout.list_item_finance, parent, false);
        return new FinanceListAdapter.ViewHolder(view);
    }


    // Выполняет привязку объекта ViewHolder к объекту FinanceListItem по определенной позиции.
    @Override
    public void onBindViewHolder(FinanceListAdapter.ViewHolder holder, int position)
    {
        FinanceListItem financeListItem = financeListItems.get(position);
        holder.operationView.setText(financeListItem.getOperation());
        holder.operationTypeView.setText(financeListItem.getOperationType());
        holder.sumView.setText(Integer.toString(financeListItem.getSum()));
        holder.paymentDateView.setText(financeListItem.getPaymentDate());

        if (financeListItem.getSum() > 0)
            holder.operationIcon.setImageDrawable(depositIcon);
        else
            holder.operationIcon.setImageDrawable(withdrawIcon);
    }


    // Возвращает количество объектов в списке.
    @Override
    public int getItemCount()
    {
        return financeListItems.size();
    }


    // Использует определенные в list_item_finance.xml элементы View.
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        final TextView operationView, operationTypeView, sumView, paymentDateView;
        final ImageView operationIcon;


        ViewHolder(View view)
        {
            super(view);
            operationIcon = view.findViewById(R.id.operationIcon);
            operationView = view.findViewById(R.id.operationText);
            operationTypeView = view.findViewById(R.id.operationTypeText);
            sumView = view.findViewById(R.id.sumText);
            paymentDateView = view.findViewById(R.id.paymentDateText);
        }
    }
}