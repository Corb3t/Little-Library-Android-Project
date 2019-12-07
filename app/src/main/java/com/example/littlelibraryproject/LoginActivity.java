package com.example.littlelibraryproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.littlelibraryproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonLoginLogin, buttonLoginRegister;

    EditText editTextLoginEmail, editTextLoginPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextLoginEmail = findViewById(R.id.editTextLoginEmail);
        editTextLoginPassword = findViewById(R.id.editTextLoginPassword);

        buttonLoginLogin = findViewById(R.id.buttonLoginLogin);
        buttonLoginRegister = findViewById(R.id.buttonLoginRegister);

        buttonLoginLogin.setOnClickListener(this);
        buttonLoginRegister.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {

        if (view == buttonLoginLogin){

            String email = editTextLoginEmail.getText().toString();
            String password = editTextLoginPassword.getText().toString();

            if (email.isEmpty() || email == null) {
                editTextLoginEmail.setError("Email cannot be empty");
                return;
            }

            if (password.isEmpty() || password == null) {
                editTextLoginPassword.setError("Password cannot be empty");
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Intent mainIntent = new Intent(LoginActivity.this, ProfileActivity.class);
                                startActivity(mainIntent);

                            } else {
                                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();


                            }

                        }
                    });
        }


        else if (view == buttonLoginRegister){

            Intent registerIntent = new Intent(this, RegisterActivity.class);
            startActivity(registerIntent);

        }





    }
}
