package se.miun.chhe1903.dt031g.dialer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

@SuppressLint("ClickableViewAccessibility")
public class DialPadButton extends ConstraintLayout {
    // Constructor with context and AttributeSet
    public DialPadButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDialLayout(context);
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.DialPadButton);
        CharSequence title = arr.getString(R.styleable.DialPadButton_title);
        CharSequence message = arr.getString(R.styleable.DialPadButton_message);
        arr.recycle();
        if (title != null && message != null){
            this.setMessage(message.toString());
            this.setTitle(title.toString());
        }
        this.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    v.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_ATOP);
                    v.invalidate();
                    return true;
                }
                case MotionEvent.ACTION_UP: {
                    v.getBackground().clearColorFilter();
                    v.invalidate();
                    break;
                }
            }
            return false;
        });
    }
    // Constructor with context only
    public DialPadButton(Context context){
        this(context, null);
        initDialLayout(context);
    }
    // Constructor with context, AttributeSet and defStyle
    public DialPadButton(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        initDialLayout(context);
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
