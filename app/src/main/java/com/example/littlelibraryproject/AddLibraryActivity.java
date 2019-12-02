package com.example.littlelibraryproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static java.lang.Double.parseDouble;

public class AddLibraryActivity extends AppCompatActivity implements View.OnClickListener{

    TextView textViewAddLibraryNamePrompt, textViewAddLibraryLatPrompt;
    TextView textViewAddLibraryLongPrompt, textViewLibraryWelcomePrompt;
    EditText editTextAddLibraryName, editTextAddLibraryLat;
    EditText editTextAddLibraryLong, editTextAddLibraryWelcome;
    Button buttonAddLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_library);

        textViewAddLibraryNamePrompt = findViewById(R.id.textViewAddLibraryNamePrompt);
        textViewAddLibraryLatPrompt = findViewById(R.id.textViewAddLibraryLatPrompt);
        textViewAddLibraryLongPrompt = findViewById(R.id.textViewAddLibraryLongPrompt);
        textViewLibraryWelcomePrompt = findViewById(R.id.textViewAddLibraryWelcomePrompt);
        editTextAddLibraryName = findViewById(R.id.editTextAddLibraryName);
        editTextAddLibraryLat = findViewById(R.id.editTextAddLibraryLat);
        editTextAddLibraryLong = findViewById(R.id.editTextAddLibraryLong);
        editTextAddLibraryWelcome = findViewById(R.id.editTextAddLibraryWelcome);
        buttonAddLibrary = findViewById(R.id.buttonAddLibrary);

        buttonAddLibrary.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Libraries");

        if (view == buttonAddLibrary) {

            try {
                String createLibraryName = editTextAddLibraryName.getText().toString();
                Double createLibraryLat = parseDouble(editTextAddLibraryLat.getText().toString());
                Double createLibraryLong = parseDouble(editTextAddLibraryLong.getText().toString());
                String createLibraryWelcome = editTextAddLibraryWelcome.getText().toString();

                Library createLibrary = new Library(createLibraryName, createLibraryLat, createLibraryLong,
                        createLibraryWelcome);

                myRef.push().setValue(createLibrary);

                Toast.makeText(this, "Library added successfully", Toast.LENGTH_SHORT).show();

            } catch(Exception e) {
                Toast.makeText(this, "Library failed to be added", Toast.LENGTH_SHORT).show();
                return;
            }

        }



    }
}
