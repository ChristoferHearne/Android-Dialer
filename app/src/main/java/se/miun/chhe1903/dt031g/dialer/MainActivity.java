package se.miun.chhe1903.dt031g.dialer;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) { // Starts up the Main Activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void goToDial(View view){ // When a user clicks the Dial-button
        // Navigates to the Dial-view
        Intent dialIntent = new Intent(this, DialActivity.class);
        startActivity(dialIntent);
    }
    public void goToCallList(View view){ // When a user clicks the Call List-button
        // Navigates to the Call List-view
        Intent callListIntent = new Intent(this, CallListActivity.class);
        startActivity(callListIntent);
    }
    public void goToSettings(View view){ // When a user clicks the Settings-button
        // Navigates to the Settings-view
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
   }
   public void goToMap(View view){ // When a user clicks the Map-button
        // Navigates to the Map-view
        Intent mapsIntent = new Intent(this, MapsActivity.class);
        startActivity(mapsIntent);
   }
   public void showAboutDialog(View view){ // When a user clicks the About-button
        // Shows a dialog with information about the app
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this); // Applies settings
        dialogBuilder.setTitle("About") //
                // Custom dialog message, could create a separate layout for this
                    // This still does the job and does not seem to be bad practice
                        // u2022 is a reference to a bullet point
                .setMessage("This app is supposed to mimic the keypad on a phone.\n\n" +
                        "This app will consist of:\n\n" +
                        "\u2022 Enter numbers to dial\n" +
                        "\u2022 See previously dialed numbers\n" +
                        "\u2022 Change the keypad settings\n" +
                        "\u2022 Show on a map where previously calls are dialed from\n"
                )
                .setPositiveButton("OK", null); // Set the dialog button
        Dialog dialog = dialogBuilder.create(); // Creates a new dialog based on the builder
        dialog.show(); // Shows the dialog
   }
}