package com.example.zeynep.cozcozdurapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class anaSayfa extends AppCompatActivity {
    Button btnStartFragment1;
    Button btnStartFragment2;
    Button btnStartFragment3;
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_sayfa);
        getControlView();
        setClickForView();
      
    }
    private void getControlView() {
        btnStartFragment1 = (Button) findViewById(R.id.btnStartFragment1);
        btnStartFragment2 = (Button) findViewById(R.id.btnStartFragment2);
        btnStartFragment3 = (Button) findViewById(R.id.btnStartFragment3);
    }

    private void setClickForView() {
        btnStartFragment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fragment1 isimli fragment eklenir.
                addFragment(new Fragment1());
            }
        });
        btnStartFragment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fragment2 isimli fragment eklenir.
                addFragment(new TestCoz());
            }
        });
        btnStartFragment3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fragment3 isimli fragment eklenir.
                addFragment(new CozCozdur());
            }
        });
    }

    public void addFragment(Fragment comingFragment) {
        //Gelen Fragment deÄŸeri deÄŸiÅŸkene aktarÄ±lÄ±r.
        fragment = comingFragment;
        //FragmentManager: Activity iÃ§erisinde fragment bileÅŸenlerini yÃ¶netebilmek iÃ§in kullanÄ±lÄ±r.
        //AyrÄ±ca FragmentTransaction aÃ§mak iÃ§in FragmentManager sÄ±nÄ±fÄ± kullanÄ±lÄ±r.
        fragmentManager = getFragmentManager();
        //FragmentTransaction: Bu sÄ±nÄ±fÄ±n bir Ã¶rneÄŸini alabilmek iÃ§in FragmentManager kullanÄ±lÄ±r.
        //AÅŸaÄŸÄ±daki kod ile FragmentTransaction sÄ±nÄ±fÄ±nÄ±n bir Ã¶rneÄŸinin elde ederiz.
        fragmentTransaction = fragmentManager.beginTransaction();
        //SeÃ§ilen fragment sÄ±nÄ±fÄ± ekli olan fragment ile deÄŸiÅŸtirilir.
        fragmentTransaction.replace(R.id.fragmentArea, fragment);
        //fragmentTransaction Ã¼zerinde deÄŸiÅŸiklik yaptÄ±k. Bu deÄŸiÅŸikliÄŸi uygulamak iÃ§in commit kullanÄ±lÄ±r.
        fragmentTransaction.commit();
    }
}
