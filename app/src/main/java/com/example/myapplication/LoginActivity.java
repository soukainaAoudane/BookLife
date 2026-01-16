package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputEditText nameEditText = findViewById(R.id.nameEditText);
        MaterialButton loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();

            if (name.isEmpty()) {
                nameEditText.setError("Veuillez entrer votre nom");
            } else {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("USER_NAME", name);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
}