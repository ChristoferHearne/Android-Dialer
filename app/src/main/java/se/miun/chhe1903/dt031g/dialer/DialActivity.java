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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import se.miun.chhe1903.dt031g.dialer.data.Number;
import se.miun.chhe1903.dt031g.dialer.data.NumberDao;
import se.miun.chhe1903.dt031g.dialer.data.NumberDatabase;


public class DialActivity extends AppCompatActivity {
    private final int MY_PERMISSIONS_CODE = 1;
    private NumberDatabase db;
    private NumberDao numberDao;
    private List<Number> numbersList;
    private FusedLocationProviderClient locationClient;
    private double locationLatitude;
    private double locationLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);
        addOnClickListenerToCallButton(this);
        db = NumberDatabase.getInstance(getApplicationContext());
        numberDao = db.numberDao();
        locationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    locationLatitude = location.getLatitude();
                    locationLongitude = location.getLongitude();
                }
            });
        }


    }

    private void addOnClickListenerToCallButton(Context context){
        // GUI Element
        Button callButton = findViewById(R.id.call_button);
        // Boolean for holding if permissions are granted


        // GET shared preferences
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();

        // Set on click for the callButton
        callButton.setOnClickListener(view -> {
            Boolean callPermissionGranted = ContextCompat.checkSelfPermission(DialActivity.this, Manifest.permission.CALL_PHONE)  == PackageManager.PERMISSION_GRANTED;
            if (callPermissionGranted){
                Toast.makeText(DialActivity.this, "You have already granted the permission to call", Toast.LENGTH_SHORT);
                performCallOperation(context);
            }
            else{
                requestPermissions();
            }
        });
    }

    private void performCallOperation(Context context){
        TextView numberInput = findViewById(R.id.number_input);
        if (SettingsActivity.shouldStoreNumbers(context)){
            Number storedNumber = new Number();
            storedNumber.setNumber(numberInput.getText().toString());
            storedNumber.setTimestamp(getCurrentTime());
            getLocation();
            storedNumber.setLatitude(locationLatitude);
            storedNumber.setLongitude(locationLongitude);
            numberDao.InsertOne(storedNumber);
        }
        // Go to ACTION_DIAL
        Uri call = Uri.parse("tel:" + Uri.encode(numberInput.getText().toString().replace("\uFF0A", "*")));
        Intent callIntent = new Intent(Intent.ACTION_CALL, call);
        context.startActivity(callIntent);
    }

    private void getLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationClient.getLastLocation();
        }
    }


    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        + ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        + ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
            || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)){
                new AlertDialog.Builder(this)
                        .setTitle("Permission Needed")
                        .setMessage("A call permission is required to make this call. Location-permissions are solely optional and you don't have to allow location to make a call. Stored calls will not have location info if denied")
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
            }
        }
        else{
            Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT);
        }
    }

    private String getCurrentTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = dateFormat.format(new Date());
        return currentDate;
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