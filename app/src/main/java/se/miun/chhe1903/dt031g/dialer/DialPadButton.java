package se.miun.chhe1903.dt031g.dialer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.TransitionDrawable;
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

        addOnTouch();
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
    private void addOnTouch(){
        this.setOnTouchListener((view, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    TransitionDrawable transition = (TransitionDrawable) view.getBackground();
                    transition.startTransition(100);
                    view.invalidate();
                    return true;
                }
                case MotionEvent.ACTION_UP: {
                    TransitionDrawable transition = (TransitionDrawable) view.getBackground();
                    transition.reverseTransition(100);
                    break;
                }
            }
            return false;
        });
    }

    private void initDialLayout(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.dial_pad_button, this);
    }
    public void setTitle(String title){
        TextView titleText = findViewById(R.id.dial_button_title);
        if (title.length() > 1){
            titleText.setText(title.substring(0, 1));
        }
        else{
            titleText.setText(title);
        }
    }
    public void setMessage(String message){
        TextView messageText = findViewById(R.id.dial_button_message);
        if (message.length() > 4){
            messageText.setText(message.substring(0, 4).toUpperCase());
        }
        else{
            messageText.setText(message);
        }
    }

}
