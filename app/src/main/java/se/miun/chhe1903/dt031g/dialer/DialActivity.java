package se.miun.chhe1903.dt031g.dialer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);
    }
    @Override
    public void onPause()
    {
        super.onPause();
        if(isFinishing())
        {
            SoundPlayer soundPlayer = SoundPlayer.getInstance(this.getApplicationContext());
            soundPlayer.destroy(); // I think this just creates a new instance of soundPlayer and then deletes it. Cant figure out how to destroy in the onclick without getting instance problems on first click.
        }
    }
}