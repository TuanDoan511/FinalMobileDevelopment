package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finalproject.models.Province;
import com.example.finalproject.models.UpLoad;
import com.example.finalproject.models.User;
import com.example.finalproject.models.Ward;
import com.example.finalproject.models.districts;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class modify_activity extends AppCompatActivity {
    ProgressBar progressBar;
    ArrayList<String> data;
    ArrayAdapter<String> adapter;
    Spinner spinner, spinner2;
    Button btnXacNhan,btnSelectPhotos,btnDiaDiem;
    TextInputLayout txtGia, txtDienTich,txtTieuDe;
    Long Long_Gia,Long_DienTich;
    DatabaseReference mDatabaseRef,sDatabaseRef;
    StorageReference mStorageRef;
    String loai_BDS;
    EditText txtMota;
    RecyclerView recyclerView;
    String id_baiDang;
    PhotoAdapter photoAdapter;
    UrlAdapter urlAdapter;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    String imageEncoded;
    List<String> imagesEncodedList;
    ArrayList<Uri> mArrayUri;
    List<String> FinalList ;
    TextInputEditText editTextDienTich,editTextTieuDe,editTextGia;
    String tempURI;
    List<String> listofUrl;
    ArrayList<UpLoad> mData;
    int PICK_IMAGE_REQUEST =1;
    private User user ;
    Province province;
    districts d;
    Ward ward;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangban);
        btnXacNhan = findViewById(R.id.btn_xacnhan_DangBan);
        spinner = findViewById(R.id.spinner);
        txtDienTich =findViewById(R.id.txtDienTich_layout);
        txtGia = findViewById(R.id.txtGia_layout);
        txtTieuDe = findViewById(R.id.txtTieuDe_layout);
        progressBar = findViewById(R.id.progressBar2);
        recyclerView = findViewById(R.id.recyclerView_PhotoDangBan);
        btnSelectPhotos =findViewById(R.id.buttonSelectPhotos);
        txtMota = findViewById(R.id.txtMoTa);
        btnDiaDiem = findViewById(R.id.btnDiaDIem_DangBan);
        user = MainActivity.user;
        final Intent intent = getIntent();
        final String key = intent.getStringExtra("KEY");
        id_baiDang = key;
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mData = new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads").child(key);
        sDatabaseRef =FirebaseDatabase.getInstance().getReference("uploads");
        editTextTieuDe = findViewById(R.id.txtTieuDe);
        editTextDienTich=findViewById(R.id.txtDienTich);
        editTextGia=findViewById(R.id.txtGia);
        genMockData();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        RecyclerViewLayoutManager
                = new LinearLayoutManager(
                this);
        HorizontalLayout
                = new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView.setLayoutManager(HorizontalLayout);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                UpLoad upLoad = dataSnapshot.getValue(UpLoad.class);

                listofUrl= upLoad.getmImageUrl();
                editTextDienTich.setText(upLoad.getDienTich()+"");
                editTextGia.setText(upLoad.getGiaBan()+"");
                editTextTieuDe.setText(upLoad.getTieuDe());
                txtMota.setText(upLoad.getMoTa());
                urlAdapter = new UrlAdapter(modify_activity.this,listofUrl);


                recyclerView.setLayoutManager(HorizontalLayout);
                recyclerView.setAdapter(urlAdapter);
                for (int i=0;i<data.size();i++){
                    if(upLoad.getLoaiBDS().equals(data.get(i))){
                        spinner.setSelection(i);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
                listofUrl =null;
                openFileChooser();

            }
        });

    }
    private String getFileExtension(Uri uri){ //to get extension of image
        ContentResolver cR = modify_activity.this.getContentResolver();
        MimeTypeMap mine = MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(cR.getType(uri));
    }
    private  void uploadFile(){
        FinalList = new ArrayList<String>();
        FinalList.clear();
        if(mArrayUri!=null && listofUrl ==null){
            final LoadingDialog loadingDialog = new LoadingDialog(modify_activity.this);
            loadingDialog.startLoadingDialog();
            for(int uploads=0;uploads<mArrayUri.size();uploads++){
                StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()+"."+getFileExtension(mArrayUri.get(uploads)));
                Uri Uri_image = mArrayUri.get(uploads);

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
                        Toast.makeText(modify_activity.this,"Tải lên thành công " + (finalUploads2+1) + " ảnh",Toast.LENGTH_SHORT).show();
                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!urlTask.isSuccessful());
                        Uri downloadUrl = urlTask.getResult();
                        tempURI = downloadUrl.toString();
                        FinalList.add(tempURI);
                        if(finalUploads1==mArrayUri.size()-1){
                            UpLoad upLoad = new UpLoad(
                                    province,
                                    d,
                                    ward,
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
                            loadingDialog.dismissDialog();
                            sDatabaseRef.child(id_baiDang).setValue(upLoad);
                        }
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(modify_activity.this,e.getMessage(),Toast.LENGTH_LONG).show();

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
            final LoadingDialog loadingDialog = new LoadingDialog(modify_activity.this);
            loadingDialog.startLoadingDialog();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(modify_activity.this,"Tải lên thành công",Toast.LENGTH_SHORT).show();
                    loadingDialog.dismissDialog();
                }
            },5000);
            UpLoad upLoad = new UpLoad(
                    province,
                    d,
                    ward,
                    user.getUserUid(),
                    id_baiDang,
                    txtTieuDe.getEditText().getText().toString().trim(),
                    Long_Gia,
                    Long_DienTich,
                    loai_BDS,
                    listofUrl,
                    txtMota.getText().toString().trim(),
                    Calendar.getInstance().getTime()
            );

            sDatabaseRef.child(id_baiDang).setValue(upLoad);
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
                    Cursor cursor = modify_activity.this.getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded  = cursor.getString(columnIndex);
                    cursor.close();

                    mArrayUri = new ArrayList<Uri>();
                    mArrayUri.add(mImageUri);
                    photoAdapter = new PhotoAdapter(modify_activity.this,mArrayUri);
                    RecyclerViewLayoutManager
                            = new LinearLayoutManager(
                            modify_activity.this);
                    HorizontalLayout
                            = new LinearLayoutManager(
                            modify_activity.this,
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
                            Cursor cursor = modify_activity.this.getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded  = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                            photoAdapter = new PhotoAdapter(modify_activity.this,mArrayUri);
                            RecyclerViewLayoutManager
                                    = new LinearLayoutManager(
                                    modify_activity.this);
                            HorizontalLayout
                                    = new LinearLayoutManager(
                                    modify_activity.this,
                                    LinearLayoutManager.HORIZONTAL,
                                    false);
                            recyclerView.setLayoutManager(HorizontalLayout);
                            recyclerView.setAdapter(photoAdapter);

                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }
            }
            else if(requestCode==12) {//open location activity
                if (resultCode == 1) {
                    province = (Province) data.getSerializableExtra("province");
                    d = (districts) data.getSerializableExtra("district");
                    ward = (Ward) data.getSerializableExtra("ward");

                    btnDiaDiem.setText(province.name + ", " + d.name + ", " + ward.name);
                    btnXacNhan.setEnabled(true);
                } else if (resultCode == 2) {
                    province = (Province) data.getSerializableExtra("province");
                    d = (districts) data.getSerializableExtra("district");
                    Toast.makeText(modify_activity.this, "Chưa chọn phường/xã \n Vui lòng chọn phường/xã để tiếp tục", Toast.LENGTH_LONG).show();
                    btnDiaDiem.setText(province.name + ", " + d.name);
                    btnDiaDiem.setEnabled(false);
                } else if (resultCode == 3) {
                    province = (Province) data.getSerializableExtra("province");
                    btnDiaDiem.setText(province.name);
                    Toast.makeText(modify_activity.this, "Chưa chọn quận/huyện \n Vui lòng chọn quận/huyện để tiếp tục", Toast.LENGTH_LONG).show();
                    btnDiaDiem.setEnabled(false);
                } else if (resultCode == 4) {
                    Toast.makeText(modify_activity.this, "Chưa chọn tỉnh/thành \n Vui lòng chọn  tỉnh/thành để tiếp tục", Toast.LENGTH_LONG).show();
                    btnDiaDiem.setEnabled(false);
                }

            }
            else {
                Toast.makeText(modify_activity.this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(modify_activity.this, "Something went wrong", Toast.LENGTH_LONG)
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
