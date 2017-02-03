package dev.mfarm.com.mfarm;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dev.mfarm.com.mfarm.fragments.AnimalListFragment;

public class RegistrationActivity2 extends AppCompatActivity {
    Button btndem,btnSire,btnsave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSire = (Button)findViewById(R.id.btnSire);
        btndem = (Button)findViewById(R.id.btndem);
        btnsave = (Button)findViewById(R.id.btnsave);

        btnSire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(getApplicationContext(), PhotoIntentActivity.class);
                startActivity(x);
            }
        });


    }

    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(RegistrationActivity2.this);
        View promptView = layoutInflater.inflate(R.layout.idinflate, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegistrationActivity2.this);
        alertDialogBuilder.setView(promptView);

        final EditText edtdamId = (EditText) promptView.findViewById(R.id.edtdamId);
//		editText.setInputType(InputType.TYPE_CLASS_PHONE);
//		editText.setInputType(InputType.TYPE_MASK_VARIATION);
/*        editText.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_CLASS_NUMBER);*/
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (edtdamId.getText().toString().trim().length()<11){

                            Toast.makeText(getApplicationContext(), "Enter valid meter number",Toast.LENGTH_LONG).show();
                        }else{

                            //the_meter_no = editText.getText().toString().trim();



                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                return;
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
