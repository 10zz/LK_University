package com.courseproject.mlkuniversity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;


public class ProfileActivity extends AppCompatActivity
{
    TextView userEmailLabel;
    EditText currentPasswordEntry, newPasswordEntry, verifyPasswordEntry;
    ImageView profilePicture;
    SharedPreferences settings;


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

        settings = getSharedPreferences("Account", MODE_PRIVATE);

        // 1. Привязка View-переменных.
        TextView userNameLabel = findViewById(R.id.userNameText);
        TextView userRoleLabel = findViewById(R.id.userRoleText);
        userEmailLabel = findViewById(R.id.userEmailText);
        currentPasswordEntry = findViewById(R.id.currentPasswordEditText);
        newPasswordEntry = findViewById(R.id.newPasswordEditText);
        verifyPasswordEntry = findViewById(R.id.verifyPasswordEditText);
        ImageButton returnButton = findViewById(R.id.returnButton);
        Button changeProfilePictureButton = findViewById(R.id.changeProfilePictureButton);
        Button changePasswordButton = findViewById(R.id.changePasswordButton);
        Button accountExitButton = findViewById(R.id.accountExitButton);
        profilePicture = findViewById(R.id.profilePicture);

        // 2. Установка значений из SharedPreference в текстовые поля.
        userNameLabel.setText(settings.getString("name", "err"));
        userEmailLabel.setText(settings.getString("email", "err"));
        userRoleLabel.setText(settings.getString("user_type", "err"));

        // 3. HTTP-запрос для скачивания икони профиля и установка её в profilePicture.
        HTTPRequests request = new HTTPRequests();
        String picLink = settings.getString("profile_picture", "err");
        if (!picLink.equals("err"))
            profilePicture.setImageBitmap(request.BitmapGetRequest(this, picLink));

        // 4. Привязка кнопок к слушателю.
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
                getImageActivity.launch("image/*");
            }
            // Если нажата кнопка смены пароля.
            else if (v.getId() == R.id.changePasswordButton)
            {
                // Если введённые пароли совпадают.
                if (newPasswordEntry.getText().toString().equals(verifyPasswordEntry.getText().toString()))
                {
                    // 1. Отправка запроса на смену пароля.
                    HTTPRequests request = new HTTPRequests();
                    JSONObject responseJSON = request.changePasswordPostRequest(ProfileActivity.this,
                            userEmailLabel.getText().toString(),
                            newPasswordEntry.getText().toString());
                    try
                    {
                        // 2. Проверка ответа сервера.
                        String changeStatus = responseJSON.getString("status");
                        switch (changeStatus)
                        {
                            // Если смена пароля успешна.
                            case ("success"):
                            {
                                // 3. Перезапись нового пароля в SharedPreferences.
                                SharedPreferences.Editor prefEditor = settings.edit();
                                prefEditor.putString("password", newPasswordEntry.getText().toString());
                                prefEditor.apply();
                                // 4. Вывод всплывающего окна о смене пароля.
                                Toast.makeText(getApplicationContext(),
                                                responseJSON.getString("message"),
                                                Toast.LENGTH_SHORT)
                                        .show();
                                break;
                            }
                            // Если смена пароля не успешна, вывод сообщения об ошибке.
                            case ("error"):
                            {
                                Toast.makeText(getApplicationContext(),
                                                responseJSON.getString("message"),
                                                Toast.LENGTH_SHORT)
                                        .show();
                                break;
                            }
                            // Иначе, вывод сообщения о неизвестной ошибке.
                            default:
                            {
                                Toast.makeText(getApplicationContext(),
                                                getText(R.string.login_unknown_error_message).toString(),
                                                Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                    }
                    catch (JSONException e)
                    {}
                }
                else
                    // Иначе, вывод сообщения о несовпадении паролей.
                    Toast.makeText(getApplicationContext(),
                            "Введённые пароли не совпадают",
                            Toast.LENGTH_SHORT)
                            .show();
            }
            // Если нажата кнопка выхода из аккаунта
            else if (v.getId() == R.id.accountExitButton)
            {
                // Очистка SharedPreferences.
                settings.edit().clear().apply();
                // Переход на LogInActivity.
                Intent LogInIntent = new Intent(ProfileActivity.this, LogInActivity.class);
                startActivity(LogInIntent);
            }
            else
                System.out.println("Unknown button");
        }
    };

    ActivityResultLauncher<String> getImageActivity = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    HTTPRequests request = new HTTPRequests();
                    JSONObject responseJSON = request.sendImagePostRequest(ProfileActivity.this, uri);
                    try
                    {
                        if (responseJSON.getString("status").equals("success")) {
                            profilePicture.setImageURI(uri);
                            settings.edit().putString("profile_picture", "profile_pictures/" + settings.getString("email", "err") + ".png")
                                    .apply();

                        }
                        Toast.makeText(getApplicationContext(),
                                        responseJSON.getString("message"),
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                    catch (JSONException e)
                    {}
                }
            });
}