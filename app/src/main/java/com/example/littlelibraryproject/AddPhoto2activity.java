package com.example.littlelibraryproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPhoto2activity<mPhotoFile> extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    Button buttonCamera;
    ImageView mImageView;

    Uri image_uri;
    private int IMAGE_CAPTURE_CODE=1001;
    private int ALBUM_RESULT_CODE=1002;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_add_photo2activity );


        mImageView =findViewById ( R.id.mImageView );
        buttonCamera=findViewById ( R.id.buttonCamera );

        //button click
        buttonCamera.setOnClickListener ( new View.OnClickListener () {



            @Override
            public void onClick(View v) {
                //if system ps os >=marshmallow,request runtime permission
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                    if (checkSelfPermission ( Manifest.permission.CAMERA ) ==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission ( Manifest.permission.WRITE_EXTERNAL_STORAGE ) ==
                                    PackageManager.PERMISSION_DENIED) {

                        //permission not enable,request it
                        String[] permission = {Manifest.permission.CAMERA , Manifest.permission.WRITE_EXTERNAL_STORAGE};


                        //show popup to request permissions
                        requestPermissions ( permission , PERMISSION_CODE );
                    } else {
                        //permission already granted

                        openCamera ();

                    }
                }
                else  {
                    //system os<marshmallow
                    openCamera ();


                    }

            }
        } );


    }

    private void openCamera() {
        ContentValues values=new ContentValues (  );
        values.put( MediaStore.Images.Media.TITLE,"New Pictures" );
        values.put ( MediaStore.Images.Media.DESCRIPTION,"From the Camera" );
        image_uri=getContentResolver ().insert ( MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values );

        //Camera intent

        Intent cameraIntent = new Intent ( MediaStore.ACTION_IMAGE_CAPTURE );
        cameraIntent.putExtra ( MediaStore.EXTRA_OUTPUT,image_uri );
        startActivityForResult ( cameraIntent,IMAGE_CAPTURE_CODE );

    }
    //handing permission result
    @Override
    public void onRequestPermissionsResult(int requestCode , @NonNull String[] permissions , @NonNull int[] grantResults) {
        super.onRequestPermissionsResult ( requestCode , permissions , grantResults );
        //this methods is calles, when user presses allow or deny from permission request popup
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length>0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED){
                    //permission from popup was granted
                    openCamera ();

                }
                else {
                    //permission from popup was denied
                    Toast.makeText ( this,"Permission Denied",Toast.LENGTH_SHORT ).show ();
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode , int resultCode , @Nullable Intent data) {
        //called when image captured from camera
        super.onActivityResult ( requestCode , resultCode , data );
        if (resultCode == RESULT_OK) {
            //set the image captured to our imageview
            mImageView.setImageURI ( image_uri );
        }
    }



    //open album
    private void openSysAlbum() {
        Intent albumIntent = new Intent ( Intent.ACTION_PICK );
        albumIntent.setDataAndType ( MediaStore.Images.Media.EXTERNAL_CONTENT_URI , "image/*" );
        startActivityForResult ( albumIntent , ALBUM_RESULT_CODE );



    }

}
