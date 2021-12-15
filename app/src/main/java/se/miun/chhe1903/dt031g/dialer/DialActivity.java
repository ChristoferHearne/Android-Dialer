package se.miun.chhe1903.dt031g.dialer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class DialActivity extends AppCompatActivity {
    private final int MY_PERMISSIONS_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);
        addOnClickListenerToCallButton(this);
    }

    private void addOnClickListenerToCallButton(Context context){
        // GUI Element
        Button callButton = findViewById(R.id.call_button);
        // Boolean for holding if permissions are granted

        Boolean permissionsGranted = ContextCompat.checkSelfPermission(DialActivity.this, Manifest.permission.CALL_PHONE) + ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                + ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;

        // GET shared preferences
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();

        // Set on click for the callButton
        callButton.setOnClickListener(view -> {
            if (permissionsGranted){
                Toast.makeText(DialActivity.this, "You have already granted the permission to call", Toast.LENGTH_SHORT);
                performCallOperation(context);
            }
            else{
                if (requestPermissions()){
                    performCallOperation(context);
                }
            }
        });
    }

    private void performCallOperation(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        TextView numberInput = findViewById(R.id.number_input);
        if (SettingsActivity.shouldStoreNumbers(context)){
            editor.putString("store_number_" + numberInput.getText().toString(), numberInput.getText().toString()); // I put the stored number as key to make sure it has unique ID
            editor.commit();
        }
        // Go to ACTION_DIAL
        Uri call = Uri.parse("tel:" + Uri.encode(numberInput.getText().toString().replace("\uFF0A", "*")));
        Intent callIntent = new Intent(Intent.ACTION_CALL, call);
        context.startActivity(callIntent);
    }


    private boolean requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        + ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        + ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
            || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)){
                new AlertDialog.Builder(this)
                        .setTitle("Permission Needed")
                        .setMessage("Access fine location, Access coarse location and Call permissions are required to do this task")
                        .setPositiveButton("Ok", (dialog, which) -> {
                            ActivityCompat.requestPermissions(
                                    this,
                                    new String[]{
                                            Manifest.permission.CALL_PHONE,
                                            Manifest.permission.ACCESS_COARSE_LOCATION,
                                            Manifest.permission.ACCESS_FINE_LOCATION
                                    }, MY_PERMISSIONS_CODE
                            );
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .create().show();
            }
            else{
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.CALL_PHONE,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION
                        },
                        MY_PERMISSIONS_CODE
                );
                if (ContextCompat.checkSelfPermission(DialActivity.this, Manifest.permission.CALL_PHONE) + ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        + ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                    return true;
                }
            }
        }
        else{
            Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT);
            return true;
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_CODE:
                if (grantResults.length > 0 && (grantResults[0] + grantResults[1] + grantResults[2] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT);
                }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SoundPlayer.getInstance(this).destroy();
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dial_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settings_item) {
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtra("caller", "DialActivity");
            SoundPlayer.getInstance(getApplicationContext()).destroy();
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.download_voices_item){
            startActivity(new Intent(this, DownloadActivity.class));
        }
        return(super.onOptionsItemSelected(item));
    }
}