package se.miun.chhe1903.dt031g.dialer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.constraintlayout.widget.ConstraintLayout;

public class Dialpad extends ConstraintLayout {
    public Dialpad(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDialPadLayout(context);
    }
    private void initDialPadLayout(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.dial_pad, this);
    }
}
