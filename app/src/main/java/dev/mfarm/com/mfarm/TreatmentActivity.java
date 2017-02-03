package dev.mfarm.com.mfarm;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import dev.mfarm.com.mfarm.adapters.AnimalAdapter;
import dev.mfarm.com.mfarm.adapters.IllnessAdapter;
import dev.mfarm.com.mfarm.models.illness;

public class TreatmentActivity extends AppCompatActivity {
    List<illness> supplierList = new ArrayList<illness>();
    //List<SupplierDetails> supplierList1 = new ArrayList<SupplierDetails>();
    private List<illness> data;
    IllnessAdapter aAdpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final ListView lv = (ListView)findViewById(R.id.listView);
        lv.setBackgroundColor(Color.TRANSPARENT);

        initList();

        aAdpt = new IllnessAdapter(this, supplierList);
        //aAdpt1 = new SupplierAdapter(this, supplierList1);
        lv.setAdapter(aAdpt);
    }

    private void initList() {
        // We populate the suppliers

        List<illness> members = new ArrayList<illness>();


        Cursor c = MainActivity.database.rawQuery("SELECT id, animal_id, animal_name, illness_occured, sings_noted, date_occured, sync_datetime, treatment, diagnosis, medicine, treatment_date, others, medicine_quantity, pregnancy_status, comments FROM illness order by id DESC",null);
        c.moveToFirst();
        if(!c.isAfterLast()) {
            do {
                supplierList.add(new illness(c.getString(0), c.getString(1), c.getString(2),c.getString(3),c.getString(4), c.getString(5), c.getString(6),c.getString(7),c.getString(8),c.getString(9),c.getString(10),c.getString(11),c.getString(12),c.getString(13),c.getString(14)));
                illness member = cursorToDisplay(c);
                members.add(member);
            } while (c.moveToNext());
        }else{


        }
        c.close();
        data = members;

    }
    private illness cursorToDisplay(Cursor cursor) {

        illness members = new illness();
        members.setId(cursor.getString(0));
        members.setAnimal_id(cursor.getString(1));
        members.setAnimal_name(cursor.getString(2));
        members.setIllness_occured(cursor.getString(3));
        members.setSings_noted(cursor.getString(3));
        members.setDate_occured(cursor.getString(3));
        members.setSync_datetime(cursor.getString(3));
        members.setTreatment(cursor.getString(3));
        members.setDiagnosis(cursor.getString(3));
        members.setMedicine(cursor.getString(3));
        members.setTreatment_date(cursor.getString(3));
        members.setOthers(cursor.getString(3));
        members.setMedicine_quantity(cursor.getString(3));
        members.setPregnancy_status(cursor.getString(3));
        members.setComments(cursor.getString(3));

        return members;
    }
}
