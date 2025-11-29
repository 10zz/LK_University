package com.courseproject.mlkuniversity;

import android.content.Intent;
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
import com.courseproject.mlkuniversity.main_ui_fragments.home_fragment.HomeFragment;
import com.courseproject.mlkuniversity.main_ui_fragments.schedule_fragment.ScheduleFragment;
import com.courseproject.mlkuniversity.main_ui_fragments.study_fragment.StudyFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity
{

    private static final int NAM_PAGES = 4;
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
                Bundle arguments = getIntent().getExtras();

                Intent ProfileIntent = new Intent(MainActivity.this, ProfileActivity.class)
                    .putExtra("name", "тфьу")
                    .putExtra("email", "уьфшд");
                // TODO: .putExtra("role", "тфьу")

                /* TODO: Intent ProfileIntent = new Intent(MainActivity.this, ProfileActivity.class)
                        .putExtra("name", arguments.getString("name"))
                        .putExtra("email", arguments.getString("email"));*/
                startActivity(ProfileIntent);
            }
        });

        viewPager2 = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePageAdapter(this);
        viewPager2.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy()
                {
                    String[] tabText = getResources().getStringArray(R.array.main_tab_ui);
                    @Override
                    public void onConfigureTab(TabLayout.Tab tab, int position)
                    {
                        tab.setText(tabText[position])
                                .setIcon(R.drawable.ic_launcher_foreground);
                    }
        });
        tabLayoutMediator.attach();
    }

    private class ScreenSlidePageAdapter extends FragmentStateAdapter
    {
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
}