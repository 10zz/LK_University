package com.courseproject.mlkuniversity;

import android.Manifest;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.jetbrains.annotations.NotNull;


public class MainActivity extends AppCompatActivity
{
    private static int tabNum, tabTextId;
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

        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {/* ... */}
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                }).check();

        // 1. Привязка View-переменных.
        ImageButton profileButton = findViewById(R.id.returnButton);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager2 viewPager2 = findViewById(R.id.pager);

        // 2. Привязка кнопки перехода в профиль к слушателю.
        profileButton.setOnClickListener(v ->
        {
            // Переход в ProfileActivity.
            Intent ProfileIntent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(ProfileIntent);
        });

        // 3. Проверка типа пользователя.
        /*final SharedPreferences settings = getSharedPreferences("Account", MODE_PRIVATE);
        if (settings.contains("user_type"))
        {
            // Если пользователь - студент, нижняя панель отрисовывается методом
            // StudentScreenSlidePageAdapter.
            if (settings.getString("user_type", "err").equals("student"))
            {*/
                pagerAdapter = new StudentScreenSlidePageAdapter(this);
                tabTextId = R.array.student_main_tab_ui;
                tabNum = 4;
            /*}
            // Если пользователь - преподаватель, нижняя панель отрисовывается методом
            // TeacherScreenSlidePageAdapter.
            else if (settings.getString("user_type", "err").equals("teacher"))
            {*/
                /*pagerAdapter = new TeacherScreenSlidePageAdapter(this);
                tabTextId = R.array.teacher_main_tab_ui;
                tabNum = 2;
            }*/
            viewPager2.setAdapter(pagerAdapter);
        /*}*/
        // Если в SharedPreferences нет данных от типе пользователя - переход в LogInActivity.
        // TODO
        /*else
        {
            Intent LoginIntent = new Intent(MainActivity.this, LogInActivity.class);
            startActivity(LoginIntent);
        }*/

        // 4. Отрисовка tabLayout.
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy()
                {
                    final String[] tabText = getResources().getStringArray(tabTextId);
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position)
                    {
                        // Установка иконок для вкладок tabLayout.
                        tab.setText(tabText[position])
                                .setIcon(R.drawable.ic_launcher_foreground);
                    }
        });
        tabLayoutMediator.attach();
    }

    // Класс слайдера фрагментов для студентов.
    private static class StudentScreenSlidePageAdapter extends FragmentStateAdapter
    {
        public StudentScreenSlidePageAdapter(MainActivity mainActivity)
        {
            super(mainActivity);
        }

        @NonNull
        @NotNull
        @Override
        public Fragment createFragment(int position)
        {
            switch (position)
            {
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
        public int getItemCount()
        {
            return tabNum;
        }
    }

    // Класс слайдера фрагментов для преподавателей.
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
            return tabNum;
        }
    }
}