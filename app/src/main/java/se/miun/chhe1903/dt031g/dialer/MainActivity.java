package se.miun.chhe1903.dt031g.dialer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void goToDial(View view){
        Intent dialIntent = new Intent(this, DialActivity.class);
        startActivity(dialIntent);
    }
    public void goToCallList(View view){
        Intent callListIntent = new Intent(this, CallListActivity.class);
        startActivity(callListIntent);
    }
    public void goToSettings(View view){
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
   }
   public void goToMap(View view){
        Intent mapsIntent = new Intent(this, MapsActivity.class);
        startActivity(mapsIntent);
   }
   public void showAboutDialog(View view){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("About")
                .setMessage("This app is supposed to mimic the keypad on a phone.\n\n" +
                        "This app will consist of:\n\n" +
                        "\u2022 Enter numbers to dial\n" +
                        "\u2022 See previously dialed numbers\n" +
                        "\u2022 Change the keypad settings\n" +
                        "\u2022 Show on a map where previously calls are dialed from\n"
                )
                .setPositiveButton("OK", null);
        Dialog dialog = dialogBuilder.create();
        dialog.show();
   }
}