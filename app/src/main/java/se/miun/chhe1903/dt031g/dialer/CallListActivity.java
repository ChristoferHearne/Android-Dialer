package se.miun.chhe1903.dt031g.dialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import java.util.Map;

public class CallListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_list);
        addStoredNumbersToTextView();
    }
    private void addStoredNumbersToTextView(){
        TextView textView = (TextView) findViewById(R.id.callListTextView);
        SharedPreferences prefs = getSharedPreferences("se.miun.chhe1903.dt031g.dialer_preferences", MODE_PRIVATE);
        Map<String, ?> keys = prefs.getAll();
        for (Map.Entry<String, ?> entry: keys.entrySet()){
            if (entry.getKey().contains("store_numbers_")){
                textView.append(entry.getValue().toString() + "\n");
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_baseline_delete_forever_24);
        Toolbar toolbar = (Toolbar) findViewById(R.id.call_list_toolbar);
        toolbar.setOverflowIcon(drawable);
        return true;
    }
}