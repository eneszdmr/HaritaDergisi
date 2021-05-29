package com.hgm.haritagenelmudurlugu.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hgm.haritagenelmudurlugu.R;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText username, password;
    private Button login;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        progressDialog = new ProgressDialog(this);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login();
            }
        });


    }
// user and password check
    private void login() {
        String user = username.getText().toString();
        String pass = password.getText().toString();

        if (TextUtils.isEmpty(user)) {

            username.setFocusable(true);
            username.setError("Email Giriniz !");
            username.requestFocus();
            return;
        } else if (TextUtils.isEmpty(pass)) {
            password.setFocusable(true);
            password.setError("Parola Giriniz !");
            password.requestFocus();
            return;
        }else{
        progressDialog.setMessage("Lütfen Bekleyiniz...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        mAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {


                    Intent intent = new Intent(LoginActivity.this, adminActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Hatalı Giriş", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(LoginActivity.this, adminActivity.class);
            startActivity(intent);
            finish();
        }
    }
}