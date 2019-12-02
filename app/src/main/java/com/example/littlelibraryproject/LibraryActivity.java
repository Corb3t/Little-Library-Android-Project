package com.example.littlelibraryproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LibraryActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        button = (Button) findViewById(R.id.buttonRegisterStartPrompt);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openAddPhotoActivity();
            }
        });
    }


    public void openAddPhotoActivity() {
        Intent intent = new Intent(this, AddPhoto2activity.class);
        startActivity(intent);
    }


}
