package com.example.zeynep.cozcozdurapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BizeUlasin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bize_ulasin);

        final EditText mail_adres = (EditText) findViewById(R.id.etMailAdres);
        final EditText mail_konu = (EditText) findViewById(R.id.etKonu);
        final EditText mail_icerik = (EditText) findViewById(R.id.etMail_icerik);
        Button mail_gonder = (Button) findViewById(R.id.button);
        Button konum = (Button) findViewById(R.id.konum);

        mail_gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mailGonder(mail_adres.getText().toString(), mail_konu.getText().toString(), mail_icerik.getText().toString());
            }
        });
    }

    private void mailGonder(String adres, String konu, String icerik) {

        // String[]dizi={adres};


        Intent mailintent = new Intent(Intent.ACTION_SEND);
        mailintent.setType("message/rfc822");
        mailintent.putExtra(Intent.EXTRA_EMAIL, new String[]{adres});
        mailintent.putExtra(Intent.EXTRA_SUBJECT, konu);
        mailintent.putExtra(Intent.EXTRA_TEXT, icerik);

        try {
            startActivity(mailintent);
        } catch (ActivityNotFoundException hata) {

            Toast.makeText(getApplicationContext(), "Hata oluÅŸtu!!!\n" + hata.getMessage(), Toast.LENGTH_LONG).show();
        }


    }

    public void btn(View view) {    // Konumun Google Map ile gösterilmesi
        Intent googlemap = new Intent(Intent.ACTION_VIEW);
        googlemap.setData(Uri.parse("geo: 41.0332402,28.7890739?z=1934"));
        startActivity(googlemap);
    }


}
