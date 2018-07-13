package com.example.android.tabswithswipes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutionException;


public class EditProfileActivity extends AppCompatActivity {

    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static EditText editName, editBio;
    private static final int CAMERA_REQUEST = 1888;
    public static final int GET_FROM_GALLERY = 3;
    private static Button changeImage;
    private static SimpleDraweeView edit_profile_pic;
    private Bitmap bitmap;
    private static Uri downloadUri;
    private static Uri downloadUrl;
    private static ImageView saveChanges;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        edit_profile_pic = (SimpleDraweeView) findViewById(R.id.edit_profile_image);
        editName = (EditText) findViewById(R.id.edit_name);
        editBio = (EditText) findViewById(R.id.edit_bio);
        changeImage = (Button) findViewById(R.id.change_profile_image);
        saveChanges = (ImageView) findViewById(R.id.saveChanges);
        final SharedPreferences preferences = getSharedPreferences("MyPref", 0);
        final SharedPreferences.Editor editor = preferences.edit();
        if(preferences.contains("userImage")){
            edit_profile_pic.setImageURI(preferences.getString("userImage", ""));
            Log.d("onCreate Image", preferences.getString("userImage", ""));
        }
        mStorageRef = FirebaseStorage.getInstance().getReference();
        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditProfileActivity.this);
                alertDialog.setTitle("IMAGE UPLOAD");
                alertDialog.setMessage("How do you want to upload?");
                alertDialog.setIcon(R.drawable.upload);
                alertDialog.setPositiveButton("CAMERA UPLOAD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        //onRequestPermissionsResult();
                        Intent cameraIntent = new
                                Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                });
                alertDialog.setNegativeButton("GALLERY UPLOAD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("hell", "GTH");
                        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);

                    }
                });
                alertDialog.show();
            }
        });

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editName.getText().toString() == null && editBio.getText().toString() == null) {
                    finish();
                }
                if (editName.getText().toString() != null && editName.getText().toString() != "" && editName.getText().toString().length() > 0) {
                    editor.putString("userName", editName.getText().toString().trim());
                    FireBaseMethods.updateName(editName.getText().toString().trim());
                }
                Log.d("msgbio", editBio.getText().toString());
                if (editBio.getText().toString() != null && editBio.getText().toString() != "" && editBio.getText().toString().length() > 0) {
                    Log.d("msgbio", editBio.getText().toString());
                    editor.putString("userBio", editBio.getText().toString().trim());
                    Log.d("checkUser", "bio is null");
                    FireBaseMethods.updateBio(editBio.getText().toString().trim());
                }
                if(downloadUrl!=null){

                    editor.putString("userImage", downloadUrl.toString());
                    Log.d("downloadFucks", "Uri is not null");
                    Log.d("downloaded urlSHARE", downloadUrl.toString());
                    FireBaseMethods.updateprofpic(downloadUrl.toString());
                }

                editor.commit();
                finish();
            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onactres", "someerror");

        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();

            if(selectedImage!=null){
                final ProgressDialog pd = new ProgressDialog(this);
                pd.setTitle("Uploading your new profile pic");
                pd.show();
                final StorageReference profileref = mStorageRef.child("images/rivers.jpg");
                profileref.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        pd.dismiss();


                        profileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                             @Override
                                                                             public void onSuccess(Uri uri) {
                                                                                 downloadUrl = uri;
                                                                                 Log.d("downloaded url", downloadUrl.toString());
                                                                                 //Do what you want with the url
                                                                             }
                                                                             //Toast.makeText(EditProfileActivity.this,"Upload Done",Toast.LENGTH_LONG).show();
                                                                         });
                        Toast.makeText(EditProfileActivity.this, "Image successfully uploaded", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(EditProfileActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                        Log.d("UPLDERROR", e.getMessage());
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                        pd.setMessage("Uploaded"+(int)progress + "%");
                    }
                });




            }

            Log.d("ASYNCTSKENTER","MADARCHOD");
               /*
            try {
                Uri uristr = new AsyncTask<Uri, Void, Uri>(){

                    ProgressDialog pd = new ProgressDialog(EditProfileActivity.this);
                    @Override
                    protected  void onPreExecute(){
                        Log.d("PREE", "pre execute starts");
                        super.onPreExecute();
                        pd.setTitle("Uploading");
                        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        pd.setMessage("uploading");
                        pd.show();
                    }

                    @Override
                    protected Uri doInBackground(Uri... uris) {
                        Log.d("DOINB", "do in bg starts");
                        Uri selectedImage = uris[0];
                        final StorageReference profileref = mStorageRef.child("images/rivers.jpg");
                        //String selectedimage_string  = strings[0];
                        UploadTask uploadTask = profileref.putFile(selectedImage);

                        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    Log.d("exception error", "Kuch to hua hai");
                                    throw task.getException();
                                }

                                // Continue with the task to get the download URL
                                return profileref.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    downloadUri = task.getResult();
                                    Log.d("uridownload", downloadUri.toString());
                                    //return downloadUri;
                                } else {
                                    // Handle failures
                                    // ...
                                    Log.d("exception2 error", "Kuch to hua hai");
                                }
                            }
                        });
                        Log.d("post", "do in bg mei issue");
                        return downloadUri;
                    }

                    @Override
                    protected  void  onPostExecute(Uri urireturn){
                        super.onPostExecute(urireturn);
                        Log.d("OPE","on post execute start");



                        synchronized (this){
                            try {
                                this.wait(4000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }



                        if(pd!=null){
                            pd.dismiss();
                            Log.d("pd is dismissed","DEMISE OF PD");
                            Toast.makeText(EditProfileActivity.this, "Uploading done", Toast.LENGTH_SHORT).show();
                        }
                    }

                }.execute(selectedImage).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
*/

            //Uri urig = getImageUri(this, bitmap);
            Log.d("FTShit",selectedImage.toString());
            edit_profile_pic.setImageURI(selectedImage);
        }else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Log.d("something crazy", "camera is crazy");
             Bitmap photo = (Bitmap) data.getExtras().get("data");
             Log.d("chirag", photo.toString());
             if(photo == null){
                 Log.d("nullphoto", "photo is null");
             }
             Uri uric = getImageUri(this, photo);
            final StorageReference profileref = mStorageRef.child("images/rivers.jpg");
            //String selectedimage_string  = strings[0];
            UploadTask uploadTask = profileref.putFile(uric);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        Log.d("some error","kyu hua hai tu madarchod?");
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return profileref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        downloadUri = task.getResult();
                        Log.d("uridownload", downloadUri.toString());
                    } else {
                        Log.d("some error","kyu hua hai tu bsdk?");
                        // Handle failures
                        // ...
                    }
                }
            });
             edit_profile_pic.setImageBitmap(photo);
        }

    }




    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        Log.d("fuckmebitch",path);
        return Uri.parse(path);
    }

}
