package com.hgm.haritagenelmudurlugu.Admin.bilimKurulu;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hgm.haritagenelmudurlugu.Admin.ozelSayilar.OzelSayiAdapter;
import com.hgm.haritagenelmudurlugu.R;
import com.hgm.haritagenelmudurlugu.SiteActivities.Utility.BilimAdapter;
import com.hgm.haritagenelmudurlugu.SiteActivities.Utility.BilimModel;
import com.hgm.haritagenelmudurlugu.pdf.PDFModel2;

import java.util.ArrayList;

public class bilimKurulFragment extends Fragment {
    private RecyclerView rvBilim;
    private FloatingActionButton fabBilimEkle;
    private BilimKuruluAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference myref;
    private ArrayList<BilimModel> bilimModelArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bilim_kurul, container, false);
        fabBilimEkle = v.findViewById(R.id.fabBilimEkle);
        rvBilim = v.findViewById(R.id.rvBilim);

        database = FirebaseDatabase.getInstance();
        myref = database.getReference("bilimKurul");


        rvBilim.setHasFixedSize(true);
        rvBilim.setLayoutManager(new LinearLayoutManager(getContext()));

        bilimModelArrayList = new ArrayList<>();

        adapter = new BilimKuruluAdapter(getContext(), bilimModelArrayList, myref);
        rvBilim.setAdapter(adapter);

        tumBilimKurulu();

        fabBilimEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertGoster();
            }
        });

        return v;
    }

    private void tumBilimKurulu() {

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bilimModelArrayList.clear();

                for (DataSnapshot d : snapshot.getChildren()) {
                    BilimModel bilimModel = d.getValue(BilimModel.class);
                    bilimModel.setId(d.getKey());
                    bilimModelArrayList.add(bilimModel);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void alertGoster() {
        LayoutInflater layout = LayoutInflater.from(getContext());
        View tasarim = layout.inflate(R.layout.alert_bilimkurul_ekle, null);


        EditText etUnvani = tasarim.findViewById(R.id.etUnvani);
        EditText etAdiSoyadi = tasarim.findViewById(R.id.etAdiSoyadi);
        EditText etKurumu = tasarim.findViewById(R.id.etKurumu);
        EditText etBransi = tasarim.findViewById(R.id.etBransi);

        AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
        ad.setTitle("Bilim Kurulu Ekle");
        ad.setView(tasarim);
        ad.setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        ad.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        ad.setView(tasarim);
        AlertDialog dialog = ad.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etUnvani.getText().toString())) {
                    Toast.makeText(getContext(), "ünvan eksik", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(etAdiSoyadi.getText().toString())) {
                    Toast.makeText(getContext(), "Adı Soyadı eksik", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(etKurumu.getText().toString())) {
                    Toast.makeText(getContext(), "Kurumu eksik", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(etBransi.getText().toString())) {
                    Toast.makeText(getContext(), "Branşı eksik", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    String unvan = etUnvani.getText().toString().trim();
                    String adiSoyadi = etAdiSoyadi.getText().toString().trim();
                    String kurumu = etKurumu.getText().toString().trim();
                    String bransi = etBransi.getText().toString().trim();

                    String key = myref.push().getKey();
                    BilimModel bilimModel = new BilimModel(key, unvan, adiSoyadi, kurumu, bransi);
                    myref.push().setValue(bilimModel);

                    Toast.makeText(getContext(), "Bilim Kuruluna yeni kayıt eklendi", Toast.LENGTH_SHORT).show();

                }

                dialog.dismiss();


            }
        });
    }
}