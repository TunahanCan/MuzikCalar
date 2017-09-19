package com.example.cann.muzikcalar;

import android.database.DataSetObserver;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.cann.muzikcalar.Envarter.RadyoAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class RadyoSayfasi extends AppCompatActivity {
    ImageButton PlayPauseBtn;
    ImageButton NextBtn;
    ImageButton PrevisionBtn;
    private MediaPlayer RadyoDinle;
    ListView RadyoListesi;
    ArrayList<HashMap<String, String>> radyoList = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> song;

    MediaPlayer radyodinle = new MediaPlayer();
    private int GecerliDizin = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radyo_sayfasi);
        RadyoDinle = new MediaPlayer();
        PlayPauseBtn = (ImageButton) findViewById(R.id.btnRadyoPlay);
        NextBtn = (ImageButton) findViewById(R.id.btnRadyoNext);
        PrevisionBtn = (ImageButton) findViewById(R.id.btnRadyoPrevious);
        RadyoListesi = (ListView) findViewById(R.id.RadyoListesi);

        song = new HashMap<>();
        song.put("radyoPath", "http://108.61.73.117:8124/");
        song.put("radyoTitle", "Kanal1");
        radyoList.add(song);

        song = new HashMap<>();
        song.put("radyoPath", "http://us1.internet-radio.com:11094/");
        song.put("radyoTitle", " Smooth Jazz");
        radyoList.add(song);

        song = new HashMap<>();
        song.put("radyoPath", "http://176.31.115.196:8214/");
        song.put("radyoTitle", " kanal2");
        radyoList.add(song);

        song = new HashMap<>();
        song.put("radyoPath", "http://canli.bestradyo.org:8004/");
        song.put("radyoTitle", " BestRadyo");
        radyoList.add(song);

        song = new HashMap<>();
        song.put("radyoPath", "http://us2.internet-radio.com:8071/");
        song.put("radyoTitle", " SketDOWN Sterio");
        radyoList.add(song);


        RadyoAdapter adapter = new RadyoAdapter(this, radyoList);
        RadyoListesi.setAdapter(adapter);


        RadyoListesi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int i = (int) RadyoListesi.getItemIdAtPosition(position);
                try {
                    GecerliDizin = i;
                    PlayRadyo(i);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                PlayPauseBtn.setImageResource(R.drawable.btn_pause);
            }
        });


        PlayPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radyodinle.isPlaying()) {
                    radyodinle.pause();
                    PlayPauseBtn.setImageResource(R.drawable.btn_play);
                } else {
                    try {
                        PlayRadyo(3);
                        GecerliDizin = 3;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    PlayPauseBtn.setImageResource(R.drawable.btn_pause);
                }

            }
        });

        NextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (GecerliDizin < (radyoList.size() - 1)) {
                    try {
                        PlayRadyo(GecerliDizin + 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    GecerliDizin = GecerliDizin + 1;
                } else {
                    try {
                        PlayRadyo(GecerliDizin + 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    GecerliDizin = 0;
                }

            }
        });


        PrevisionBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (GecerliDizin > 0) {
                    try {
                        PlayRadyo(GecerliDizin - 1);
                        GecerliDizin = GecerliDizin - 1;
                    } catch (IOException E) {
                        E.printStackTrace();
                    }

                } else {
                    try {
                        PlayRadyo(radyoList.size() - 1);
                        GecerliDizin = radyoList.size() - 1;
                    } catch (IOException E) {
                        E.printStackTrace();
                    }

                }

            }
        });


    }

    public void PlayRadyo(int gelen) throws IOException {
        radyodinle.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            radyodinle.setDataSource(radyoList.get(gelen).get("radyoPath"));
            radyodinle.prepare();
            radyodinle.start();


        } catch (IllegalArgumentException e) {
            e.printStackTrace();

        } catch (SecurityException e) {
            Log.e("hata", "SecurityException");
        } catch (IllegalStateException e) {
            Log.e("hata", "IllegalStateException");
        } catch (IOException e) {
            Log.e("hata", "IOException");
        }

    }
}


