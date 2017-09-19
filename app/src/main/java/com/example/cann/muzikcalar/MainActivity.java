package com.example.cann.muzikcalar;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cann.muzikcalar.Envarter.PlayPauseDrawable;
import com.example.cann.muzikcalar.Envarter.SarkiBulGetir;
import com.example.cann.muzikcalar.Envarter.Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener {

    private ImageView Tusum;
    private ImageButton btnPlay;
    private ImageButton btnForward;
    private ImageButton btnBackward;
    private ImageButton btnNext;
    private ImageButton btnPrevious;
    private ImageButton btnPlaylist;
    private ImageButton btnRepeat;
    private ImageButton btnShuffle;
    private SeekBar songProgressBar;
    private TextView songTitleLabel;
    private TextView songCurrentDurationLabel;
    private TextView songTotalDurationLabel;
    private MediaPlayer mp;
    private Handler mHandler = new Handler();
    private Utilities utils;
    private int seekForwardTime = 5000; // 5000 milliseconds
    private int seekBackwardTime = 5000; // 5000 milliseconds
    private int currentSongIndex = 0;
    private boolean isShuffle = false;
    private boolean isRepeat = false;
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    private ImageButton RadyoTusum;
    private ImageView   DigerSayfaTusum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnForward = (ImageButton) findViewById(R.id.btnForward);
        btnBackward = (ImageButton) findViewById(R.id.btnBackward);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnPrevious = (ImageButton) findViewById(R.id.btnPrevious);
        btnPlaylist = (ImageButton) findViewById(R.id.btnPlaylist);
        btnRepeat = (ImageButton) findViewById(R.id.btnRepeat);
        btnShuffle = (ImageButton) findViewById(R.id.btnShuffle);
        songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);
        songTitleLabel = (TextView) findViewById(R.id.songTitle);
        songCurrentDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);
        songTotalDurationLabel = (TextView) findViewById(R.id.songTotalDurationLabel);
        RadyoTusum = (ImageButton) findViewById(R.id.btnRadyo);
        DigerSayfaTusum = (ImageView) findViewById(R.id.btnDigerSayfa);

        mp = new MediaPlayer();
        utils = new Utilities();

        SarkiBulGetir bulGetir = new SarkiBulGetir();
        songsList = bulGetir.SarkiGetir();



        final PlayPauseDrawable BuyukTus = new PlayPauseDrawable(60, 0XFFE91E63, 0XFFffffff, 300);
        Tusum = (ImageView) findViewById(R.id.AnaTus);
        Tusum.setImageDrawable(BuyukTus);
        RadyoTusum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(getApplicationContext(),RadyoSayfasi.class);
                startActivity(i);
            }
        });



        DigerSayfaTusum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),DigerSayfa.class);
                startActivity(i);
            }
        });



        Tusum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuyukTus.toggle();
                if(BuyukTus.getAnimationMode()==true)
                {
                    Log.d("Anlık","true");
                    playSong(0);
                }
                else {
                    Log.d("anlık", "false");
                    mp.pause();
                    btnPlay.setImageResource(R.drawable.btn_play);
                }


            }
        });


        btnPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (mp.isPlaying()) {
                    if (mp != null) {
                        mp.pause();
                        btnPlay.setImageResource(R.drawable.btn_play);
                        BuyukTus.animatePlay();
                    }
                } else {
                    if (mp != null) {
                        mp.start();
                        btnPlay.setImageResource(R.drawable.btn_pause);
                        BuyukTus.animatePause();
                    }
                }

            }
        });

        btnForward.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                int currentPosition = mp.getCurrentPosition();
                if (currentPosition + seekForwardTime <= mp.getDuration()) {

                    mp.seekTo(currentPosition + seekForwardTime);
                } else {
                    mp.seekTo(mp.getDuration());
                }
            }
        });

        btnBackward.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                int currentPosition = mp.getCurrentPosition();
                if (currentPosition - seekBackwardTime >= 0) {
                    mp.seekTo(currentPosition - seekBackwardTime);
                } else {
                    mp.seekTo(0);
                }

            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (currentSongIndex < (songsList.size() - 1)) {
                    playSong(currentSongIndex + 1);
                    currentSongIndex = currentSongIndex + 1;
                } else {
                    playSong(0);
                    currentSongIndex = 0;
                }

            }
        });


        btnPrevious.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (currentSongIndex > 0) {
                    playSong(currentSongIndex - 1);
                    currentSongIndex = currentSongIndex - 1;
                } else {
                    playSong(songsList.size() - 1);
                    currentSongIndex = songsList.size() - 1;
                }

            }
        });


        btnRepeat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (isRepeat) {
                    isRepeat = false;
                    Toast.makeText(getApplicationContext(), "Repeat is OFF", Toast.LENGTH_SHORT).show();
                    btnRepeat.setImageResource(R.drawable.btn_repeat);
                } else {
                    isRepeat = true;
                    Toast.makeText(getApplicationContext(), "Repeat is ON", Toast.LENGTH_SHORT).show();
                    isShuffle = false;
                    btnRepeat.setImageResource(R.drawable.btn_repeat_focused);
                    btnShuffle.setImageResource(R.drawable.btn_shuffle);
                }
            }
        });


        btnShuffle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (isShuffle) {
                    isShuffle = false;
                    Toast.makeText(getApplicationContext(), "Shuffle is OFF", Toast.LENGTH_SHORT).show();
                    btnShuffle.setImageResource(R.drawable.btn_shuffle);
                } else {
                    // make repeat to true
                    isShuffle = true;
                    Toast.makeText(getApplicationContext(), "Shuffle is ON", Toast.LENGTH_SHORT).show();
                    // make shuffle to false
                    isRepeat = false;
                    btnShuffle.setImageResource(R.drawable.btn_shuffle_focused);
                    btnRepeat.setImageResource(R.drawable.btn_repeat);
                }
            }
        });


        btnPlaylist.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(), OynatmaListesi.class);
                startActivityForResult(i, 100);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 100){
            currentSongIndex = data.getExtras().getInt("id");
            playSong(currentSongIndex);
        }

    }

    public void playSong(int songIndex) {
        if (songsList.size() < songIndex + 1) return;
        try {
            mp.reset();
            mp.setDataSource(songsList.get(songIndex).get("songPath"));
            mp.prepare();
            mp.start();
            String songTitle = songsList.get(songIndex).get("songTitle");
            songTitleLabel.setText(songTitle);
            btnPlay.setImageResource(R.drawable.btn_pause);
            songProgressBar.setProgress(0);
            songProgressBar.setMax(100);


            updateProgressBar();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void updateProgressBar() {

        mHandler.postDelayed(mUpdateTimeTask, 100);
    }


    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mp.getDuration();
            long currentDuration = mp.getCurrentPosition();
            songTotalDurationLabel.setText("" + utils.milliSecondsToTimer(totalDuration));
            songCurrentDurationLabel.setText("" + utils.milliSecondsToTimer(currentDuration));
            int progress = (int) (utils.getProgressPercentage(currentDuration, totalDuration));
            songProgressBar.setProgress(progress);
            mHandler.postDelayed(this, 100);
        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {

    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = mp.getDuration();
        int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);
        mp.seekTo(currentPosition);
        updateProgressBar();
    }

    @Override
    public void onCompletion(MediaPlayer arg0) {

        if (isRepeat) {
            playSong(currentSongIndex);
        } else if (isShuffle) {
            Random rand = new Random();
            currentSongIndex = rand.nextInt((songsList.size() - 1) - 0 + 1) + 0;
            playSong(currentSongIndex);
        } else {
            if (currentSongIndex < (songsList.size() - 1)) {
                playSong(currentSongIndex + 1);
                currentSongIndex = currentSongIndex + 1;
            } else {
                playSong(0);
                currentSongIndex = 0;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.release();
    }
}




