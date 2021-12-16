package se.miun.chhe1903.dt031g.dialer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import java.io.File;
import java.util.List;
import java.util.Map;

import se.miun.chhe1903.dt031g.dialer.data.Number;
import se.miun.chhe1903.dt031g.dialer.data.NumberDao;
import se.miun.chhe1903.dt031g.dialer.data.NumberDatabase;

public class SettingsActivity extends AppCompatActivity {
    // Instance variables
    private static NumberDatabase db;
    private static NumberDao numberDao;
    private static List<Number> storedNumbers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            ListPreference voicePref = findPreference("select_voices");
            File directoryPath = new File(Util.getInternalStorageDir(getContext()) + "/voices");
            String[] voiceFilesAvailable = directoryPath.list((current, name) -> new File(current, name).isDirectory());
            voicePref.setEntries(voiceFilesAvailable);
            voicePref.setEntryValues(voiceFilesAvailable);
        }

        @Override
        public boolean onPreferenceTreeClick(Preference preference) {
            String key = preference.getKey();
            if(key.equals("delete_numbers")){
                deleteNumbers(getContext().getApplicationContext());
            }
            return super.onPreferenceTreeClick(preference);
        }

    }


    public static boolean shouldStoreNumbers(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean("store_numbers", true);
    }

    public static void deleteNumbers(Context context){
        db = NumberDatabase.getInstance(context);
        numberDao = db.numberDao();
        storedNumbers = numberDao.getAll();
        numberDao.DeleteAll(storedNumbers);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            String caller = getIntent().getStringExtra("caller");
            if (caller != null && caller.equals("DialActivity")){
                startActivity(new Intent(this, DialActivity.class));
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}