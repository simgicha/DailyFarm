package dev.mfarm.com.mfarm;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    Spinner spngender;
    private ArrayAdapter<String> adapterForSpinner;
    Button btnsave;
    EditText edtname,edtbodyconformance,edtbodycolor;
    Spinner spnbreed;
    String breed_id = "";
    String gender = "";

    private ArrayAdapter<String> adapterForBreedSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        spngender = (Spinner)findViewById(R.id.spngender);
        LoadSpinnerGender();
        edtname = (EditText)findViewById(R.id.edtname);
        spnbreed = (Spinner)findViewById(R.id.edtbreed);
        LoadBreedSpinner();
        edtbodyconformance = (EditText)findViewById(R.id.edtbodyconformance);
        edtbodycolor =(EditText)findViewById(R.id.edtbodycolor);

        spnbreed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String breed_name = "";
                breed_name = ((TextView) view).getText().toString();
                Cursor cursor = MainActivity.database.rawQuery("SELECT id, name FROM breeds WHERE name =?", new String[]{breed_name});
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    do {
                        breed_id = cursor.getString(0);
                    }while (cursor.moveToNext());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spngender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String breed_name = "";
                gender = ((TextView) view).getText().toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        btnsave = (Button)findViewById(R.id.btnsave);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtname.getText().toString().length()<2){
                    Toast.makeText(getApplicationContext(),"Please Enter animal name",Toast.LENGTH_LONG).show();
                    edtname.requestFocus();
                }
                else if (breed_id.equalsIgnoreCase("") || breed_id == null){
                    Toast.makeText(getApplicationContext(),"Select animal breed",Toast.LENGTH_LONG).show();
                    spnbreed.requestFocus();
                }
                else {
/*                    ContentValues collect = new ContentValues();
                    collect.put("name", edtname.getText().toString());
                    collect.put("breed_id", 1);
                    collect.put("gender", spngender.getSelectedItemId());
                    collect.put("dob", 1);
                    collect.put("body_color", edtbodycolor.getText().toString());
                    collect.put("dam_id", 1);
                    collect.put("sire_id", 1);*/
                    GlobalVariables.animal_name = edtname.getText().toString().trim();
                    GlobalVariables.breed_id = breed_id;
                    GlobalVariables.animal_name = edtbodyconformance.getText().toString();
                    GlobalVariables.gender = gender;
                    GlobalVariables.remarks = edtbodycolor.getText().toString();


                    try {
                        //MainActivity.database.insert("animas", null, collect);
                        //Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_LONG).show();

                        Intent x = new Intent(getApplicationContext(), RegistrationActivity2.class);
                        startActivity(x);

                    } catch (Exception e) {
                    }
                }
            }
        });
    }
    private void LoadSpinnerGender() {
        // database handler
        try{
            adapterForSpinner = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
            //adapterForSpinner = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item);
            adapterForSpinner.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);

            adapterForSpinner.add("Male");
            adapterForSpinner.add("Female");

            spngender.setAdapter(adapterForSpinner);

        }catch(Exception e){
            Log.v("@@@@", e.getMessage());
        }
    }

    private void LoadBreedSpinner(){
        try{
            String[] allColumns = new String[]{"id", "name"};
            adapterForBreedSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
            //adapterForSpinner = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item);
            adapterForBreedSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            Cursor cursor = MainActivity.database.rawQuery("SELECT id, name FROM breeds ", null);
            cursor.moveToFirst();
            adapterForBreedSpinner.add("Select Breed");

            while (!cursor.isAfterLast()) {
                do {
                    adapterForBreedSpinner.add(cursor.getString(1));
                    Log.v("EEEEEEEEE", "CCCCCCCCCCCCCCCCCCCCCCCCCCC " + cursor.getString(1));
                }while (cursor.moveToNext());
            }
            spnbreed.setAdapter(adapterForBreedSpinner);
        }
        catch(Exception e){
            Log.v("@@@@", e.getMessage());
        }

    }

}
