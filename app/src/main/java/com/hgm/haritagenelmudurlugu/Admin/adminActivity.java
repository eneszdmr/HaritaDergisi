package com.hgm.haritagenelmudurlugu.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.hgm.haritagenelmudurlugu.Admin.bilimKurulu.bilimKurulFragment;
import com.hgm.haritagenelmudurlugu.Admin.dergiArsivi.dergiArsivFragment;
import com.hgm.haritagenelmudurlugu.Admin.ozelSayilar.ozelSayiFragment;
import com.hgm.haritagenelmudurlugu.MainActivity;
import com.hgm.haritagenelmudurlugu.R;

public class adminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private Fragment fragment;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        navigationView = findViewById(R.id.navigationView);
        drawer = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        fragment = new welcomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tutucu, fragment).commit();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    //navigation drawer
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.homepage) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.cikis) {
            mAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        }
        if (item.getItemId() == R.id.action_makaleSorgu) {
            fragment = new makaleSorguFragment();

        }
        if (item.getItemId() == R.id.action_sonSayi) {
            fragment = new sonSayiFragment();
        }
        if (item.getItemId() == R.id.action_arsiv) {
            fragment = new dergiArsivFragment();
        }
        if (item.getItemId() == R.id.action_ozelSayilar) {
            fragment = new ozelSayiFragment();
        }
        if (item.getItemId() == R.id.action_bilim) {
            fragment = new bilimKurulFragment();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tutucu, fragment).commit();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}