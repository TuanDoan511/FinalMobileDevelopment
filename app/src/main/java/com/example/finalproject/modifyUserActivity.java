package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class modifyUserActivity extends AppCompatActivity {
    EditText curr_password, new_password, retype_password;
    Button change_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user);
        curr_password = findViewById(R.id.current_password);
        new_password = findViewById(R.id.new_password);
        retype_password = findViewById(R.id.re_type_new_password);
        change_btn = findViewById(R.id.change_btn);

        change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curr_pass = curr_password.getText().toString();
                final String new_pass = new_password.getText().toString();
                String retype = retype_password.getText().toString();

                if (TextUtils.isEmpty(curr_pass)) {
                    curr_password.setError("Please input current password");
                    return;
                }
                else if (TextUtils.isEmpty(new_pass)) {
                    new_password.setError("Please input new password");
                    return;
                }
                else if (new_pass.length() < 6) {
                    new_password.setError("Password must be >= 6 Characters");
                    return;
                }
                else if (TextUtils.isEmpty(retype)) {
                    retype_password.setError("Please input retype your new password");
                    return;
                }
                else if (!retype.equals(new_pass)) {
                    retype_password.setError("password does not match");
                    return;
                }
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                AuthCredential credential = EmailAuthProvider.getCredential(MainActivity.user.email, curr_pass);

                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    user.updatePassword(new_pass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(modifyUserActivity.this, "Password updated", Toast.LENGTH_SHORT).show();
                                                finish();
                                            } else {
                                                Toast.makeText(modifyUserActivity.this, "Error password not updated", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    curr_password.setError("Your password not true");
                                }
                            }
                        });
            }
        });
    }
}
