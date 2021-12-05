package se.miun.chhe1903.dt031g.dialer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import java.util.Map;

public class SettingsActivity extends AppCompatActivity {
    // Auto-create stub from wizard
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

    public static boolean deleteNumbers(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Map<String, ?> keys = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry: keys.entrySet()){
            if (entry.getKey().contains("store_number_")){
                editor.remove(entry.getKey());
                editor.commit();
            }
        }
        return true;
    }


}