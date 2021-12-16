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
import se.miun.chhe1903.dt031g.dialer.data.Number;
import se.miun.chhe1903.dt031g.dialer.data.NumberDao;
import se.miun.chhe1903.dt031g.dialer.data.NumberDatabase;

import java.util.List;
import java.util.Map;

public class CallListActivity extends AppCompatActivity {
    private List<Number> storedNumbers;
    private NumberDatabase db;
    private NumberDao numberDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_list);
        db = NumberDatabase.getInstance(getApplicationContext());
        numberDao = db.numberDao();
        addStoredNumbersToTextView();
    }
    private void addStoredNumbersToTextView(){
        TextView textView = findViewById(R.id.callListTextView);
        textView.setMovementMethod(new ScrollingMovementMethod());
        storedNumbers = numberDao.getAll();
        for (Number storedNumber: storedNumbers){
            textView.append(storedNumber.getNumber() + "");
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