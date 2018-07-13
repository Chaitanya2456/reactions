package com.example.android.tabswithswipes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.sql.Ref;

public class CreatePoll extends AppCompatActivity {

    private static ImageView closeShare, addImage, loadedImage;
    private static TextView post;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference myRef = database.getReference();
    private FrameLayout imageFrameLayout;
    private EditText editCaption;
    private String uploadtext;
    private String uploadUri;
    private ProgressDialog pd;
    private FirebaseAuth auth;
    private Uri downloadUrl;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReferenceFromUrl("gs://reactions-4f81f.appspot.com");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_create_poll);
        closeShare = (ImageView)findViewById(R.id.close_share);
        addImage = (ImageView)findViewById(R.id.add_image);
        loadedImage = (ImageView)findViewById(R.id.loaded_image);
        post = (TextView)findViewById(R.id.share_poll);
        imageFrameLayout = (FrameLayout)findViewById(R.id.frame_image_layout);
        editCaption = (EditText)findViewById(R.id.editCaption);
        pd = new ProgressDialog(this);
        pd.setMessage("Uploading...");

        closeShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                imageFrameLayout.setBackgroundColor(Color.WHITE);
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filePath != null) {
                    if ((editCaption == null || editCaption.getText().toString().length() == 0 || editCaption.getText().toString().trim().equals("")) && (loadedImage.getDrawable() == null)) {
                        Log.d("posting", "none");
                        Toast.makeText(CreatePoll.this, "Text and image can't be empty", Toast.LENGTH_SHORT);
                    } else if (loadedImage.getDrawable() == null) {
                        Log.d("posting", "only text");
                        uploadtext = editCaption.getText().toString();
                        uploadUri = "";
                    } else if (editCaption == null || editCaption.getText().toString().length() == 0 || editCaption.getText().toString().trim().equals("")) {
                        Log.d("posting", "only img");
                        uploadtext = "";
                        uploadUri = filePath.toString();
                    } else {
                        Log.d("posting", "both");
                        uploadUri = filePath.toString();
                        uploadtext = editCaption.getText().toString();
                    }


                    pd.show();
                    final StorageReference childRef = storageReference.child("image.jpg");
                    UploadTask uploadTask = childRef.putFile(filePath);
                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                pd.dismiss();
                                Toast.makeText(CreatePoll.this, "Failed to upload", Toast.LENGTH_SHORT).show();
                                throw task.getException();
                            }
                            return childRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                downloadUrl = task.getResult();
                                pd.dismiss();
                                Log.d("gotUri", downloadUrl.toString());
                                String key = myRef.child("posts").push().getKey();
                                Log.d("generatedkey", key);
                                Post p = new Post(uploadtext, downloadUrl.toString(), auth.getCurrentUser().getUid());
                                Log.d("uploadtext", p.getUploadUri());
                                myRef.child("posts").child(key).setValue(p);
                                Log.d("postdone", "POST DONE");
                                String key2 = myRef.child("exposts").child(auth.getCurrentUser().getUid()).push().getKey();
                                myRef.child("exposts").child(auth.getCurrentUser().getUid()).child(key2).setValue(p);
                                finish();
                            } else {
                                Log.d("PoatingFailure", "Failure");
                            }
                        }
                    });
                    Toast.makeText(CreatePoll.this, "Posting the poll", Toast.LENGTH_SHORT).show();
                }

            }
            });
        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                loadedImage.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}

