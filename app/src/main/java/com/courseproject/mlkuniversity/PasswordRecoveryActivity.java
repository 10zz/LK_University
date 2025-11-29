package com.courseproject.mlkuniversity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PasswordRecoveryActivity extends AppCompatActivity
{
    EditText emailEntry;
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

        emailEntry = findViewById(R.id.emailEditText);
        sendButton = findViewById(R.id.sendButton);
        returnButton = findViewById(R.id.returnButton);

        sendButton.setOnClickListener(buttonListener);
        returnButton.setOnClickListener(buttonListener);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null)
            emailEntry.setText(arguments.getString("email"));
    }

    View.OnClickListener buttonListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            // Если нажата кнопка входа.
            if (v.getId() == R.id.sendButton)
            {
                // TODO: Я хз как это делать.
            }
            // Если нажата кнопка регистрации.
            else if (v.getId() == R.id.returnButton)
            {
                // Осуществляется переход в LogInActivity с передачей введённого email.
                Intent SignUpIntent = new Intent(PasswordRecoveryActivity.this, LogInActivity.class)
                        .putExtra("email", emailEntry.getText().toString());
                startActivity(SignUpIntent);
            }
            else
                System.out.println("Unknown button");
        }
    };
}