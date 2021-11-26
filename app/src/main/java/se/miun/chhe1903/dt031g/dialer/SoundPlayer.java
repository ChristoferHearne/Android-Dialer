package se.miun.chhe1903.dt031g.dialer;

import android.content.Context;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;

import androidx.loader.content.Loader;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class SoundPlayer {
    // Instance variables
    private static SoundPlayer INSTANCE = null;
    private SoundPool soundPool;
    private int soundIDThree;
    private boolean soundIsLoaded;

    // Constructor
    private SoundPlayer(Context context) throws IllegalAccessException {
        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .build();
        soundIDThree = soundPool.load(context, R.raw.three, 1);
        soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> soundIsLoaded = true);
    }
    // Singleton getInstance function
    public static SoundPlayer getInstance(Context context) throws IllegalAccessException {
        if (INSTANCE == null){
            INSTANCE = new SoundPlayer(context.getApplicationContext());
        }
        return INSTANCE;
    }
    // Functions
    public void playSound(DialPadButton dialPadButton){
            switch(dialPadButton.getTitle()){
                case "0":
                    soundPool.play(R.raw.zero, 50, 50, 1, 0, 1f);
                    break;
                case "1":
                    soundPool.play(R.raw.one, 1, 1, 1, 0, 1f);
                    break;
                case "2":
                    soundPool.play(R.raw.two, 1, 1, 1, 0, 1f);
                    break;
                case "3":
                    soundPool.play(soundIDThree, 50, 50, 1, 0, 1f);
                    break;
                case "4":
                    soundPool.play(R.raw.four, 1, 1, 1, 0, 1f);
                    break;
                case "5":
                    soundPool.play(R.raw.five, 1, 1, 1, 0, 1f);
                    break;
                case "6":
                    soundPool.play(R.raw.six, 1, 1, 1, 0, 1f);
                    break;
                case "7":
                    soundPool.play(R.raw.seven, 1, 1, 1, 0, 1f);
                    break;
                case "8":
                    soundPool.play(R.raw.eight, 1, 1, 1, 0, 1f);
                    break;
                case "9":
                    soundPool.play(R.raw.nine, 1, 1, 1, 0, 1f);
                    break;
                case "*":
                    soundPool.play(R.raw.star, 1, 1, 1, 0, 1f);
                    break;
                case "#":
                    soundPool.play(R.raw.pound, 1, 1, 1, 0, 1f);
                    break;
            }
    }
    public void destroy(){
        soundPool.release();
        soundPool = null;
        this.INSTANCE = null;
    }

}
