package se.miun.chhe1903.dt031g.dialer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import se.miun.chhe1903.dt031g.dialer.SettingsActivity;

import java.util.Map;

public class CallListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_list);
        addStoredNumbersToTextView();
    }
    private void addStoredNumbersToTextView(){
        TextView textView = findViewById(R.id.callListTextView);
        textView.setMovementMethod(new ScrollingMovementMethod());
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Map<String, ?> keys = prefs.getAll();
        if (keys.size() == 1){
            textView.setText("You have no stored numbers yet");
        }
        else{
            textView.setText("Your stored numbers: \n");
            for (Map.Entry<String, ?> entry: keys.entrySet()){
                if (entry.getKey().contains("store_number_")){
                    textView.append(entry.getValue().toString() + "\n");
                }
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.call_list_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        TextView textView = findViewById(R.id.callListTextView);
        if (item.getItemId() == R.id.action_delete_stored) {
            SettingsActivity.deleteNumbers(getApplicationContext());
            textView.setText("You have no stored numbers yet");
        }
        return(super.onOptionsItemSelected(item));
    }
}