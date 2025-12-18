package com.courseproject.mlkuniversity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;


public class SignUpActivity extends AppCompatActivity
{
    EditText nameEntry, emailEntry, SNILSEntry, IDEntry, passwordEntry, verifyPasswordEntry;


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

        // 1. Привязка View-переменных.
        nameEntry = findViewById(R.id.nameEditText);
        emailEntry = findViewById(R.id.emailEditText);
        SNILSEntry = findViewById(R.id.SNILSEditText);
        IDEntry = findViewById(R.id.IDEditText);
        passwordEntry = findViewById(R.id.passwordEditText);
        verifyPasswordEntry = findViewById(R.id.verifyPasswordEditText);
        Button logInButton = findViewById(R.id.returnButton);
        Button registerButton = findViewById(R.id.registerButton);

        // 2. Привязка кнопок к слушателю.
        logInButton.setOnClickListener(buttonListener);
        registerButton.setOnClickListener(buttonListener);

        // 3. Передача значений из LogInActivity в текстовые поля.
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
            if (v.getId() == R.id.returnButton)
            {
                // Переход в LogInActivity с передачей текста из полей email и password.
                Intent LogInIntent = new Intent(SignUpActivity.this, LogInActivity.class)
                        .putExtra("email", emailEntry.getText().toString())
                        .putExtra("password", passwordEntry.getText().toString());
                startActivity(LogInIntent);
            }
            // Если нажата кнопка регистрации.
            else if (v.getId() == R.id.registerButton)
            {
                // Если введённые пароли совпадают.
                if (passwordEntry.getText().toString().equals(verifyPasswordEntry.getText().toString()))
                {
                    // 1. Вызов метода SignUpPostRequest - отправка POST-запроса с введёнными
                    // электронной почтой, паролем.
                    HTTPRequests request = new HTTPRequests();
                    JSONObject response = request.SignUpPostRequest(SignUpActivity.this,
                            nameEntry.getText().toString(),
                            emailEntry.getText().toString(),
                            passwordEntry.getText().toString(),
                            IDEntry.getText().toString(),
                            SNILSEntry.getText().toString());
                    try
                    {
                        String signupStatus = response.getString("status");
                        // 2. Проверка статуса входа.
                        switch (signupStatus)
                        {
                            case ("success"):
                            {
                                // 3. Статус входа, имя, email, роль и пароль сохраняются в
                                // SharedPreferences.
                                SharedPreferences settings = getSharedPreferences("Account", MODE_PRIVATE);
                                SharedPreferences.Editor prefEditor = settings.edit();
                                prefEditor.putBoolean("logged_in", true);
                                prefEditor.putString("email", emailEntry.getText().toString());
                                prefEditor.putString("password", passwordEntry.getText().toString());
                                prefEditor.putString("name", nameEntry.getText().toString());
                                prefEditor.putString("user_type", response.getString("user_type"));
                                prefEditor.putString("profile_picture", response.getString("user_icon"));
                                prefEditor.apply();

                                // 4. Переход в MainActivity.
                                Intent MainIntent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(MainIntent);
                                break;
                            }
                            // Если пользователя не найдено в БД.
                            case ("error"):
                            {
                                Toast.makeText(getApplicationContext(),
                                                response.getString("message"),
                                                Toast.LENGTH_SHORT)
                                        .show();
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
                    catch (JSONException e)
                    {}
                }
                else
                    Toast.makeText(getApplicationContext(),
                            "Введённые пароли не совпадают",
                            Toast.LENGTH_SHORT)
                            .show();
            }
            else
                System.out.println("Unknown button");
        }
    };
}