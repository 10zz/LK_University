package com.courseproject.mlkuniversity;

import android.content.Intent;
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

import okhttp3.OkHttpClient;

public class SignUpActivity extends AppCompatActivity
{
    EditText emailEntry, SNILSEntry, IDEntry, passwordEntry, verifyPasswordEntry;
    Button logInButton, registerButton;
    TextView errorTextView;
    OkHttpClient httpClient;


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
        errorTextView = findViewById(R.id.password_reset);

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
                /*HTTPRequests request = new HTTPRequests();
                JSONObject response = request.SignUpPostRequest(SignUpActivity.this,
                        emailEntry.getText().toString(),
                        passwordEntry.getText().toString(),
                        IDEntry.getText().toString(),
                        SNILSEntry.getText().toString());

                String singupStatus;
                try
                {
                    singupStatus = response.getString("status");
                }
                catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }

                switch (singupStatus)
                {
                    // Если вход успешен, управление передаётся в MainActivity.
                    // Статус входа, имя, email, роль и пароль сохраняются в SharedPreferences.
                    case ("success"):
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

                            Intent MainIntent = new Intent(SignUpActivity.this, MainActivity.class);
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
                }*/
                // Потом убрать
                Intent MainIntent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(MainIntent);
            }
            else
                System.out.println("Unknown button");
        }
    };
}