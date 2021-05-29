package com.hgm.haritagenelmudurlugu.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hgm.haritagenelmudurlugu.R;

public class NavFragment extends Fragment {
    private int izinKontrol;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nav, container, false);
/*
        izinKontrol= ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (izinKontrol != PackageManager.PERMISSION_GRANTED){
            // daha önce izin verilmemiş ise burası

            ActivityCompat
                    .requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);
        }else{

        }
*/

        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);


        // konuma animasyon ile yakınlaş
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                LatLng ankara = new LatLng(39.932417, 32.881883);
                googleMap.addMarker(new MarkerOptions()
                        .position(ankara)
                        .title("Harita Genel Müdürlüğü"));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ankara,18f),1500,null);

            }
        });


        return rootView;
    }

  /*  @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==100){
            if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getActivity(),"izin verildi",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(),"izin verilmedi",Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }*/
}