package com.example.littlelibraryproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    TextView textViewRegisterMessage, textViewRegisterEnterEmailPrompt;
    TextView textViewRegisterEnterPasswordPrompt, textViewRegisterReenterPasswordPrompt;
    EditText editTextRegisterEnterEmail, editTextRegisterEnterPassword;
    EditText editTextRegisterReenterPassword;
    Button buttonRegisterStartPrompt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textViewRegisterMessage = findViewById(R.id.textViewRegisterMessage);
        textViewRegisterEnterEmailPrompt = findViewById(R.id.textViewRegisterEnterEmailPrompt);
        textViewRegisterEnterPasswordPrompt = findViewById(R.id.textViewRegisterEnterPasswordPrompt);
        textViewRegisterReenterPasswordPrompt = findViewById(R.id.textViewRegisterReenterPasswordPrompt);
        editTextRegisterEnterEmail = findViewById(R.id.editTextRegisterEnterEmail);
        editTextRegisterEnterPassword = findViewById(R.id.editTextRegisterEnterPassword);
        editTextRegisterReenterPassword= findViewById(R.id.editTextRegisterReenterPassword);
        buttonRegisterStartPrompt = findViewById(R.id.buttonRegisterStartPrompt);

        buttonRegisterStartPrompt.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Users");

        if (view == buttonRegisterStartPrompt) {

        }
    }
}
