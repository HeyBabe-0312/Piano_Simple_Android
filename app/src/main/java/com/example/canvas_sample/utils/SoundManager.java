package com.example.canvas_sample.utils;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.SparseIntArray;

import android.os.Handler;

import com.example.canvas_sample.R;

public class SoundManager {
    private SoundPool soundPool;
    private SparseIntArray soundPoolMap;
    private boolean mute = false;
    private Context mContext;

    private static final int MAX_STREAM = 10;
    private static final int STOP_DELAY_MILLIS = 10000;
    private Handler mHandler;

    private static SoundManager instance = null;
    public SoundManager(){
        soundPool = new SoundPool(MAX_STREAM, AudioManager.STREAM_MUSIC,0);
        soundPoolMap = new SparseIntArray();
        mHandler = new Handler();
    }
    public static SoundManager getInstance(){
        if (instance==null){
            instance = new SoundManager();
        }
        return instance;
    }
    public void initStreamTypeMedia(Activity activity){
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }
    public void addSound(int soundID){
        soundPoolMap.put(soundID,soundPool.load(mContext,soundID,1));
    }
    public void playSound(int soundID){
        if(mute){
            return;
        }
        boolean hasSound = soundPoolMap.indexOfKey(soundID) >= 0;

        if (!hasSound){
            return;
        }
        final int soundId = soundPool.play(soundPoolMap.get(soundID),1,1,1,0,1f);
        scheduleSoundStop(soundId);
    }
    private void scheduleSoundStop(final int soundID){
        mHandler.postDelayed(new Runnable(){
            public void run(){
                soundPool.stop(soundID);
            }
        },STOP_DELAY_MILLIS);
    }
    public void init(Context context){
        this.mContext = context;
        instance.initStreamTypeMedia((Activity)mContext);
        instance.addSound(R.raw.c3);
        instance.addSound(R.raw.c4);
        instance.addSound(R.raw.d3);
        instance.addSound(R.raw.d4);
        instance.addSound(R.raw.e3);
        instance.addSound(R.raw.e4);
        instance.addSound(R.raw.f3);
        instance.addSound(R.raw.f4);
        instance.addSound(R.raw.g3);
        instance.addSound(R.raw.g4);
        instance.addSound(R.raw.a3);
        instance.addSound(R.raw.a4);
        instance.addSound(R.raw.b3);
        instance.addSound(R.raw.b4);
        instance.addSound(R.raw.db3);
        instance.addSound(R.raw.db4);
        instance.addSound(R.raw.eb3);
        instance.addSound(R.raw.eb4);
        instance.addSound(R.raw.gb3);
        instance.addSound(R.raw.gb4);
        instance.addSound(R.raw.ab3);
        instance.addSound(R.raw.ab4);
        instance.addSound(R.raw.bb3);
        instance.addSound(R.raw.bb4);
    }
}
