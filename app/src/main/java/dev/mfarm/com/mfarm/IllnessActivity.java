package dev.mfarm.com.mfarm;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class IllnessActivity extends AppCompatActivity {
    Spinner spinneranimal,spinnerdisease;
    private ArrayAdapter<String> adapterForSpinner;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private DatePicker datePicker;
    private Calendar calendar;
    String animalName = "";

    private int year, month, day;
    int animal_id = 0;
    String animal_name;
    EditText edtsymptoms;
    Button btnSave;
    String disease = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illness);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        spinneranimal = (Spinner)findViewById(R.id.spinner);
        spinnerdisease = (Spinner)findViewById(R.id.spinnerdisease);
        edtsymptoms = (EditText)findViewById(R.id.edtsymptoms);
        btnSave = (Button)findViewById(R.id.btnSave);
        loadAnimalData();
        loadDiseasesData();
        spinneranimal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                animalName = ((TextView) view).getText().toString();
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

        spinnerdisease.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                disease = ((TextView) view).getText().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(getApplicationContext(),"Please select illness",Toast.LENGTH_SHORT).show();
                return;
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtsymptoms.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(), "Please Enter description",Toast.LENGTH_LONG).show();
                }
                else if (animal_id == 0){
                    Toast.makeText(getApplicationContext(), "Please Select Animal",Toast.LENGTH_LONG).show();
                }
                else {
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy, HH:mm");
                    String date = df.format(Calendar.getInstance().getTime());

                    ContentValues collect = new ContentValues();
                    collect.put("animal_id", animal_id);
                    collect.put("animal_name", animalName);
                    collect.put("sings_noted", edtsymptoms.getText().toString());
                    collect.put("illness_occured", disease);
                    collect.put("treatment",  "");
                    collect.put("date_occured",  date);
                    collect.put("diagnosis",  "");
                    collect.put("medicine",  "");
                    collect.put("treatment_date",  "");
                    collect.put("others",  "");



                    try {
                        MainActivity.database.insert("illness", null, collect);
                        Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_LONG).show();

                        Intent pp = new Intent(getApplicationContext(), TreatmentActivity.class);
                        startActivity(pp);

                    } catch (Exception e) {
                    }
                }
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

    private  void loadDiseasesData(){
        try{
            adapterForSpinner = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
            //autcountry.setAdapter(countryadapter);
            adapterForSpinner.add("Select the Disease Suspected");
            Cursor cursor = MainActivity.database.rawQuery("SELECT id,disease_name FROM diseases", null);
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                do {
                    adapterForSpinner.add(cursor.getString(1));

                }while (cursor.moveToNext());
            }
            cursor.close();
            spinnerdisease.setAdapter(adapterForSpinner);

        }catch(Exception e){
            Log.v("@@@@", e.getMessage());
        }
    }

}
