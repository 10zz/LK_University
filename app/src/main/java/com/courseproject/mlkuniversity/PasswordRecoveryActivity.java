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


public class PasswordRecoveryActivity extends AppCompatActivity
{
    EditText emailEditText, IDEditText, SNILSEditText, newPasswordEditText, confirmPasswordEditText;
    Button sendButton, returnButton;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_password_recovery);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Привязка View-переменных.
        emailEditText = findViewById(R.id.emailEditText);
        IDEditText = findViewById(R.id.IDEditText);
        SNILSEditText = findViewById(R.id.SNILSEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        sendButton = findViewById(R.id.sendButton);
        returnButton = findViewById(R.id.returnButton);

        // 2. Привязка кнопок к слушателю.
        sendButton.setOnClickListener(buttonListener);
        returnButton.setOnClickListener(buttonListener);

        // 3. Передача значений из LogInActivity в текстовые поля.
        Bundle arguments = getIntent().getExtras();
        if (arguments != null)
            emailEditText.setText(arguments.getString("email"));
    }

    View.OnClickListener buttonListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            // Если нажата кнопка отправки.
            if (v.getId() == R.id.sendButton)
            {
                // Если введённые пароли совпадают.
                if (newPasswordEditText.getText().toString().equals(confirmPasswordEditText.getText().toString()))
                {
                    // 4. Отправка запроса на смену пароля.
                    HTTPRequests request = new HTTPRequests();
                    JSONObject responseJSON = request.recoverPasswordPostRequest(PasswordRecoveryActivity.this,
                            emailEditText.getText().toString(),
                            IDEditText.getText().toString(),
                            SNILSEditText.getText().toString(),
                            newPasswordEditText.getText().toString());
                    try
                    {
                        // 5. Проверка ответа сервера.
                        String changeStatus = responseJSON.getString("status");
                        switch (changeStatus)
                        {
                            // Если смена пароля успешна.
                            case ("success"):
                            {
                                // 6. Вывод всплывающего окна о смене пароля.
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
            // Если нажата кнопка регистрации.
            else if (v.getId() == R.id.returnButton)
            {
                // Осуществляется переход в LogInActivity с передачей введённого email.
                Intent SignUpIntent = new Intent(PasswordRecoveryActivity.this, LogInActivity.class)
                        .putExtra("email", emailEditText.getText().toString());
                startActivity(SignUpIntent);
            }
            else
                System.out.println("Unknown button");
        }
    };
}