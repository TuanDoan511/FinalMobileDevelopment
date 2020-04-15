package com.example.finalproject;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.finalproject.models.UpLoad;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;


public class DangBan_Fragment extends Fragment {
    private static final  int PICK_IMAGE_REQUEST =1;

    ProgressBar progressBar;
    Uri imageUri;

    ArrayList<String> data;
    ArrayAdapter<String> adapter;
    Spinner spinner, spinner2;
    Button btnXacNhan;
    TextInputLayout txtGia, txtDienTich,txtTieuDe;
    Long Long_Gia,Long_DienTich;
    ImageView imageView;
    DatabaseReference mDatabaseRef;
    StorageReference mStorageRef;
    String loai_BDS;
    EditText txtMota;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dangban, container, false);
        btnXacNhan = rootView.findViewById(R.id.btn_xacnhan_DangBan);
        spinner = rootView.findViewById(R.id.spinner);
        txtDienTich = rootView.findViewById(R.id.txtDienTich_layout);
        txtGia = rootView.findViewById(R.id.txtGia_layout);
        txtTieuDe = rootView.findViewById(R.id.txtTieuDe_layout);
        progressBar = rootView.findViewById(R.id.progressBar2);
        imageView = rootView.findViewById(R.id.imageView2);
        txtMota = rootView.findViewById(R.id.txtMoTa);
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        genMockData();
        adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loai_BDS = data.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!valideGia() | !valideDienTich() | !valideTieuDe()) {
                    return;
                }
                String String_Gia = txtGia.getEditText().getText().toString();
                Long_Gia=Long.parseLong(String_Gia);
                String String_DienTich = txtDienTich.getEditText().getText().toString();
                Long_DienTich=Long.parseLong(String_DienTich);
                uploadFile();

            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        return rootView;

    }
    private String getFileExtension(Uri uri){ //to get extension of image
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mine = MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(cR.getType(uri));
    }
    private  void uploadFile(){
        if(imageUri!=null){
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));
            fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(0);
                        }
                    },5000);
                    Toast.makeText(getActivity().getApplicationContext(),"Tải lên thành công",Toast.LENGTH_LONG).show();
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while(!urlTask.isSuccessful());
                    Uri downloadUrl = urlTask.getResult();

                    UpLoad upLoad = new UpLoad(
                            txtTieuDe.getEditText().getText().toString().trim(),
                            Long_Gia,
                            Long_DienTich,
                            loai_BDS,
                            downloadUrl.toString(),
                            txtMota.getText().toString().trim(),
                            Calendar.getInstance().getTime()
                    );
                    String uploadID=mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uploadID).setValue(upLoad);
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity().getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double process = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int)process);
                        }
                    });
        }else {
            Toast.makeText(getActivity().getApplicationContext(),"No file selected",Toast.LENGTH_LONG).show();
        }

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null && data.getData()!=null ){
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(imageView);
        }

    }

    private boolean valideGia() {
        String gia = txtGia.getEditText().getText().toString().trim();

        if (gia.isEmpty()) {
            txtGia.setError("Không thể để trống");
            return false;
        } else {
            txtGia.setError(null);
            return true;
        }
    }

    private boolean valideDienTich() {
        String dientich = txtDienTich.getEditText().getText().toString().trim();

        if (dientich.isEmpty()) {
            txtDienTich.setError("Không thể để trống");
            return false;
        } else {
            txtDienTich.setError(null);
            return true;
        }
    }
    private boolean valideTieuDe() {
        String tieude = txtTieuDe.getEditText().getText().toString().trim();

        if (tieude.isEmpty()) {
            txtTieuDe.setError("Không thể để trống");
            return false;
        } else {
            txtTieuDe.setError(null);
            return true;
        }
    }
    private void genMockData() {
        data = new ArrayList<>(Arrays.asList("Căn hộ/Chung cư", "Nhà ở", "Đất", "Văn phòng, mặt bằng kinh doanh"));

    }
}

