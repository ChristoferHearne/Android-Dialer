package se.miun.chhe1903.dt031g.dialer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);

        DialPadButton myButton = new DialPadButton(this);
        myButton.setTitle("1-this-is-to-long");
        myButton.setMessage("ABC-this-is-to-long");
        setContentView(myButton);
    }
}