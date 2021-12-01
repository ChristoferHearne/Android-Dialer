package se.miun.chhe1903.dt031g.dialer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dial_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.settings_item:
                startActivity(new Intent(this, SettingsActivity.class));
        }
        return(super.onOptionsItemSelected(item));
    }
}