package se.miun.chhe1903.dt031g.dialer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.TransitionDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

@SuppressLint("ClickableViewAccessibility")
public class DialPadButton extends ConstraintLayout {
    // Instance variables
    private TextView titleText;
    private TextView messageText;
    private TextView numberInput; 
    private SoundPlayer soundPlayer = SoundPlayer.getInstance(this.getContext());

    // Constructor with context and AttributeSet
    public DialPadButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDialLayout(context);
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.DialPadButton);
        CharSequence title = arr.getString(R.styleable.DialPadButton_title);
        CharSequence message = arr.getString(R.styleable.DialPadButton_message);
        arr.recycle();

        this.setMessage(message.toString());
        this.setTitle(title.toString());
        addOnTouch();
    }
    // Constructor with context only
    public DialPadButton(Context context){
        super(context);
        initDialLayout(context);
    }
    // Constructor with context, AttributeSet and defStyle
    public DialPadButton(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        initDialLayout(context);
    }

    // Onclick listener interface
    public interface OnClickListener{
        void onClick(DialPadButton dialPadButton);
    }
    // Variable for holding the listener
    private OnClickListener listener;

    // Listener setter
    public void setCustomOnClickListener(OnClickListener listener){
        this.listener = listener;
    }

    // Methods
    private void addOnTouch(){ // Adds fading animation to button when user interacts with it
        this.setOnTouchListener((view, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: { // If the button is pressed
                    // Play Sound
                    soundPlayer.playSound(this);
                    // Fade animation
                    TransitionDrawable transition = (TransitionDrawable) view.getBackground();
                    transition.startTransition(100);
                    view.invalidate();
                    return true;
                }
                case MotionEvent.ACTION_UP: { // If the button is released
                    // Reverses the transition to animate back to original state
                    TransitionDrawable transition = (TransitionDrawable) view.getBackground();
                    transition.reverseTransition(100);
                    if (listener != null){
                        listener.onClick(this);
                    }
                    break;
                }
            }
            return false;
        });
    }


    private void initDialLayout(Context context){ // Inflates the view
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.dial_pad_button, this);
    }
    // Getters and setters
    public void setTitle(String title){
        titleText = findViewById(R.id.dial_button_title);
        if (title.length() > 1){
            titleText.setText(title.substring(0, 1));
        }
        else{
            titleText.setText(title);
        }
    }
    public void setMessage(String message){
        messageText = findViewById(R.id.dial_button_message);
        if (message.length() > 4){
            messageText.setText(message.substring(0, 4).toUpperCase());
        }
        else{
            messageText.setText(message);
        }
    }
    public String getTitle(){
        return this.titleText.getText().toString();
    }


}
