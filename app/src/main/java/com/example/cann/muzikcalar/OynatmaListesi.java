package com.example.cann.muzikcalar;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.cann.muzikcalar.Envarter.MusicListAdaptor;
import com.example.cann.muzikcalar.Envarter.SarkiBulGetir;

public class OynatmaListesi extends AppCompatActivity {

    private ListView listem ;
    private SarkiBulGetir scanFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oynatma_listesi);
        listem = (ListView) findViewById(R.id.SarkilarListesi);
        ArrayList<HashMap<String, String>> songsList;
        scanFile = new SarkiBulGetir();
        songsList = scanFile.SarkiGetir();
        MusicListAdaptor adaptor = new MusicListAdaptor(this, songsList);
        listem.setAdapter(adaptor);

        listem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int i = (int) listem.getItemIdAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("id", i);
                setResult(100,intent);
                finish();

            }
        });
    }
}

