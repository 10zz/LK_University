package com.courseproject.mlkuniversity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;


public class LogInActivity extends AppCompatActivity
{
    EditText emailEntry, passwordEntry;
    Button logInButton, registerButton, passwordRecoveryButton;
    TextView errorTextView;
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

        // 1. Запрос параметра статуса входа из SharedPreferences(logged_in) для автоматического входа в приложение.
        if (getSharedPreferences("Account", MODE_PRIVATE)
                .getBoolean("logged_in", false))
        {
            Intent MainIntent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(MainIntent);
        }

        // 2. Привязка View-переменных.
        emailEntry = findViewById(R.id.emailEditText);
        passwordEntry = findViewById(R.id.passwordEditText);
        logInButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        passwordRecoveryButton = findViewById(R.id.passwordRecoveryButton);
        errorTextView = findViewById(R.id.errorTextView);

        // 3. Привязка кнопок к слушателю.
        logInButton.setOnClickListener(buttonListener);
        registerButton.setOnClickListener(buttonListener);
        passwordRecoveryButton.setOnClickListener(buttonListener);

        // 4. Передача значений из SignUpActivity или PasswordRecoveryActivity в текстовые поля.
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
        public void onClick(View v)
        {
            httpClient = new OkHttpClient();

            // Если нажата кнопка входа.
            if (v.getId() == R.id.loginButton)
            {
                // 1. Вызов метода logInPostRequest - отправка POST-запроса с введёнными
                // электронной почтой и паролем.
                HTTPRequests request = new HTTPRequests();
                JSONObject response = request.logInPostRequest(LogInActivity.this,
                        emailEntry.getText().toString(), passwordEntry.getText().toString());

                String loginStatus;
                try
                {
                    loginStatus = response.getString("status");
                }
                catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }

                // 2. Проверка статуса входа.
                switch (loginStatus)
                {
                    case("success"):
                    {
                        try
                        {
                            // 3. Статус входа, имя, email, роль и пароль сохраняются в
                            // SharedPreferences.
                            SharedPreferences settings = getSharedPreferences("Account", MODE_PRIVATE);
                            SharedPreferences.Editor prefEditor = settings.edit();
                            prefEditor.putBoolean("logged_in", true);
                            prefEditor.putString("name", response.getString("name"));
                            prefEditor.putString("email", response.getString("email"));
                            prefEditor.putString("user_type", response.getString("user_type"));
                            prefEditor.putString("password", response.getString("password"));
                            prefEditor.apply();

                            // 4. Переход в MainActivity.
                            Intent MainIntent = new Intent(LogInActivity.this, MainActivity.class);
                            startActivity(MainIntent);
                            break;
                        }
                        catch (JSONException e)
                        {
                            throw new RuntimeException(e);
                        }
                    }
                    // Если пользователя не найдено в БД.
                    case("failure"):
                    {
                        errorTextView.setText(R.string.login_error_message);
                        break;
                    }
                    // Если отправленный запрос не является POST.
                    case("failed, an error occurred"):
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
            // Если нажата кнопка регистрации.
            else if (v.getId() == R.id.registerButton)
            {
                // Переход в SignUpActivity с передачей введённого email и пароля.
                Intent SignUpIntent = new Intent(LogInActivity.this, SignUpActivity.class)
                        .putExtra("email", emailEntry.getText().toString())
                        .putExtra("password", passwordEntry.getText().toString());
                startActivity(SignUpIntent);
            }
            // Если нажата кнопка восстановления пароля.
            else if (v.getId() == R.id.passwordRecoveryButton)
            {
                // Переход в PasswordRecoveryActivity с передачей введённого email.
                Intent PasswordRecoveryIntent = new Intent(LogInActivity.this, PasswordRecoveryActivity.class)
                        .putExtra("email", emailEntry.getText().toString());
                startActivity(PasswordRecoveryIntent);
            }
            else
                System.out.println("Unknown button");
        }
    };
}