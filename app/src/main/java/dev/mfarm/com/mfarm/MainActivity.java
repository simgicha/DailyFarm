package dev.mfarm.com.mfarm;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import dev.mfarm.com.mfarm.dao.DatabaseHelper;
import dev.mfarm.com.mfarm.fragments.AnimalListFragment;
import dev.mfarm.com.mfarm.fragments.MedicationFragment;
import dev.mfarm.com.mfarm.fragments.MilkProductionListFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentTransaction ft;
    FragmentManager fragmentManager;
    AnimalListFragment _fragAnimalList;
    MilkProductionListFragment _fragMedication;
    public static final String DB_NAME = "farmapp.sqlite";
    public static SQLiteDatabase database;

    static DatabaseHelper dbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbOpenHelper = new DatabaseHelper(this, DB_NAME);
        // Initialize database
        database = dbOpenHelper.openDataBase();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button imgregister = (Button)findViewById(R.id.imgregister);
        imgregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(p);
            }
        });
        Button imgmilk = (Button)findViewById(R.id.imgmilk);
        imgmilk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pp = new Intent(getApplicationContext(),MilkProductionActivity.class);
                startActivity(pp);
            }
        });

        Button imgmedication = (Button)findViewById(R.id.imgmedication);
        imgmedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x= new Intent(getApplicationContext(), MedicationActivity.class);
                startActivity(x);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        ft = getSupportFragmentManager().beginTransaction();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            _fragAnimalList = new AnimalListFragment().newInstance();
            if (_fragAnimalList.isAdded()) { // if the fragment is already in container
                ft.show(_fragAnimalList);
            } else { // fragment needs to be added to frame container
                ft.addToBackStack("A");
                ft.replace(R.id.main_frame, _fragAnimalList, "A");
            }


        } else if (id == R.id.nav_gallery) {

            _fragMedication = new MilkProductionListFragment().newInstance();
            if (_fragMedication.isAdded()) { // if the fragment is already in container
                ft.show(_fragMedication);
            } else { // fragment needs to be added to frame container
                ft.addToBackStack("A");
                ft.replace(R.id.main_frame, _fragMedication, "B");
            }

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        ft.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
