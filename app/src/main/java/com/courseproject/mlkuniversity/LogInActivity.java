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

        // Запрос параметра статуса входа из SharedPreferences для автоматического входа в приложение.
        if (getSharedPreferences("Account", MODE_PRIVATE)
                .getBoolean("logged_in", false))
        {
            Intent MainIntent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(MainIntent);
        }

        emailEntry = findViewById(R.id.emailEditText);
        passwordEntry = findViewById(R.id.passwordEditText);
        logInButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        passwordRecoveryButton = findViewById(R.id.passwordRecoveryButton);
        errorTextView = findViewById(R.id.errorTextView);

        logInButton.setOnClickListener(buttonListener);
        registerButton.setOnClickListener(buttonListener);
        passwordRecoveryButton.setOnClickListener(buttonListener);

        httpClient = new OkHttpClient();

        // Передача значений из SignUpActivity в текстовые поля.
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
            // Если нажата кнопка входа.
            if (v.getId() == R.id.loginButton)
            {
                /*HTTPRequests request = new HTTPRequests();
                JSONObject response = request.logInPostRequest(LogInActivity.this, emailEntry.getText().toString(), passwordEntry.getText().toString());

                String loginStatus;
                try
                {
                    loginStatus = response.getString("status");
                }
                catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }

                switch (loginStatus)
                {
                    // Если вход успешен, управление передаётся в MainActivity.
                    // Статус входа, имя, email, роль и пароль сохраняются в SharedPreferences.
                    case("success"):
                    {
                        try
                        {
                            SharedPreferences settings = getSharedPreferences("Account", MODE_PRIVATE);
                            SharedPreferences.Editor prefEditor = settings.edit();
                            prefEditor.putBoolean("logged_in", true);
                            prefEditor.putString("name", response.getString("name"));
                            prefEditor.putString("email", response.getString("email"));
                            prefEditor.putString("user_type", response.getString("user_type"));
                            prefEditor.putString("password", response.getString("password"));
                            prefEditor.apply();

                            Intent MainIntent = new Intent(LogInActivity.this, MainActivity.class);
                            startActivity(MainIntent);
                            break;
                        }
                        catch (JSONException e)
                        {
                            throw new RuntimeException(e);
                        }
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
                        errorTextView.setText(R.string.api_error_message);
                        break;
                    }
                    default:
                    {
                        errorTextView.setText(R.string.login_unknown_error_message);
                    }
                }*/
                // Потом убрать
                Intent MainIntent = new Intent(LogInActivity.this, MainActivity.class);
                startActivity(MainIntent);
            }
            // Если нажата кнопка регистрации.
            else if (v.getId() == R.id.registerButton)
            {
                // Осуществляется переход в SignUpActivity с передачей введённого email и пароля.
                Intent SignUpIntent = new Intent(LogInActivity.this, SignUpActivity.class)
                        .putExtra("email", emailEntry.getText().toString())
                        .putExtra("password", passwordEntry.getText().toString());
                startActivity(SignUpIntent);
            }
            else if (v.getId() == R.id.passwordRecoveryButton)
            {
                // Осуществляется переход в PasswordRecoveryActivity с передачей введённого email.
                Intent SignUpIntent = new Intent(LogInActivity.this, PasswordRecoveryActivity.class)
                        .putExtra("email", emailEntry.getText().toString());
                startActivity(SignUpIntent);
            }
            else
                System.out.println("Unknown button");
        }
    };
}