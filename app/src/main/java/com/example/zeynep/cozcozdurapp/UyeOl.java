package com.example.zeynep.cozcozdurapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UyeOl extends AppCompatActivity {
    private EditText uyeEmail, uyeParola;
    private Button yeniUyeButton,uyeGirisButton;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uye_ol);
        auth=FirebaseAuth.getInstance();

        uyeEmail=(EditText)findViewById(R.id.uyeEmail);
        uyeParola=(EditText)findViewById(R.id.uyeParola);
        yeniUyeButton=(Button)findViewById(R.id.yeniUyeButton);
        uyeGirisButton=(Button)findViewById(R.id.uyeGirisButton);

        yeniUyeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=uyeEmail.getText().toString();
                String parola=uyeParola.getText().toString();

                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Lütfen e-mailinizi giriniz!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(parola)) {
                    Toast.makeText(getApplicationContext(), "Lütfen parolanızı giriniz!", Toast.LENGTH_SHORT).show();
                }
                if(parola.length()<6) {
                    Toast.makeText(getApplicationContext(), "Parola en az 6 haneli olmalıdır", Toast.LENGTH_SHORT).show();
                }

                auth.createUserWithEmailAndPassword(email,parola).addOnCompleteListener(UyeOl.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()) {
                            Toast.makeText(UyeOl.this, "İşlem başarısız", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            startActivity(new Intent(UyeOl.this,MainActivity.class));
                            finish();
                        }
                    }
                });


            }
        });



        uyeGirisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UyeOl.this,MainActivity.class));
                finish();
            }
        });

    }
}
