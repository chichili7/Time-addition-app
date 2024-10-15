package com.example.myapplication;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText valueToAdd;
    private TextView results,historySpace;
    private RadioGroup selectionButtons;
    private Button calculateButton,clearAll;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        valueToAdd = findViewById(R.id.valueToAddInput);
        results = findViewById(R.id.resultsoutput);
        historySpace = findViewById(R.id.historyspace);
        selectionButtons = findViewById(R.id.selectionBtns);
        calculateButton = findViewById(R.id.calculate_button);
        clearAll = findViewById(R.id.clearAll);
        historySpace.setMovementMethod(new ScrollingMovementMethod());
    }

    public void calculate (View v){
      int value = Integer.parseInt(valueToAdd.getText().toString());
      Calendar cal = Calendar.getInstance();
        int unit = selectionButtons.getCheckedRadioButtonId();
        String unitName = "";

        if(unit == R.id.hours) {
            cal.add(Calendar.HOUR, value);
            unitName = "Hours";
        } else if(unit == R.id.days) {
            cal.add(Calendar.DAY_OF_YEAR, value);
            unitName="Days";
        } else if (unit == R.id.weeks){
            cal.add(Calendar.WEEK_OF_YEAR, value);
            unitName="Weeks";
        } else {
            cal.add(Calendar.MONTH, value);
            unitName="Months";
        }

        String output = simpleDateFormat.format(cal.getTime());
        results.setText(output);
        String value1;
        if(value>0){
            value1 = "+" + value;
        }
        else{
             value1 = value +" ";
        }

        String previousHistory = historySpace.getText().toString();
        String newHistory = value1 +" "+ unitName+ " : " + output;
        historySpace.setText(previousHistory + "\n" + newHistory);


    }
    public void clearAll(View v){
        valueToAdd.setText(" ");
        selectionButtons.check(R.id.hours);
        results.setText(" ");
        historySpace.setText(" ");

    }

    public void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("historySpace",historySpace.getText().toString());
        outState.putString("result",results.getText().toString());
    }

    public void onRestoreInstanceState(@NonNull Bundle inState){
        super.onRestoreInstanceState(inState);
        historySpace.setText(inState.getString("historySpace"));
        results.setText(inState.getString("result"));
    }

}