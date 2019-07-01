package com.example.mediaplayer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SeekBar seekVolume;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bach);
        inicializarSeekBar();

    }

    private void inicializarSeekBar(){

        seekVolume = findViewById(R.id.seekBarVolume);

        //configurar o audio manager
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //recupera o vol max e vol atual
        int volMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volAtual = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        //configurar o volMax para a nossa seekBar
        seekVolume.setMax( volMax );

        //conf volAtual
        seekVolume.setProgress( volAtual );

        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

    }

    public void executarSom (View view){

        if(mediaPlayer != null){
            mediaPlayer.start();

        }

    }

    public void pausarSom(View view){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }

    }

    public void pararSom(View view){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();

            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bach);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();//para libertar os recursos de media de mem
            mediaPlayer = null;
        }
    }
}
