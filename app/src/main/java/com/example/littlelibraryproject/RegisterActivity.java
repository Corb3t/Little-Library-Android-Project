package com.example.littlelibraryproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    TextView textViewRegisterMessage, textViewRegisterEnterEmailPrompt;
    TextView textViewRegisterEnterPasswordPrompt, textViewRegisterReenterPasswordPrompt;
    EditText editTextRegisterEnterEmail, editTextRegisterEnterPassword;
    EditText editTextRegisterReenterPassword;
    Button buttonRegisterStartPrompt;

    private FirebaseAuth mAuth;

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

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Users");

        final String email = editTextRegisterEnterEmail.getText().toString();
        final String password = editTextRegisterEnterPassword.getText().toString();

        if (view == buttonRegisterStartPrompt) {

            if (!(password.equals(editTextRegisterReenterPassword.getText().toString()))) {

                Toast.makeText(this, "Your entered password is inconsistent", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    User NewUser = new User(email, "John Doe", "Horror", "Library1", "None");
                                    myRef.push().setValue(NewUser);

                                    Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(loginIntent);

                                } else {
                                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();

                                }

                            }
                        });
            }
        }

    }
}
