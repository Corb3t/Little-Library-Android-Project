package com.example.littlelibraryproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.littlelibraryproject.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonLoginLogin, buttonLoginGoogle, buttonLoginRegister;

    EditText editTextLoginEmail, editTextLoginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextLoginEmail = findViewById(R.id.editTextLoginEmail);
        editTextLoginPassword = findViewById(R.id.editTextLoginPassword);

        buttonLoginLogin = findViewById(R.id.buttonLoginLogin);
        buttonLoginGoogle = findViewById(R.id.buttonLoginGoogle);
        buttonLoginRegister = findViewById(R.id.buttonLoginRegister);

        buttonLoginLogin.setOnClickListener(this);
        buttonLoginGoogle.setOnClickListener(this);
        buttonLoginRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view == buttonLoginLogin){


        }

        else if (view == buttonLoginGoogle){

        }

        else if (view == buttonLoginRegister){

            Intent registerIntent = new Intent(this, RegisterActivity.class);
            startActivity(registerIntent);



        }





    }
}
