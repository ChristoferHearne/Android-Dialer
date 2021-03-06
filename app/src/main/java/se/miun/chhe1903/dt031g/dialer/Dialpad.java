package se.miun.chhe1903.dt031g.dialer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class Dialpad extends ConstraintLayout {
    // Instance variables
    private TextView numberInput;
    private Button backspaceButton;
    private Button callButton;
    private DialPadButton buttonZero, buttonOne, buttonTwo, buttonThree, buttonFour, buttonFive, buttonSix,
            buttonSeven, buttonEight, buttonNine, buttonPound, buttonStar;
    public Dialpad(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDialPadLayout(context);
        addOnClickListeners();
    }
    private void initDialPadLayout(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.dial_pad, this);
    }
    private void addOnClickListeners(){
        ArrayList<DialPadButton> dialPadButtons = new ArrayList<>(Arrays.asList(buttonZero = findViewById(R.id.button_zero), buttonOne = findViewById(R.id.button_one), buttonTwo = findViewById(R.id.button_two),
                    buttonThree = findViewById(R.id.button_three), buttonFour = findViewById(R.id.button_four), buttonFive = findViewById(R.id.button_five), buttonSix = findViewById(R.id.button_six),
                    buttonSeven = findViewById(R.id.button_seven), buttonEight = findViewById(R.id.button_eight), buttonNine = findViewById(R.id.button_nine), buttonPound = findViewById(R.id.button_pound), buttonStar = findViewById(R.id.button_star)));
        for (DialPadButton dialPadButton: dialPadButtons){
            dialPadButton.setCustomOnClickListener(button -> addNumberToInput(dialPadButton));
        }
        backspaceButton = findViewById(R.id.backspace_button);
        backspaceButton.setOnClickListener(textView -> numberInput.setText(deleteLastEntry(numberInput)));
    }

    private void addNumberToInput(DialPadButton dialPadButton){
        numberInput = findViewById(R.id.number_input);
        numberInput.append(dialPadButton.getTitle());
    }
    private String deleteLastEntry(TextView input){
        if (input.getText() == null || input.getText().length() == 0){
            return "";
        }
        else{
            return input.getText().toString().substring(0, input.getText().length() - 1);
        }
    }

}
