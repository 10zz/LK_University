package com.courseproject.mlkuniversity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.courseproject.mlkuniversity.main_ui_fragments.finance_fragment.FinanceFragment;
import com.courseproject.mlkuniversity.main_ui_fragments.fqw_fragment.FQWFragment;
import com.courseproject.mlkuniversity.main_ui_fragments.home_fragment.HomeFragment;
import com.courseproject.mlkuniversity.main_ui_fragments.schedule_fragment.ScheduleFragment;
import com.courseproject.mlkuniversity.main_ui_fragments.study_fragment.StudyFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity
{
    private static int NAM_PAGES, tabTextId;
    private ViewPager2 viewPager2;
    private FragmentStateAdapter pagerAdapter;
    private ImageButton profileButton;

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

        profileButton = findViewById(R.id.returnButton);
        profileButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent ProfileIntent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(ProfileIntent);
            }
        });

        viewPager2 = findViewById(R.id.pager);
        SharedPreferences settings = getSharedPreferences("Account", MODE_PRIVATE);
            if (settings.getString("user_type", "err").equals("student"))
            {
                pagerAdapter = new StudentScreenSlidePageAdapter(this);
                tabTextId = R.array.student_main_tab_ui;
                NAM_PAGES = 4;
            }
            else if (settings.getString("user_type", "err").equals("teacher"))
            {
                pagerAdapter = new TeacherScreenSlidePageAdapter(this);
                tabTextId = R.array.teacher_main_tab_ui;
                NAM_PAGES = 2;
            }
            viewPager2.setAdapter(pagerAdapter);

        /*else
        {
            Intent LoginIntent = new Intent(MainActivity.this, LogInActivity.class);
            startActivity(LoginIntent);
        }*/


        TabLayout tabLayout = findViewById(R.id.tab_layout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy()
                {
                    String[] tabText = getResources().getStringArray(tabTextId);
                    @Override
                    public void onConfigureTab(TabLayout.Tab tab, int position)
                    {
                        if (settings.getString("user_type", "err").equals("student"))
                            switch (position)
                            {
                                case (0):
                                {
                                    tab.setText(tabText[position])
                                            .setIcon(R.drawable.ic_launcher_foreground); // !! замени R.drawable.ic_launcher_foreground на нужную иконку, для остальных ниже аналогично.
                                    break;
                                }
                                case (1):
                                {
                                    tab.setText(tabText[position])
                                            .setIcon(R.drawable.profile_pic); // !! замени R.drawable.ic_launcher_foreground на нужную иконку, для остальных ниже аналогично.
                                    break;
                                }
                                case (2):
                                {
                                    tab.setText(tabText[position])
                                            .setIcon(R.drawable.logout_icon); // !! замени R.drawable.ic_launcher_foreground на нужную иконку, для остальных ниже аналогично.
                                    break;
                                }
                                case (3):
                                {
                                    tab.setText(tabText[position])
                                            .setIcon(R.drawable.ic_launcher_foreground); // !! замени R.drawable.ic_launcher_foreground на нужную иконку, для остальных ниже аналогично.
                                    break;
                                }
                                default:
                                    System.out.println("Icons err");
                            }
                        else if (settings.getString("user_type", "err").equals("teacher"))
                            switch (position)
                            {
                                case (0):
                                {
                                    tab.setText(tabText[position])
                                            .setIcon(R.drawable.ic_launcher_foreground); // !! замени R.drawable.ic_launcher_foreground на нужную иконку, для остальных ниже аналогично.
                                    break;
                                }
                                case (1):
                                {
                                    tab.setText(tabText[position])
                                            .setIcon(R.drawable.logout_icon); // !! замени R.drawable.ic_launcher_foreground на нужную иконку, для остальных ниже аналогично.
                                    break;
                                }
                                default:
                                    System.out.println("Icons err");
                            }
                    }
        });
        tabLayoutMediator.attach();
    }

    private static class StudentScreenSlidePageAdapter extends FragmentStateAdapter
    {
        public StudentScreenSlidePageAdapter(MainActivity mainActivity) {
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
                    return new StudyFragment();
                case 2:
                    return new ScheduleFragment();
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

    private static class TeacherScreenSlidePageAdapter extends FragmentStateAdapter
    {
        public TeacherScreenSlidePageAdapter(MainActivity mainActivity) {
            super(mainActivity);
        }

        @NonNull
        @NotNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new FQWFragment();
                case 1:
                    return new ScheduleFragment();
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