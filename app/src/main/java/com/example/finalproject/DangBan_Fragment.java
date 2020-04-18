package com.example.finalproject;

import android.content.ClipData;
import android.content.ContentResolver;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.models.UpLoad;
import com.example.finalproject.models.User;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.ArrayList;
import java.util.Arrays;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


import static android.app.Activity.RESULT_OK;


public class DangBan_Fragment extends Fragment {
    private static final  int PICK_IMAGE_REQUEST =1;

    ProgressBar progressBar;
    ArrayList<String> data;
    ArrayAdapter<String> adapter;
    Spinner spinner, spinner2;
    Button btnXacNhan,btnSelectPhotos;
    TextInputLayout txtGia, txtDienTich,txtTieuDe;
    Long Long_Gia,Long_DienTich;
    DatabaseReference mDatabaseRef;
    StorageReference mStorageRef;
    String loai_BDS;
    EditText txtMota;
    RecyclerView recyclerView;
    String id_baiDang;
    PhotoAdapter photoAdapter;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    String imageEncoded;
    List<String> imagesEncodedList;
    ArrayList<Uri> mArrayUri;
    List<String> FinalList ;
    String tempURI;


    private User user ;
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
        recyclerView = rootView.findViewById(R.id.recyclerView_PhotoDangBan);
        btnSelectPhotos = rootView.findViewById(R.id.buttonSelectPhotos);
        txtMota = rootView.findViewById(R.id.txtMoTa);
        user = MainActivity.getUser();
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        mDatabaseRef =FirebaseDatabase.getInstance().getReference("uploads");
        id_baiDang = mDatabaseRef.push().getKey();
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
        btnSelectPhotos.setOnClickListener(new View.OnClickListener() {
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
        FinalList = new ArrayList<String>();
        if(mArrayUri!=null){
            for(int uploads=0;uploads<mArrayUri.size();uploads++){
                StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()+"."+getFileExtension(mArrayUri.get(uploads)));
                Uri Uri_image = mArrayUri.get(uploads);

                final int finalUploads = uploads;
                final int finalUploads1 = uploads;
                final int finalUploads2 = uploads;
                fileReference.putFile(Uri_image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setProgress(0);
                            }
                        },5000);
                        Toast.makeText(getActivity().getApplicationContext(),"Tải lên thành công" + (finalUploads2+1) + "ảnh",Toast.LENGTH_SHORT).show();
                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!urlTask.isSuccessful());
                        Uri downloadUrl = urlTask.getResult();
                        tempURI = downloadUrl.toString();
                        FinalList.add(tempURI);
                        if(finalUploads1==mArrayUri.size()-1){
                            UpLoad upLoad = new UpLoad(
                                    user.getUserUid(),
                                    id_baiDang,
                                    txtTieuDe.getEditText().getText().toString().trim(),
                                    Long_Gia,
                                    Long_DienTich,
                                    loai_BDS,
                                    FinalList,
                                    txtMota.getText().toString().trim(),
                                    Calendar.getInstance().getTime()
                            );
                            mDatabaseRef.child(id_baiDang).setValue(upLoad);
                        }
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
            }
            }
        else {
            Toast.makeText(getActivity().getApplicationContext(),"No file selected",Toast.LENGTH_LONG).show();
        }
        }


    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                imagesEncodedList = new ArrayList<String>();
                if(data.getData()!=null){

                    Uri mImageUri=data.getData();

                    // Get the cursor
                    Cursor cursor = getActivity().getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded  = cursor.getString(columnIndex);
                    cursor.close();

                    mArrayUri = new ArrayList<Uri>();
                    mArrayUri.add(mImageUri);
                    photoAdapter = new PhotoAdapter(getActivity().getApplicationContext(),mArrayUri);
                    RecyclerViewLayoutManager
                            = new LinearLayoutManager(
                            getActivity().getApplicationContext());
                    HorizontalLayout
                            = new LinearLayoutManager(
                            getActivity().getApplicationContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false);
                    recyclerView.setLayoutManager(HorizontalLayout);
                    recyclerView.setAdapter(photoAdapter);

                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded  = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                            photoAdapter = new PhotoAdapter(getActivity().getApplicationContext(),mArrayUri);
                            RecyclerViewLayoutManager
                                    = new LinearLayoutManager(
                                    getActivity().getApplicationContext());
                            HorizontalLayout
                                    = new LinearLayoutManager(
                                    getActivity().getApplicationContext(),
                                    LinearLayoutManager.HORIZONTAL,
                                    false);
                            recyclerView.setLayoutManager(HorizontalLayout);
                            recyclerView.setAdapter(photoAdapter);

                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

        super.onActivityResult(requestCode, resultCode, data);
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

