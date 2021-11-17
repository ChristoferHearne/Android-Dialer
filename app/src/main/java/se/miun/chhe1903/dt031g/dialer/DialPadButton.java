package se.miun.chhe1903.dt031g.dialer;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;


public class DialPadButton extends ConstraintLayout {
    public DialPadButton(Context context) {
        super(context);
        initDialLayout(context);
        TypedArray arr = context.obtainStyledAttributes(null, R.styleable.DialPadButton);
        CharSequence title = arr.getString(R.styleable.DialPadButton_title);
        CharSequence message = arr.getString(R.styleable.DialPadButton_message);
        arr.recycle();
        if (title != null && message != null){
            this.setMessage(message.toString());
            this.setTitle(title.toString());
        }
    }
    public DialPadButton(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    private void initDialLayout(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.dial_pad_button, this);
    }
    public void setTitle(String title){
        TextView titleText = findViewById(R.id.dial_button_title);
        titleText.setText(title.substring(0, 1));
    }
    public void setMessage(String message){
        TextView messageText = findViewById(R.id.dial_button_message);
        messageText.setText(message.substring(0, 3).toUpperCase());
    }
}
