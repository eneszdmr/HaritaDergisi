package com.hgm.haritagenelmudurlugu.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hgm.haritagenelmudurlugu.R;
import com.hgm.haritagenelmudurlugu.SiteActivities.ArchiveActivity;
import com.hgm.haritagenelmudurlugu.SiteActivities.KurulActivities.bilimActivity;
import com.hgm.haritagenelmudurlugu.SiteActivities.KurulActivities.yonetimActivity;
import com.hgm.haritagenelmudurlugu.SiteActivities.MakaleSorgulaActivity;
import com.hgm.haritagenelmudurlugu.SiteActivities.OzelSayilarActivity;
import com.hgm.haritagenelmudurlugu.SiteActivities.ViewActivity;
import com.hgm.haritagenelmudurlugu.SiteActivities.yayinIlkeActivity;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment implements ValueEventListener{
    Button btnsonsayi, btnYayinilke, btnDergiArsiv, btnOzelSayi, btnMakaleSorgu,
            btnYonetimKurul, btnBilimKurul, btnMakaleYazimEsaslari, btnMakaleYazimSablonu;
    Toolbar toolbarHome;
    TextView textson;


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference = firebaseDatabase.getReference();
    private DatabaseReference mHeadingReference = mRootReference.child("dergisonsayi");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        tanimlamalar(rootView);
        textson = rootView.findViewById(R.id.textson);

        String makaleEsas = "https://www.harita.gov.tr/uploads/files-folder/makale_yazim_esaslari.pdf";
        String makaleSablon = "https://www.harita.gov.tr/uploads/files-folder/makale_yazim_sablonu.pdf";

        btnsonsayi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sonsayi = textson.getText().toString();
                mHeadingReference.setValue(sonsayi);
                Intent intent = new Intent(getActivity(), ViewActivity.class);
                intent.putExtra("key", sonsayi);
                startActivity(intent);
            }
        });

        btnYayinilke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), yayinIlkeActivity.class);
                startActivity(intent);
            }
        });
        btnDergiArsiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ArchiveActivity.class);
                startActivity(intent);
            }
        });
        btnOzelSayi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OzelSayilarActivity.class);
                startActivity(intent);
            }
        });
        btnMakaleSorgu.setOnClickListener(v -> startActivity(new Intent(getActivity(), MakaleSorgulaActivity.class)));

        btnYonetimKurul.setOnClickListener(v -> startActivity(new Intent(getActivity(), yonetimActivity.class)));
        btnBilimKurul.setOnClickListener(v -> startActivity(new Intent(getActivity(), bilimActivity.class)));

        btnMakaleYazimEsaslari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewActivity.class);
                intent.putExtra("key", makaleEsas);
                startActivity(intent);
            }
        });
        btnMakaleYazimSablonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewActivity.class);
                intent.putExtra("key", makaleSablon);
                startActivity(intent);
            }
        });


        return rootView;

    }

    private void tanimlamalar(View rootView) {
        btnsonsayi = rootView.findViewById(R.id.btnsonsayi);
        toolbarHome = rootView.findViewById(R.id.toolbarHome);
        btnYayinilke = rootView.findViewById(R.id.btnYayinilke);
        btnDergiArsiv = rootView.findViewById(R.id.btnDergiArsiv);
        btnOzelSayi = rootView.findViewById(R.id.btnOzelSayi);
        btnMakaleSorgu = rootView.findViewById(R.id.btnMakaleSorgu);
        btnYonetimKurul = rootView.findViewById(R.id.btnYonetimKurul);
        btnBilimKurul = rootView.findViewById(R.id.btnBilimKurul);
        btnMakaleYazimEsaslari = rootView.findViewById(R.id.btnMakaleYazimEsaslari);
        btnMakaleYazimSablonu = rootView.findViewById(R.id.btnMakaleYazimSablonu);
    }

    @Override
    public void onStart() {
        super.onStart();
        mHeadingReference.addValueEventListener(this);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {

        if (snapshot.getValue(String.class) != null) {
            String key = snapshot.getKey();
            if (key.equals("dergisonsayi")) {
                String dergisonsayi = snapshot.getValue(String.class);
                textson.setText(dergisonsayi);
            }

        }

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}