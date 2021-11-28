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
    private int soundIDZero, soundIDOne, soundIDTwo, soundIDThree, soundIDFour, soundIDFive, soundIDSix, soundIDSeven,
    soundIDEight, soundIDNine, soundIDPound, soundIDStar;
    boolean soundIsLoaded = false;

    // Constructor
    private SoundPlayer(Context context) {
        initSoundPool(context);
    }
    // Singleton getInstance function
    public static SoundPlayer getInstance(Context context) {
        if (INSTANCE == null){
            INSTANCE = new SoundPlayer(context.getApplicationContext());
        }
        return INSTANCE;
    }
    // Functions
    public void playSound(DialPadButton dialPadButton){
        if (soundIsLoaded){
            switch(dialPadButton.getTitle()){
                case "0":
                    soundPool.play(soundIDZero, 25, 25, 1, 0, 1f);
                    break;
                case "1":
                    soundPool.play(soundIDOne, 1, 1, 1, 0, 1f);
                    break;
                case "2":
                    soundPool.play(soundIDTwo, 1, 1, 1, 0, 1f);
                    break;
                case "3":
                    soundPool.play(soundIDThree, 25, 25, 1, 0, 1f);
                    break;
                case "4":
                    soundPool.play(soundIDFour, 1, 1, 1, 0, 1f);
                    break;
                case "5":
                    soundPool.play(soundIDFive, 1, 1, 1, 0, 1f);
                    break;
                case "6":
                    soundPool.play(soundIDSix, 1, 1, 1, 0, 1f);
                    break;
                case "7":
                    soundPool.play(soundIDSeven, 1, 1, 1, 0, 1f);
                    break;
                case "8":
                    soundPool.play(soundIDEight, 1, 1, 1, 0, 1f);
                    break;
                case "9":
                    soundPool.play(soundIDNine, 1, 1, 1, 0, 1f);
                    break;
                case "*":
                    soundPool.play(soundIDStar, 1, 1, 1, 0, 1f);
                    break;
                case "#":
                    soundPool.play(soundIDPound, 1, 1, 1, 0, 1f);
                    break;
            }
        }
    }
    private void initSoundPool(Context context){
        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .build();
        soundIDZero = soundPool.load(context, R.raw.zero, 1);
        soundIDOne = soundPool.load(context, R.raw.one, 1);
        soundIDTwo = soundPool.load(context, R.raw.two, 1);
        soundIDThree = soundPool.load(context, R.raw.three, 1);
        soundIDFour = soundPool.load(context, R.raw.four, 1);
        soundIDFive = soundPool.load(context, R.raw.five, 1);
        soundIDSix = soundPool.load(context, R.raw.six, 1);
        soundIDSeven = soundPool.load(context, R.raw.seven, 1);
        soundIDEight = soundPool.load(context, R.raw.eight, 1);
        soundIDNine = soundPool.load(context, R.raw.nine, 1);
        soundIDPound = soundPool.load(context, R.raw.pound, 1);
        soundIDStar = soundPool.load(context, R.raw.star, 1);
        soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> soundIsLoaded = true);
    }
    public void destroy(){
        soundPool.release();
        soundPool = null;
        this.INSTANCE = null;
    }

}
