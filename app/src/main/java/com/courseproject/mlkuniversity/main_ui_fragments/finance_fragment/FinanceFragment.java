package com.courseproject.mlkuniversity.main_ui_fragments.finance_fragment;

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

public class FinanceFragment extends Fragment {
    ArrayList<FinanceListItem> financeListItems = new ArrayList<>();


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        // Создается объект inflater и RecyclerView этого фрагмента.
        View rootView = inflater.inflate(R.layout.fragment_finance, container,false);
        RecyclerView recyclerView = rootView.findViewById(R.id.list);
        // Заполняется массив значений списка.
        setInitialData();
        // Создаётся объект адаптера с передачей фрагмента и массива значений списка.
        FinanceListAdapter adapter = new FinanceListAdapter(rootView.getContext(), financeListItems);
        // Созданный адаптер задаётся для текущего фрагмента.
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    
    private void setInitialData()
    {
        /*HTTPRequests request = new HTTPRequests();
        JSONObject[] response = request.financePostRequest(this.getContext());

        for (JSONObject object : response)
            try
            {
                financeListItems.add(new FinanceListItem(
                        object.getString("operation"),
                        object.getString("operationType"),
                        object.getString("operationType"),
                        object.getString("paymentDate")
                ));
            }
            catch (JSONException e)
            {
                throw new RuntimeException(e);
            }*/
        // Тестовые значения.
        financeListItems.add(new FinanceListItem("Бразилия", "Бразилиа", "23", "23.12.2333"));
        financeListItems.add(new FinanceListItem("Аргентина", "Буэнос-Айрес", "23", "23.12.2333"));
        financeListItems.add(new FinanceListItem("Колумбия", "Богота", "23", "23.12.2333"));
        financeListItems.add(new FinanceListItem("Уругвай", "Монтевидео", "23", "23.12.2333"));
        financeListItems.add(new FinanceListItem("Чили", "Сантьяго", "23", "23.12.2333"));
    }
}