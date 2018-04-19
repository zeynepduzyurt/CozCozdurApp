package com.example.zeynep.cozcozdurapp;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class anaSayfa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_sayfa);

      
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.my_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.testcoz){
            Intent intent = new Intent(anaSayfa.this,TestCoz.class);
            startActivity(intent);
        }

        if(id==R.id.cozcozdur){
            Intent intent = new Intent(anaSayfa.this,TestCoz.class);
            startActivity(intent);
        }

        if(id==R.id.profil){
            Intent intent = new Intent(anaSayfa.this,TestCoz.class);
            startActivity(intent);
        }

        if(id==R.id.bizeulasin){
            Intent intent = new Intent(anaSayfa.this,TestCoz.class);
            startActivity(intent);
        }
        return true;
    }
}
