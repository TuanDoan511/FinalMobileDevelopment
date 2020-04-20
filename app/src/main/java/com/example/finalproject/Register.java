package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.models.User;
import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Register extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 22;
    EditText mFullName, mEmail, mPassword, mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    RadioGroup mSex;
    Button mChoosingImage;
    ImageView avata;
    Uri AvatarUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        mPhone = findViewById(R.id.phone);
        mRegisterBtn = findViewById(R.id.registerBtn);
        mLoginBtn = findViewById(R.id.createText);
        mSex = findViewById(R.id.sex);
        mChoosingImage = findViewById(R.id.choosing_image_btn);
        avata = findViewById(R.id.register_image);
        final StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("userImages");

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        final String[] sex_choosing = {""};

        if (fAuth.getCurrentUser() != null){
            Intent intent = new Intent();
            setResult(Activity.RESULT_CANCELED, intent);
            finish();
        }

        mSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checked = findViewById(checkedId);
                sex_choosing[0] = (String) checked.getText();
            }
        });

        mChoosingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                final String fullName = mFullName.getText().toString();
                final String phone = mPhone.getText().toString();


                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required");
                    return;
                }
                else if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required");
                    return;
                }
                else if (password.length() < 6) {
                    mPassword.setError("Password must be >= 6 Characters");
                    return;
                }
                else if (TextUtils.isEmpty(sex_choosing[0])) {
                    Toast.makeText(Register.this, "Please choose your gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    mRegisterBtn.setEnabled(false);

                    fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull final Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                final String userUid = fAuth.getUid();

                                if (AvatarUri == null) {
                                    String avatar = "";
                                    if ("Male".equals(sex_choosing[0])) {
                                         avatar = "https://firebasestorage.googleapis.com/v0/b/finalproject-deb3d.appspot.com/o/userImages%2Fusers_user_male_icon.png?alt=media&token=efea1de3-7c4e-462d-aae0-30b7da650c0d";
                                    } else if ("Female".equals(sex_choosing[0])) {
                                        avatar = "https://firebasestorage.googleapis.com/v0/b/finalproject-deb3d.appspot.com/o/userImages%2Fbusiness_woman_01_512.png?alt=media&token=24c4889e-e978-42ae-adb5-6e48d258996e";
                                    }
                                    create_user_db(userUid, email, fullName, phone, sex_choosing[0], avatar);
                                }
                                else {
                                    final StorageReference fileReference = mStorageRef.child(userUid + "." + MimeTypeMap.getFileExtensionFromUrl(AvatarUri.toString()));

                                    fileReference.putFile(AvatarUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    String avatar = uri.toString();
                                                    create_user_db(userUid, email, fullName, phone, sex_choosing[0], avatar);
                                                }
                                            });
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Register.this, "Fail to upload your image", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }else {
                                Toast.makeText(Register.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                mRegisterBtn.setEnabled(true);
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });
    }

    private void create_user_db(String userUid, String email, String fullName, String phone, String gender, String avatar){
        User user = new User(userUid, email, fullName, phone, gender, avatar);
        final DatabaseReference user_root = FirebaseDatabase.getInstance().getReference("User");
        user_root.child(userUid).setValue(user);

        Intent intent = new Intent();
        intent.putExtra("user", user);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void SelectImage()
    {
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            AvatarUri = data.getData();
            avata.setVisibility(View.VISIBLE);
            avata.setImageURI(AvatarUri);
        }
    }
}
