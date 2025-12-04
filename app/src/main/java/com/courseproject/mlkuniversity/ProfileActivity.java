package com.courseproject.mlkuniversity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity
{
    TextView userNameLabel, userRoleLabel, userEmailLabel;
    EditText currentPasswordEntry, newPasswordEntry, verifyPasswordEntry;
    ImageButton returnButton;
    Button changeProfilePictureButton, changePasswordButton, accountExitButton;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userNameLabel = findViewById(R.id.userNameText);
        userRoleLabel = findViewById(R.id.userRoleText);
        userEmailLabel = findViewById(R.id.userEmailText);
        currentPasswordEntry = findViewById(R.id.currentPasswordEditText);
        newPasswordEntry = findViewById(R.id.newPasswordEditText);
        verifyPasswordEntry = findViewById(R.id.verifyPasswordEditText);
        returnButton = findViewById(R.id.returnButton);
        changeProfilePictureButton = findViewById(R.id.changeProfilePictureButton);
        changePasswordButton = findViewById(R.id.changePasswordButton);
        accountExitButton = findViewById(R.id.accountExitButton);

        SharedPreferences settings = getSharedPreferences("Account", MODE_PRIVATE);
        userNameLabel.setText(settings.getString("name", "err"));
        userEmailLabel.setText(settings.getString("email", "err"));
        userRoleLabel.setText(settings.getString("user_type", "err"));

        returnButton.setOnClickListener(buttonListener);
        changeProfilePictureButton.setOnClickListener(buttonListener);
        changePasswordButton.setOnClickListener(buttonListener);
        accountExitButton.setOnClickListener(buttonListener);
    }

    View.OnClickListener buttonListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            // Возврат на MainActivity.
            if (v.getId() == R.id.returnButton)
            {
                Intent MainIntent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(MainIntent);
            }
            // Смена иконки профиля.
            else if (v.getId() == R.id.changeProfilePictureButton)
            {
                // TODO: PHP скрипт смены иконки профиля.
            }
            // Смена пароля.
            else if (v.getId() == R.id.changePasswordButton)
            {
                if (newPasswordEntry.getText().toString().equals(verifyPasswordEntry.getText().toString()))
                {
                    // TODO: PHP скрипт смены пароля.
                }
                else
                    Toast.makeText(getApplicationContext(), "Введённые пароли не совпадают", Toast.LENGTH_SHORT).show();
            }
            // Выход из аккаунта
            else if (v.getId() == R.id.accountExitButton)
            {
                // Очистка SharedPreferences и переход на LogInActivity.
                SharedPreferences settings = getSharedPreferences("Account", MODE_PRIVATE);
                settings.edit().clear().apply();
                Intent LogInIntent = new Intent(ProfileActivity.this, LogInActivity.class);
                startActivity(LogInIntent);
            }
            else
                System.out.println("Unknown button");
        }
    };
}