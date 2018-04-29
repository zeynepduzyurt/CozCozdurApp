package com.example.zeynep.cozcozdurapp;
// Yeni Parola almak için işlemler
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
import com.google.firebase.auth.FirebaseAuth;

public class YeniParolaActivity extends AppCompatActivity {
    private EditText email;
    private Button yeniParolagonder;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeni_parola);
        // email=(EditText)findViewById(R.id.uyeEmail);
        yeniParolagonder = (Button) findViewById(R.id.yeniParolaGonder);
        auth = FirebaseAuth.getInstance();


        yeniParolagonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString().trim();
                if (TextUtils.isEmpty(mail)) {
                    Toast.makeText(getApplication(), "Lütfen e-mail adresinizi giriniz!", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(YeniParolaActivity.this, "Yeni parola için gerekli bağlantı e-mail adresinize gönderildi!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(YeniParolaActivity.this, "Mail gönderme hatası!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
    }
}
