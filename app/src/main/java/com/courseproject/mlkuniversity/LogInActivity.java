package com.courseproject.mlkuniversity;

import android.content.Intent;
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

public class LogInActivity extends AppCompatActivity
{
     EditText emailEntry, passwordEntry;
    Button logInButton, registerButton; // passwordRecoverButton - если будет время закодить
    TextView errorTextView;
    OkHttpClient httpClient;
    // Возвращаемое значение метода logInRequest.
    String responseResult;

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

        emailEntry = findViewById(R.id.emailEditText);
        passwordEntry = findViewById(R.id.passwordEditText);
        logInButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        errorTextView = findViewById(R.id.errorTextView);

        logInButton.setOnClickListener(buttonListener);
        registerButton.setOnClickListener(buttonListener);

        httpClient = new OkHttpClient();
    }

    View.OnClickListener buttonListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            // Если нажата кнопка входа.
            if (v.getId() == R.id.loginButton)
            {
                // Выполняется POST-запрос входа на сервер, возвращается строка responseResult.
                logInRequest();
                // Проверка строки на наличие символов.
                if (responseResult.isEmpty())
                {
                    errorTextView.setText(R.string.login_empty_response_error_message);
                    return;
                }
                // Разбиение ответа на подстроки.
                String[] responseParts = responseResult.split("\n");

                switch (responseParts[0])
                {
                    // Если вход успешен, управление передаётся в MainActivity, с параметрами
                    // имени, почты, СНИЛСа и номера паспорта.
                    case("success"):
                    {
                        Intent MainIntent = new Intent(LogInActivity.this, MainActivity.class)
                                .putExtra("name", responseParts[1])
                                .putExtra("email",responseParts[2])
                                .putExtra("SNILS",responseParts[3])
                                .putExtra("ID",responseParts[4]);
                        startActivity(MainIntent);
                        break;
                    }
                    // Если пользователя не найдено в БД, выводится соответствующее сообщение
                    // об ошибке.
                    case("failure"):
                    {
                        errorTextView.setText(R.string.login_error_message);
                        break;
                    }
                    // Если отправленный запрос не является POST, выводится сообщение об ошибке
                    // в API.
                    case("failed, an error occurred"):
                    {
                        errorTextView.setText(R.string.login_server_error_message);
                        break;
                    }
                    default:
                    {
                        errorTextView.setText(R.string.login_unknown_error_message);
                    }
                }
            }
            // Если нажата кнопка регистрации.
            else if (v.getId() == R.id.registerButton)
            {
                // Осуществляется переход в SignUpActivity с передачей введённого email и пароля.
                Intent SignUpIntent = new Intent(LogInActivity.this, SignUpActivity.class)
                        .putExtra("email", emailEntry.getText())
                        .putExtra("password", passwordEntry.getText());
                startActivity(SignUpIntent);
            }
            else
                System.out.println("Unknown button");
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
                        { responseResult = response.body().string(); }
                        catch (IOException e) { throw new RuntimeException(e); }
                    }
                });
            }
        });
    }
}