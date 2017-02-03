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
import dev.mfarm.com.mfarm.models.animal;

/**
 * Created by Methu on 4/19/2016.
 */
public class AnimalListFragment extends Fragment{
    List<animal> supplierList = new ArrayList<animal>();
    //List<SupplierDetails> supplierList1 = new ArrayList<SupplierDetails>();
    private List<animal> data;
    AnimalAdapter aAdpt;
    public AnimalListFragment newInstance(){
        AnimalListFragment _AnimalListFragment = new AnimalListFragment();

        return _AnimalListFragment;
    }

    public static AnimalListFragment newInstance(Fragment f){
        AnimalListFragment _AnimalListFragment = new AnimalListFragment();

        return _AnimalListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_animal_list, container, false);

        final ListView lv = (ListView)view.findViewById(R.id.listView);
        lv.setBackgroundColor(Color.TRANSPARENT);

        initList();

        aAdpt = new AnimalAdapter(getActivity(), supplierList);
        //aAdpt1 = new SupplierAdapter(this, supplierList1);
        lv.setAdapter(aAdpt);
        return view;
    }

    private void initList() {
        // We populate the suppliers

        List<animal> members = new ArrayList<animal>();


        Cursor c = MainActivity.database.rawQuery("SELECT id,name,breed_id,gender, body_conf,dob, body_color,dam_id, sire_id FROM animas order by id DESC",null);
        c.moveToFirst();
        if(!c.isAfterLast()) {
            do {
                supplierList.add(new animal(c.getString(0), c.getString(1), c.getString(2),c.getString(3),c.getString(4), c.getString(5), c.getString(6),c.getString(7)));
                animal member = cursorToDisplay(c);
                members.add(member);
            } while (c.moveToNext());
        }else{


        }
        c.close();
        data = members;

    }
    private animal cursorToDisplay(Cursor cursor) {
        animal members = new animal();
        members.setId(cursor.getString(0));
        members.setName(cursor.getString(1));
        members.setDob(cursor.getString(2));
        members.setBreed_id(cursor.getString(3));

        return members;
    }
}
