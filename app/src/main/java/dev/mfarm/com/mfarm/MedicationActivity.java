package dev.mfarm.com.mfarm;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import dev.mfarm.com.mfarm.adapters.AnimalAdapter;
import dev.mfarm.com.mfarm.adapters.MenuListAdapter;
import dev.mfarm.com.mfarm.models.menulist;

public class MedicationActivity extends AppCompatActivity {
    List<menulist> supplierList = new ArrayList<menulist>();
    private List<menulist> data;
    MenuListAdapter aAdpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ListView lv = (ListView)findViewById(R.id.listView);
        lv.setBackgroundColor(Color.TRANSPARENT);

        initList();

        aAdpt = new MenuListAdapter(this, supplierList);
        //aAdpt1 = new SupplierAdapter(this, supplierList1);
        lv.setAdapter(aAdpt);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                menulist m = aAdpt.getItem(position);
                String rec = "";
                rec=m.getId();
                if (rec.equalsIgnoreCase("1")){
                    Intent p = new Intent(getApplicationContext(), IllnessActivity.class);
                    startActivity(p);

                }
                if (rec.equalsIgnoreCase("3")){
                    Intent p = new Intent(getApplicationContext(), TreatmentActivity.class);
                    startActivity(p);

                }
            }
        });
    }
    private void initList() {
        // We populate the suppliers
        List<menulist> members = new ArrayList<menulist>();
        supplierList.add(new menulist("1", "Illness Occurence", "img"));
        supplierList.add(new menulist("2", "Mastitis Occurence", "img"));
        supplierList.add(new menulist("3", "Treatment", "img"));
        supplierList.add(new menulist("4", "Vet Checks", "img"));
        //data = members;
    }
}
