package com.example.kwinam.isafeyou.isafeyou.Activity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kwinam.isafeyou.R;
import com.pkmmte.view.CircularImageView;

/**
 * Created by Taewoong on 2017-08-13.
 */

public class WhistleActivity extends AppCompatActivity{
    SoundPool sound;
    int soundId, streamId;
    boolean clicked = false;
    TextView tv;
    CircularImageView whistle;
    AudioManager audio;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whistle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sound = new SoundPool.Builder().setMaxStreams(1).build();
        } else {
            sound = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
        }
        soundId = sound.load(this, R.raw.whistle,1);
        audio = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        tv = (TextView)findViewById(R.id.section_label);
        whistle = (CircularImageView)findViewById(R.id.whistlebutton);
        whistle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!clicked){
                    audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    audio.setStreamVolume(AudioManager.STREAM_MUSIC,
                            audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC), AudioManager.FLAG_PLAY_SOUND);
                    streamId = sound.play(soundId, 1.0F,1.0F,1,-1,1.0F);
                    clicked = true;
                    tv.setText("한번 더 누르면 경보음이 멈춥니다.");
                }
                else{
                    sound.stop(streamId);
                    tv.setText("호루라기를 누르면 경보음이 울립니다.");
                    clicked = false;
                }
            }
        });
    }
}
