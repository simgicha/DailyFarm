package dev.mfarm.com.mfarm;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MilkProductionActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapterForSpinner;
    Spinner spinneranimal;
    Button btnsave,btndate;
    EditText edtlitres,edtmilkCondition;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private DatePicker datePicker;
    private Calendar calendar;

    private int year, month, day;
    int animal_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milk_production);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        //dateView = (TextView) findViewById(R.id.textView3);


        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        spinneranimal = (Spinner)findViewById(R.id.spinner);
        btnsave = (Button)findViewById(R.id.btnsave);
        btndate = (Button)findViewById(R.id.btndate);
        edtlitres = (EditText)findViewById(R.id.edtlitres);
        edtmilkCondition = (EditText)findViewById(R.id.edtmilkCondition);
        btndate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(999);
                //showDate(year, month+1, day);
            }
        });
        loadAnimalData();
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtlitres.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(), "Please Enter milk in litres",Toast.LENGTH_LONG).show();
                }
                else {
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy, HH:mm");
                    String date = df.format(Calendar.getInstance().getTime());

                    ContentValues collect = new ContentValues();
                    collect.put("litres", edtlitres.getText().toString());
                    collect.put("animal_id", animal_id);
                    collect.put("description", edtmilkCondition.getText().toString());
                    collect.put("datetime",  date);

                    try {
                        MainActivity.database.insert("milk_production", null, collect);
                        Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_LONG).show();

                        Intent pp = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(pp);

                    } catch (Exception e) {
                    }
                }
            }
        });


        spinneranimal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String animalName = ((TextView) view).getText().toString();
                //Toast.makeText(getApplicationContext(),"CCC "+currencyCode, Toast.LENGTH_LONG).show();
                Cursor cursor = MainActivity.database.rawQuery("SELECT id,name FROM animas where name = ?",  new String[]{animalName});
                cursor.moveToFirst();
                if (!cursor.isAfterLast()) {
                    do {
                        //GlobalVariables.currency_code = cursor.getString(3);
                        animal_id = cursor.getInt(0);
                    }while (cursor.moveToNext());
                }
                cursor.close();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(getApplicationContext(),"Please select currency",Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }
    private void loadAnimalData() {
        // database handler
        try{

            adapterForSpinner = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
            //autcountry.setAdapter(countryadapter);
            adapterForSpinner.add("Select the Cow");
            Cursor cursor = MainActivity.database.rawQuery("SELECT id,name FROM animas", null);
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                do {
                    adapterForSpinner.add(cursor.getString(1));

                }while (cursor.moveToNext());
            }
            cursor.close();
            spinneranimal.setAdapter(adapterForSpinner);

        }catch(Exception e){
            Log.v("@@@@", e.getMessage());
        }
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        btndate.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }
}
