package com.courseproject.mlkuniversity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.courseproject.mlkuniversity.main_ui_fragments.FinanceFragment;
import com.courseproject.mlkuniversity.main_ui_fragments.HomeFragment;
import com.courseproject.mlkuniversity.main_ui_fragments.ScheduleFragment;
import com.courseproject.mlkuniversity.main_ui_fragments.StudyFragment;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity
{

    private static final int NAM_PAGES = 5;
    private ViewPager2 viewPager2;
    private FragmentStateAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Переход на activity входа в аккаунт.
       /* Intent logInIntent = new Intent(MainActivity.this, LogInActivity.class);
        startActivity(logInIntent);*/

        viewPager2 = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePageAdapter(this);
        viewPager2.setAdapter(pagerAdapter);
    }

    private class ScreenSlidePageAdapter extends FragmentStateAdapter {
        public ScreenSlidePageAdapter(MainActivity mainActivity) {
            super(mainActivity);
        }

        @NonNull
        @NotNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new HomeFragment();
                case 1:
                    return new ScheduleFragment();
                case 2:
                    return new StudyFragment();
                case 3:
                    return new FinanceFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return NAM_PAGES;
        }
    }
}