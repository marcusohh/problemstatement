package sg.edu.rp.c346.problemstatement;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    final Calendar c = Calendar.getInstance();
    EditText name;
    EditText number;
    EditText pax;
    CheckBox smoke;
    Button reserve;
    Button reset;
    EditText etday;
    EditText ettime;
    int dd = c.get(Calendar.DAY_OF_MONTH);
    int mm = c.get(Calendar.MONTH);
    int yy = c.get(Calendar.YEAR);

    int hr = c.get(Calendar.HOUR_OF_DAY);
    int min = c.get(Calendar.MINUTE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.Name);
        number = findViewById(R.id.number);
        pax = findViewById(R.id.pax);
        smoke = findViewById(R.id.smoking);
        reserve = findViewById(R.id.reserve);
        reset = findViewById(R.id.reset);
        etday = findViewById(R.id.editTextDay);
        ettime = findViewById(R.id.editTextTime);





        c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DATE + 1));
        String time = "\nTime is " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
        String date = "\nDate is " + c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR);

        etday.setText(date);
        ettime.setText(time);



        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("MyActivity","Inside onClick()");
                String time = "\nTime is " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
                String date = "\nDate is " + c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR);

                String m = "name:" + name.getText() + "\nphone number:" + number.getText() + "\nnumber of people:" + pax.getText() + date + time;

                if(name.getText().toString().length() == 0 || number.getText().toString().length() == 0 || pax.getText().toString().length() == 0)
                {
                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
                    myBuilder.setTitle("Input");
                    myBuilder.setMessage("Please enter you input");
                    myBuilder.setCancelable(false);
                    myBuilder.setNeutralButton("Cancel",null);
                    myBuilder.setPositiveButton("OK",null);
                    AlertDialog myDialog = myBuilder.create();
                    myDialog.show();
                }
                else
                {
                    if (smoke.isChecked()) {
                        AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
                        myBuilder.setTitle("Booking");
                        myBuilder.setMessage(m +"\nsmoking table");
                        myBuilder.setCancelable(false);
                        myBuilder.setNeutralButton("Cancel",null);
                        myBuilder.setPositiveButton("OK",null);
                        AlertDialog myDialog = myBuilder.create();
                        myDialog.show();
                    } else {
                        AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
                        myBuilder.setTitle("Booking");
                        myBuilder.setMessage(m );
                        myBuilder.setCancelable(false);
                        myBuilder.setNeutralButton("Cancel",null);
                        myBuilder.setPositiveButton("OK",null);
                        AlertDialog myDialog = myBuilder.create();
                        myDialog.show();
                    }
                }
            }
        });

        ettime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker View, int hourofDay, int minute) {
                        ettime.setText("Time " + hourofDay + ":" +minute);
                        hr = hourofDay;
                        min = minute;
                    }
                };


                TimePickerDialog myTimeDialog = new TimePickerDialog(MainActivity.this,myTimeListener,hr,min,true);

                myTimeDialog.show();
            }
        });

        etday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthofYear, int dayOfMonth) {
                        etday.setText("Date: " + dayOfMonth + "/" + (monthofYear+1) + "/" + year);
                        dd = dayOfMonth;
                        mm = monthofYear;
                        yy = year;

                    }
                };



                DatePickerDialog myDateDialog = new DatePickerDialog(MainActivity.this,
                        myDateListener,yy,mm,dd);

                myDateDialog.show();
            }
        });
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etday.setText(null);
                ettime.setText(null);
                name.setText(null);
                number.setText(null);
                pax.setText(null);
                if (smoke.isChecked()) {
                    smoke.toggle();
                }
                SharedPreferences.Editor prefEdit = prefs.edit();

                prefEdit.clear().commit();
            }
        });

    }
    @Override
    protected void onPause() {

        super.onPause();
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

            SharedPreferences.Editor prefEdit = prefs.edit();
            prefEdit.putString("Sname", name.getText().toString());
            prefEdit.putString("Snumber", number.getText().toString());
            prefEdit.putString("Spax", pax.getText().toString());
            prefEdit.putString("day", etday.getText().toString());
            prefEdit.putString("time", ettime.getText().toString());

            if (smoke.isChecked()){
                prefEdit.putBoolean("checkbox",true);
            }
            else{
                prefEdit.putBoolean("checkbox",false);
            }

            prefEdit.commit();

    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String Sname = prefs.getString("Sname","");
        String Snumber = prefs.getString("Snumber","");
        String Spax = prefs.getString("Spax","");
        String date = prefs.getString("day","");
        String time = prefs.getString("time","");
        Boolean chb = prefs.getBoolean("checkbox",false);
        smoke.setChecked(chb);
        name.setText(Sname);
        number.setText(Snumber);
        pax.setText(Spax);
        etday.setText(date);
        ettime.setText(time);
    }


}
