package com.courseproject.mlkuniversity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;


public class ProfileActivity extends AppCompatActivity
{
    TextView userNameLabel, userRoleLabel, userEmailLabel;
    EditText currentPasswordEntry, newPasswordEntry, verifyPasswordEntry;
    ImageButton returnButton;
    ImageView profilePicture;
    Button changeProfilePictureButton, changePasswordButton, accountExitButton;


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

        // 1. Привязка View-переменных.
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
        profilePicture = findViewById(R.id.profilePicture);

        // 2. Установка значений из SharedPreference в текстовые поля.
        SharedPreferences settings = getSharedPreferences("Account", MODE_PRIVATE);
        userNameLabel.setText(settings.getString("name", "err"));
        userEmailLabel.setText(settings.getString("email", "err"));
        userRoleLabel.setText(settings.getString("user_type", "err"));

        HTTPRequests request = new HTTPRequests();
        String picLink = settings.getString("profile_picture", "err");
        if (!picLink.equals("err"))
            profilePicture.setImageBitmap(request.BitmapGetRequest(this, picLink));

        // 3. Привязка кнопок к слушателю.
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
            // Если нажата кнопка возврата на MainActivity.
            if (v.getId() == R.id.returnButton)
            {
                Intent MainIntent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(MainIntent);
            }
            // Если нажата кнопка смены иконки профиля.
            else if (v.getId() == R.id.changeProfilePictureButton)
            {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                intent.setType("image/*");

                startActivity(intent);
                // TODO: PHP скрипт смены иконки профиля.
            }
            // Если нажата кнопка смены пароля.
            else if (v.getId() == R.id.changePasswordButton)
            {
                if (newPasswordEntry.getText().toString().equals(verifyPasswordEntry.getText().toString()))
                {
                    HTTPRequests request = new HTTPRequests();
                    JSONObject responseJSON = request.changePasswordPostRequest(ProfileActivity.this,
                            userEmailLabel.getText().toString(),
                            newPasswordEntry.getText().toString());

                    String changeStatus;
                    try
                    {
                        changeStatus = responseJSON.getString("status");
                    }
                    catch (JSONException e)
                    {
                        throw new RuntimeException(e);
                    }

                    switch (changeStatus)
                    {
                        case("успешно"):
                        {
                                SharedPreferences settings = getSharedPreferences("Account", MODE_PRIVATE);
                                SharedPreferences.Editor prefEditor = settings.edit();
                                prefEditor.putString("password", newPasswordEntry.getText().toString());
                                prefEditor.apply();
                                break;
                        }
                        case ("ошибка сервера"):
                        case ("неуспешно"):
                        {
                            try {
                                Toast.makeText(getApplicationContext(),
                                        responseJSON.getString("message"),
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                            catch (JSONException e)
                            {
                                throw new RuntimeException(e);
                            }
                            break;
                        }
                        default:
                        {
                            Toast.makeText(getApplicationContext(),
                                            getText(R.string.login_unknown_error_message).toString(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                }
                else
                    Toast.makeText(getApplicationContext(),
                            "Введённые пароли не совпадают",
                            Toast.LENGTH_SHORT)
                            .show();
            }
            // Если нажата кнопка выхода из аккаунта
            else if (v.getId() == R.id.accountExitButton)
            {
                // Очистка SharedPreferences.
                SharedPreferences settings = getSharedPreferences("Account", MODE_PRIVATE);
                settings.edit().clear().apply();
                // Переход на LogInActivity.
                Intent LogInIntent = new Intent(ProfileActivity.this, LogInActivity.class);
                startActivity(LogInIntent);
            }
            else
                System.out.println("Unknown button");
        }
    };
}