package com.example.littlelibraryproject;
//test
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class AddPhoto2activity<mPhotoFile, storageDir> extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    Button buttonCamera, buttonUpload;
    ImageView mImageView;

    Uri image_uri;
    private int IMAGE_CAPTURE_CODE = 1001;
    private int ALBUM_RESULT_CODE = 1002;

    FirebaseAuth mAuth = FirebaseAuth.getInstance ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_add_photo2activity );

        mImageView = findViewById ( R.id.mImageView );
        buttonCamera = findViewById ( R.id.buttonCamera );
        buttonUpload = findViewById ( R.id.buttonUpload );

// Get a non-default bucket from a custom FirebaseApp
        final FirebaseStorage storage;
        storage = FirebaseStorage.getInstance ( "gs://littlelibraryproject-dbdcb.appspot.com/" );



        //button click upload

        buttonUpload.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                // Create a storage reference from our app

                StorageReference storageRef = storage.getReference ();
                FirebaseUser user = mAuth.getCurrentUser ();
                String uid = user.getUid ();


// Create a reference to 'images/mountains.jpg'
                // StorageReference mountainImagesRef = storageRef.child("images/mountains.jpg/");
                StorageReference imageFolderoRef = storageRef.child ( "images" );
                StorageReference useridRef = imageFolderoRef.child ( uid );
                StorageReference imageRef = useridRef.child ( "mountain.ipg" );


// While the file names are the same, the references point to different files
                imageRef.getName ().equals ( imageRef.getName () );    // true
                imageRef.getPath ().equals ( imageRef.getPath () );    // false


                // Get the data from an ImageView as bytes
                mImageView.setDrawingCacheEnabled ( true );
                mImageView.buildDrawingCache ();
                Bitmap bitmap = ((BitmapDrawable) mImageView.getDrawable ()).getBitmap ();
                ByteArrayOutputStream baos = new ByteArrayOutputStream ();
                bitmap.compress ( Bitmap.CompressFormat.JPEG , 100 , baos );
                byte[] data = baos.toByteArray ();

                UploadTask uploadTask = imageRef.putBytes ( data );
                uploadTask.addOnFailureListener ( new OnFailureListener () {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                } ).addOnSuccessListener ( new OnSuccessListener<UploadTask.TaskSnapshot> () {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                    }
                } );


            }
        } );


        //button click camera
        buttonCamera.setOnClickListener ( new View.OnClickListener () {


            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
                } else {
                    //system os<marshmallow
                    openCamera ();


                }

            }
        } );


    }

    private void openCamera() {
        ContentValues values = new ContentValues ();
        values.put ( MediaStore.Images.Media.TITLE , "New Pictures" );
        values.put ( MediaStore.Images.Media.DESCRIPTION , "From the Camera" );
        image_uri = getContentResolver ().insert ( MediaStore.Images.Media.EXTERNAL_CONTENT_URI , values );

        //Camera intent

        Intent cameraIntent = new Intent ( MediaStore.ACTION_IMAGE_CAPTURE );
        cameraIntent.putExtra ( MediaStore.EXTRA_OUTPUT , image_uri );
        startActivityForResult ( cameraIntent , IMAGE_CAPTURE_CODE );

    }

    //handing permission result
    @Override
    public void onRequestPermissionsResult(int requestCode , @NonNull String[] permissions , @NonNull int[] grantResults) {
        super.onRequestPermissionsResult ( requestCode , permissions , grantResults );
        //this methods is calles, when user presses allow or deny from permission request popup
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    //permission from popup was granted
                    openCamera ();

                } else {
                    //permission from popup was denied
                    Toast.makeText ( this , "Permission Denied" , Toast.LENGTH_SHORT ).show ();
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

    private void handleSmallCameraPhoto(Intent intent) {
        Bundle extras = intent.getExtras ();
        Bitmap mImageBitmap = (Bitmap) extras.get ( "data" );
        mImageView.setImageBitmap ( mImageBitmap );
    }







    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.itemLogin) {
            Intent LoginIntent = new Intent(this, LoginActivity.class);
            startActivity(LoginIntent);

        } else if (item.getItemId() == R.id.itemMap) {
            Intent MapIntent = new Intent(this, MapsActivity.class);
            startActivity(MapIntent);
        } else if (item.getItemId() == R.id.itemUsers) {
            Intent UsersIntent = new Intent(this, ProfileActivity.class);
            startActivity(UsersIntent);
        } else if (item.getItemId() == R.id.itemLibrary) {
            Intent LibraryIntent = new Intent(this, LibraryActivity.class);
            startActivity(LibraryIntent);
        } else if (item.getItemId() == R.id.itemAddLibrary) {
            Intent AddLibraryIntent = new Intent(this, AddLibraryActivity.class);
            startActivity(AddLibraryIntent);
        }
        else if (item.getItemId() == R.id.itemLogOut){

            /// Implement log out funcitonality here
        }
        return super.onOptionsItemSelected(item);
    }
}

                //if system ps os >=marshmallow,request runtime permission

