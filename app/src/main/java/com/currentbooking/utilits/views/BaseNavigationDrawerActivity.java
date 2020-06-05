package com.currentbooking.utilits.views;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.currentbooking.R;
import com.google.android.material.navigation.NavigationView;

public abstract class BaseNavigationDrawerActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Menu menu;
    public DrawerLayout dl;
    public ActionBarDrawerToggle t;
    public NavigationView nv;
    protected LinearLayoutCompat signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.nav_view_content);
        setNavigationView();
    }
//    public void setContentView(int id) {
//        FrameLayout dynamicContent = findViewById(R.id.main_content);
//        View wizardView = getLayoutInflater().inflate(id, dynamicContent, false);
//        // add the inflated View to the layout
//        dynamicContent.addView(wizardView);
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (t.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setNavigationView() {
        dl = findViewById(R.id.drawer_layout);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);
        dl.addDrawerListener(t);
        t.syncState();
        ActionBar ab = null; //getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        nv = findViewById(R.id.nav_view);
        signOut = nv.findViewById(R.id.sign_out_btn);
        nv.setNavigationItemSelectedListener(this);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;

    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
