package se.miun.chhe1903.dt031g.dialer;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Dialpad extends ConstraintLayout {
    public Dialpad(@NonNull Context context) {
        super(context);
        initDialPadLayout(context);
    }
    private void initDialPadLayout(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.dial_pad, this);
    }
}
