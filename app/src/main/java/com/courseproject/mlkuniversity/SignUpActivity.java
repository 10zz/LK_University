package com.courseproject.mlkuniversity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class SignUpActivity extends AppCompatActivity
{
    EditText emailEntry, SNILSEntry, IDEntry, passwordEntry, verifyPasswordEntry;
    Button logInButton, registerButton;
    TextView errorTextView;
    OkHttpClient httpClient;
    // Возвращаемое значение метода logInRequest.
    String responseResult;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        emailEntry = findViewById(R.id.emailEditText);
        SNILSEntry = findViewById(R.id.SNILSEditText);
        IDEntry = findViewById(R.id.IDEditText);
        passwordEntry = findViewById(R.id.passwordEditText);
        verifyPasswordEntry = findViewById(R.id.verifyPasswordEditText);
        logInButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        errorTextView = findViewById(R.id.errorTextView);

        logInButton.setOnClickListener(buttonListener);
        registerButton.setOnClickListener(buttonListener);

        httpClient = new OkHttpClient();

        // Передача значений из LogInActivity в текстовые поля.
        Bundle arguments = getIntent().getExtras();
        if (arguments != null)
        {
            emailEntry.setText(arguments.getString("email"));
            passwordEntry.setText(arguments.getString("password"));
        }
    }

    View.OnClickListener buttonListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            // Если нажата кнопка входа.
            if (v.getId() == R.id.loginButton)
            {
                // Переход в LogInActivity с текстом из полей email и password.
                Intent LogInIntent = new Intent(SignUpActivity.this, LogInActivity.class)
                        .putExtra("email", emailEntry.getText().toString())
                        .putExtra("password", passwordEntry.getText().toString());
                startActivity(LogInIntent);
            }
            // Если нажата кнопка регистрации.
            else if (v.getId() == R.id.registerButton) {
                // Выполняется POST-запрос входа на сервер, возвращается строка responseResult.
                SignUpRequest();
                // Проверка строки на наличие символов.
                if (responseResult == null || responseResult.isEmpty()) {
                    errorTextView.setText(R.string.login_empty_response_error_message);
                    return;
                }
                // Разбиение ответа на подстроки.
                String[] responseParts = responseResult.split("\n");

                switch (responseParts[0])
                {
                    // Если вход успешен, управление передаётся в MainActivity.
                    // Статус входа, имя, email, роль и пароль сохраняются в SharedPreferences.
                    case ("success"):
                    {
                        SharedPreferences settings = getSharedPreferences("Account", MODE_PRIVATE);
                        SharedPreferences.Editor prefEditor = settings.edit();
                        prefEditor.putBoolean("logged_in", true);
                        prefEditor.putString("name", responseParts[1]);
                        prefEditor.putString("email", responseParts[2]);
                        prefEditor.putString("user_type", responseParts[3]);
                        prefEditor.putString("password", responseParts[4]);
                        prefEditor.apply();

                        Intent MainIntent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(MainIntent);
                        break;
                    }
                    // Если пользователя не найдено в БД, выводится соответствующее сообщение
                    // об ошибке.
                    case ("failure"):
                    {
                        errorTextView.setText(R.string.signup_server_error);
                        break;
                    }
                    // Если отправленный запрос не является POST, выводится сообщение об ошибке
                    // в API.
                    case ("failed, an error occurred"):
                    {
                        errorTextView.setText(R.string.api_error_message);
                        break;
                    }
                    default:
                    {
                        errorTextView.setText(R.string.login_unknown_error_message);
                    }
                }
            }
            else
                System.out.println("Unknown button");
        }
    };

    // отправляет POST-запрос с регистрационными данными на сервер для создания нового пользователя.
    private void SignUpRequest()
    {
        final String emailString = emailEntry.getText().toString();
        final String SNILSString = SNILSEntry.getText().toString();
        final String IDString = IDEntry.getText().toString();
        final String passwordString = passwordEntry.getText().toString();

        RequestBody requestBody = new FormBody.Builder()
                .add("email",emailString)
                .add("SNILS",SNILSString)
                .add("ID",IDString)
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
                        { responseResult = response.body().string(); }
                        catch (IOException e) { throw new RuntimeException(e); }
                    }
                });
            }
        });
    }
}