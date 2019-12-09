package com.example.littlelibraryproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AddPhoto3Activity extends AppCompatActivity {
    ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6;
    Button buttonAlbum;
    private static final int PICK_IMAGE=100;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_add_photo3 );




            imageView1 = findViewById ( R.id.imageView1);
            imageView2 = findViewById ( R.id.imageView2);
            imageView3 = findViewById ( R.id.imageView3);
            imageView4 = findViewById ( R.id.imageView4);
            imageView5 = findViewById ( R.id.imageView5);
            imageView6 = findViewById ( R.id.imageView6);
            buttonAlbum = findViewById ( R.id. buttonAlbum );

            buttonAlbum.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    openGallery();
                }
            } );

        }

        private void openGallery(){
            Intent gallery = new Intent ( Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI );
            startActivityForResult ( gallery, PICK_IMAGE );
        }


        @Override
        protected void onActivityResult(int requestCode , int resultCode , @Nullable Intent data) {
            super.onActivityResult ( requestCode , resultCode , data );
            if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
                imageUri = data.getData ();
                imageView1.setImageURI ( imageUri );
            }

}




    }

