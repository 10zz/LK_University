package com.courseproject.mlkuniversity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

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
    Button changeProfilePictureButton, changePasswordButton;


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

        Bundle arguments = getIntent().getExtras();
        if (arguments != null)
        {
            userNameLabel.setText(arguments.getString("name"));
            userEmailLabel.setText(arguments.getString("email"));
            // TODO: userRoleLabel.setText(arguments.getString("role"));
        }

        returnButton.setOnClickListener(buttonListener);
        changeProfilePictureButton.setOnClickListener(buttonListener);
        changePasswordButton.setOnClickListener(buttonListener);
    }

    View.OnClickListener buttonListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if (v.getId() == R.id.returnButton)
            {
                Bundle arguments = getIntent().getExtras();
                Intent MainIntent;
                if (arguments != null)
                    MainIntent = new Intent(ProfileActivity.this, MainActivity.class)
                            .putExtra("email", arguments.getString("name"))
                            .putExtra("password", arguments.getString("email"));
                else
                    MainIntent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(MainIntent);
            }
            else if (v.getId() == R.id.changeProfilePictureButton)
            {
                // TODO: PHP скрипт смены иконки профиля.
                return;
            }
            else if (v.getId() == R.id.changePasswordButton)
            {
                // TODO: PHP скрипт смены пароля.
                return;
            }
            else
                System.out.println("Unknown button");
        }
    };
}