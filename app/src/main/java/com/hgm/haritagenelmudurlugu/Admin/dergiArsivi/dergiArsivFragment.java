package com.hgm.haritagenelmudurlugu.Admin.dergiArsivi;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hgm.haritagenelmudurlugu.R;
import com.hgm.haritagenelmudurlugu.pdf.PDFModel;

import java.util.ArrayList;

public class dergiArsivFragment extends Fragment {
    private RecyclerView rvDergiArsivEkle;
    private FloatingActionButton fabDergiArsivEkle;
    private PDFModel pdfModel;
    private ArsivAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference myref;
    private ArrayList<PDFModel> pdfModelArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dergi_arsiv, container, false);
        fabDergiArsivEkle = v.findViewById(R.id.fabOzelSayiEkle);
        rvDergiArsivEkle = v.findViewById(R.id.rvOzelSayi);

        database = FirebaseDatabase.getInstance();
        myref = database.getReference("dergiArsivi");


        rvDergiArsivEkle.setHasFixedSize(true);
        rvDergiArsivEkle.setLayoutManager(new LinearLayoutManager(getContext()));

        pdfModelArrayList = new ArrayList<>();

        adapter = new ArsivAdapter(getContext(), pdfModelArrayList, myref);
        rvDergiArsivEkle.setAdapter(adapter);

        tumArsivler();

        fabDergiArsivEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertGoster();
            }
        });

        return v;
    }

    private void tumArsivler() {

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pdfModelArrayList.clear();

                for (DataSnapshot d : snapshot.getChildren()) {
                    PDFModel pdfModel = d.getValue(PDFModel.class);
                    pdfModel.setPdfid(d.getKey());
                    pdfModelArrayList.add(pdfModel);
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
        View tasarim = layout.inflate(R.layout.alert_arsiv_ekle, null);

        EditText etArsivAdi = tasarim.findViewById(R.id.etUnvani);
        EditText etArsivTarihi = tasarim.findViewById(R.id.etAdiSoyadi);
        EditText etArsivUrl = tasarim.findViewById(R.id.etOzelSayiUrl);


        AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
        ad.setTitle("Arşive Dergi Ekle");
        ad.setView(tasarim);
        ad.setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String arsivAdi = etArsivAdi.getText().toString().trim();
                String arsivTarihi= etArsivTarihi.getText().toString().trim();
                String arsivUrl = etArsivUrl.getText().toString().trim();

                String key = myref.push().getKey();
                PDFModel pdfModel = new PDFModel(key, arsivAdi,arsivTarihi, arsivUrl);
                myref.push().setValue(pdfModel);

                Toast.makeText(getContext(), "Arşive Dergi eklendi", Toast.LENGTH_SHORT).show();

            }
        });
        ad.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        ad.create().show();
    }
}