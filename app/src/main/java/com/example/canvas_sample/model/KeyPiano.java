package com.example.canvas_sample.model;

import android.graphics.RectF;

public class KeyPiano {
    public int sound;
    public RectF rect;
    public boolean down;

    public KeyPiano(int sound, RectF rect, boolean down) {
        this.sound = sound;
        this.rect = rect;
        this.down = down;
    }
}
