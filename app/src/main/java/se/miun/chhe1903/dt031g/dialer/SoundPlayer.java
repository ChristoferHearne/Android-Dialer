package se.miun.chhe1903.dt031g.dialer;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.SoundPool;

import androidx.preference.PreferenceManager;

import java.util.Map;

import se.miun.chhe1903.dt031g.dialer.Util;


public class SoundPlayer {
    // Instance variables
    private static SoundPlayer INSTANCE = null;
    private String selectedVoice;
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
                case "\uFF0A":
                    soundPool.play(soundIDStar, 1, 1, 1, 0, 1f);
                    break;
                case "#":
                    soundPool.play(soundIDPound, 1, 1, 1, 0, 1f);
                    break;
            }
        }
    }
    private void initSoundPool(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        selectedVoice = prefs.getString("select_voices", "mamacita_us");
        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .build();

        soundIDZero = soundPool.load(Util.getDirForVoice(context, selectedVoice + "/zero.mp3").toString(), 1);
        soundIDOne = soundPool.load(Util.getDirForVoice(context, selectedVoice + "/one.mp3").toString(), 1);
        soundIDTwo = soundPool.load(Util.getDirForVoice(context, selectedVoice + "/two.mp3").toString(), 1);
        soundIDThree = soundPool.load(Util.getDirForVoice(context, selectedVoice + "/three.mp3").toString(), 1);
        soundIDFour = soundPool.load(Util.getDirForVoice(context, selectedVoice + "/four.mp3").toString(), 1);
        soundIDFive = soundPool.load(Util.getDirForVoice(context, selectedVoice + "/five.mp3").toString(), 1);
        soundIDSix = soundPool.load(Util.getDirForVoice(context,  selectedVoice + "/six.mp3").toString(), 1);
        soundIDSeven = soundPool.load(Util.getDirForVoice(context, selectedVoice + "/seven.mp3").toString(), 1);
        soundIDEight = soundPool.load(Util.getDirForVoice(context, selectedVoice + "/eight.mp3").toString(), 1);
        soundIDNine = soundPool.load(Util.getDirForVoice(context, selectedVoice + "/nine.mp3").toString(), 1);
        soundIDPound = soundPool.load(Util.getDirForVoice(context, selectedVoice + "/pound.mp3").toString(), 1);
        soundIDStar = soundPool.load(Util.getDirForVoice(context, selectedVoice + "/star.mp3").toString(), 1);
        soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> soundIsLoaded = true);
    }
    public void destroy(){
        soundPool.release();
        soundPool = null;
        this.INSTANCE = null;
    }

}
