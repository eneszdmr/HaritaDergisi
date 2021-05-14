package com.hgm.haritagenelmudurlugu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.hgm.haritagenelmudurlugu.SiteActivities.Utility.NetworkChangeListener;
import com.hgm.haritagenelmudurlugu.fragments.CallFragment;
import com.hgm.haritagenelmudurlugu.fragments.HomeFragment;
import com.hgm.haritagenelmudurlugu.fragments.NavFragment;

public class MainActivity extends AppCompatActivity {
    private MeowBottomNavigation bottomNavigation;
    private FirebaseAnalytics mFirebaseAnalytics;
    NetworkChangeListener networkChangeListener=new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        bottomNavigation=findViewById(R.id.bottom_navigation);

        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.call));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.home));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.navigation));


        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment=null;

                switch (item.getId()){
                    case 1:
                        fragment = new CallFragment();
                      break;
                    case 2:
                        fragment=new HomeFragment();
                       break;
                    case 3:
                        fragment=new NavFragment();
                        break;

                }
                LoadFragment(fragment);
            }
        });

        bottomNavigation.show(2,true);

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

                //Toast.makeText(getApplicationContext(),"tıkladınız "+item.getId(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void LoadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,fragment)
                .commit();
    }

    @Override
    protected void onStart() {
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

}