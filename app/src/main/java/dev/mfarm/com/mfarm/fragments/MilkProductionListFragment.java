package dev.mfarm.com.mfarm.fragments;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import dev.mfarm.com.mfarm.MainActivity;
import dev.mfarm.com.mfarm.R;
import dev.mfarm.com.mfarm.adapters.AnimalAdapter;

import dev.mfarm.com.mfarm.adapters.MilkProductionAdapter;
import dev.mfarm.com.mfarm.models.milkproduction;

/**
 * Created by SIMGICH on 4/20/2016.
 */
public class MilkProductionListFragment  extends Fragment {
    List<milkproduction> supplierList = new ArrayList<milkproduction>();
    //List<SupplierDetails> supplierList1 = new ArrayList<SupplierDetails>();
    private List<milkproduction> data;
    MilkProductionAdapter aAdpt;
    public MilkProductionListFragment newInstance(){
        MilkProductionListFragment _AnimalListFragment = new MilkProductionListFragment();

        return _AnimalListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_milk_list, container, false);

        final ListView lv = (ListView)view.findViewById(R.id.listView);
        lv.setBackgroundColor(Color.TRANSPARENT);

        initList();

        aAdpt = new MilkProductionAdapter(getActivity(), supplierList);
        //aAdpt1 = new SupplierAdapter(this, supplierList1);
        lv.setAdapter(aAdpt);
        return view;
    }

    private void initList() {
        // We populate the suppliers

        List<milkproduction> members = new ArrayList<milkproduction>();


        Cursor c = MainActivity.database.rawQuery("SELECT m.id,m.animal_id,m.litres,m.datetime, m.description,a.name FROM animas a, milk_production m WHERE m.animal_id = a.id order by m.id DESC",null);
        c.moveToFirst();
        if(!c.isAfterLast()) {
            do {

                supplierList.add(new milkproduction(c.getString(0), c.getString(1), c.getString(2),c.getString(4),c.getString(3),c.getString(5)));
                milkproduction member = cursorToDisplay(c);
                members.add(member);
            } while (c.moveToNext());
        }else{


        }
        c.close();
        data = members;

    }
    private milkproduction cursorToDisplay(Cursor cursor) {
        milkproduction members = new milkproduction();
        members.setId(cursor.getString(0));
        members.setAnimal_id(cursor.getString(1));
        members.setLitres(cursor.getString(2));
        members.setDatetime(cursor.getString(3));
        members.setA_name(cursor.getString(5));

        return members;
    }
}
