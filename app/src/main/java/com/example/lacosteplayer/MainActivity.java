package com.example.lacosteplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<AudioList> musics;
    MediaPlayer mPlayer;
    Button playButton, pauseButton;
    ImageView stopButton;
    int index = 0;
    SeekBar volum;
    AudioManager audioManager;
    TextView trackTitleTextView;
    ImageView trackImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.ListViewTrack);
        musics = new ArrayList<AudioList>();

        trackTitleTextView = findViewById(R.id.trackTitleTextView);
        trackImageView = findViewById(R.id.imageView1);

        setInizialedDate();

        AudioAdapter adapter = new AudioAdapter(this, R.layout.item_list, musics);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;
                setMedia();
            }
        });

        playButton = findViewById(R.id.playButton);
        pauseButton = findViewById(R.id.pauseButton);
        stopButton = findViewById(R.id.imageView1);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curValume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        volum = findViewById(R.id.volumeControl);
        volum.setMax(maxVolume);
        volum.setProgress(curValume);
        volum.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);
    }

    private void setMedia(){
        if(index == 0) mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.hirosima);
        if(index == 1) mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sahara);
        if(index == 2) mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sok);
        if(index == 3) mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.seti);
        if(index == 4) mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.temnota);
        if(index == 5) mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.atam);
        if(index == 6) mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.delishki);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlay();
            }
        });
        String trackTitle = musics.get(index).getName();
        trackTitleTextView.setText(trackTitle);

        int imageResource = musics.get(index).getImageResurse();
        trackImageView.setImageResource(imageResource);
    }
    private void stopPlay(){
        mPlayer.stop();
        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);
        try {
            mPlayer.prepare();
            mPlayer.seekTo(0);
            playButton.setEnabled(true);
        }
        catch (Throwable t) {
            Toast.makeText(this, "Выберете трек", Toast.LENGTH_SHORT).show();
        }
    }
    public void play(View view){
        try {
            mPlayer.start();
            playButton.setVisibility(View.INVISIBLE);
            pauseButton.setEnabled(true);
            stopButton.setEnabled(true);
        }
        catch (Throwable t) {
            Toast.makeText(this, "Выберете трек", Toast.LENGTH_SHORT).show();
        }
    }
    public void pause(View view){
        try {
            mPlayer.pause();
            playButton.setVisibility(View.VISIBLE);
            pauseButton.setEnabled(false);
            stopButton.setEnabled(true);
        }
        catch (Throwable t) {
            Toast.makeText(this, "Выберете трек", Toast.LENGTH_SHORT).show();
        }
    }
    public void stop(View view){
        stopPlay();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer.isPlaying()) {
            stopPlay();
        }
    }
    public void setInizialedDate(){
        musics.add(new AudioList("Тени Хиросимы", "Digital Fantazy",R.drawable.teni));
        musics.add(new AudioList("Сахарный человек", "СУПЕРЧУИТС",R.drawable.sahar));
        musics.add(new AudioList("Сок", "LIL CHILL",R.drawable.sok));
        musics.add(new AudioList("Сети", "Boys Don't cry",R.drawable.delishkiseti));
        musics.add(new AudioList("Проснулся в темноте", "Sigle Psychic Attak",R.drawable.temnota));
        musics.add(new AudioList("Атомы", "Digital Fantazy",R.drawable.atom));
        musics.add(new AudioList("Как делишки", "Boys Don't cry",R.drawable.delishkiseti));

    }

    public void next(View view) {
        stopPlay();
        index++;
        if (index >= musics.size()) {
            index = 0;
        }
        setMedia();
        mPlayer.start();
    }

    public void back(View view) {
        stopPlay();
        index--;
        if (index < 0) {
            index = musics.size() - 1;
        }
        setMedia();
        mPlayer.start();
    }
}