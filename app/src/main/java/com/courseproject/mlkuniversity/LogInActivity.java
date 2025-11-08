package com.courseproject.mlkuniversity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LogInActivity extends AppCompatActivity
{
    EditText emailEntry, passwordEntry;
    CheckBox rememberCheckbox;
    Button logInButton, registerButton; // passwordRecoverButton - если будет время закодить
    OkHttpClient httpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // TODO: Связать emailEntry, passwordEntry, rememberCheckbox, logInButton, registerButton
        //  с View-элементами.
    }

    View.OnClickListener buttonListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            //TODO: добавить ветвление для двух кнопок -
            // logInButton вызовет logInRequest
            // registerButton откроет registerActivity
        }
    };

    // отправляет POST-запрос с паролем и почтой на сервер для проверки регистрации.
    private void logInRequest()
    {
        final String emailString = emailEntry.getText().toString();
        final String passwordString = passwordEntry.getText().toString();

        RequestBody requestBody = new FormBody.Builder()
                .add("email",emailString)
                .add("password",passwordString)
                .build();
        Request request = new Request.Builder().url(getString(R.string.base_url)).post(requestBody).build();

        httpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {}

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            // TODO: написать POST-скрипт,
                            //  получающий на вход email и пароль,
                            //  возвращающий (через echo) есть ли данный пользователь в базе данных.
                            String responseResult = response.body().string();
                            // TODO: написать ветвление - если пользователь есть в системе, перейти
                            //  на activity с основным интерфейсом в соответствии с параметрами на
                            //  этой странице (запомнить пользователя в системе или нет) и его ролью,
                            //  указанной в базе данных;
                            //  если пользователя в системе нет - вывести предупреждение об этом в
                            //  textView над полями для ввода данных.
                        }
                        catch (IOException e) { throw new RuntimeException(e); }
                    }
                });
            }
        });
    }
}