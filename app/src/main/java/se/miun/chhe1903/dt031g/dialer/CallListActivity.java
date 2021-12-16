package se.miun.chhe1903.dt031g.dialer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ReceiverCallNotAllowedException;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
    private NumbersAdapter numbersAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_list);
        db = NumberDatabase.getInstance(getApplicationContext());
        numberDao = db.numberDao();
        updateRecyclerView();
    }
    private void updateRecyclerView(){
        storedNumbers = numberDao.getAll();

        if (storedNumbers.size() != 0){
            recyclerView = findViewById(R.id.numbers_rv);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            numbersAdapter = new NumbersAdapter(this, storedNumbers);
            recyclerView.setAdapter(numbersAdapter);
        }
        else{
            if(recyclerView != null){
                recyclerView.setVisibility(View.GONE);
            }
            TextView noStoredNumbersInfo = findViewById(R.id.no_stored_numbers_info);
            noStoredNumbersInfo.setVisibility(View.VISIBLE);
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
        if (item.getItemId() == R.id.action_delete_stored) {
            SettingsActivity.deleteNumbers(getApplicationContext());
            updateRecyclerView();
        }
        return(super.onOptionsItemSelected(item));
    }
}