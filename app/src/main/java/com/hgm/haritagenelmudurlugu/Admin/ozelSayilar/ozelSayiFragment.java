package com.hgm.haritagenelmudurlugu.Admin.ozelSayilar;

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
import com.hgm.haritagenelmudurlugu.R;
import com.hgm.haritagenelmudurlugu.pdf.PDFModel2;

import java.util.ArrayList;

public class ozelSayiFragment extends Fragment {
    private RecyclerView rvOzelSayi;
    private FloatingActionButton fabOzelSayiEkle;
    private PDFModel2 pdfModel;
    private OzelSayiAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference myref;
    private ArrayList<PDFModel2> pdfModelArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dergi_arsiv, container, false);
        fabOzelSayiEkle = v.findViewById(R.id.fabOzelSayiEkle);
        rvOzelSayi = v.findViewById(R.id.rvOzelSayi);

        database = FirebaseDatabase.getInstance();
        myref = database.getReference("ozelSayilar");


        rvOzelSayi.setHasFixedSize(true);
        rvOzelSayi.setLayoutManager(new LinearLayoutManager(getContext()));

        pdfModelArrayList = new ArrayList<>();

        adapter = new OzelSayiAdapter(getContext(), pdfModelArrayList, myref);
        rvOzelSayi.setAdapter(adapter);

        tumOzelSayilar();

        fabOzelSayiEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertGoster();
            }
        });

        return v;
    }

    private void tumOzelSayilar() {

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pdfModelArrayList.clear();

                for (DataSnapshot d : snapshot.getChildren()) {
                    PDFModel2 pdfModel = d.getValue(PDFModel2.class);
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
        View tasarim = layout.inflate(R.layout.alert_ozelsayi_ekle, null);

        EditText etOzelSayiAdi = tasarim.findViewById(R.id.etUnvani);
        EditText etOzelSayiDesc = tasarim.findViewById(R.id.etAdiSoyadi);
        EditText etOzelSayiUrl = tasarim.findViewById(R.id.etOzelSayiUrl);


        AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
        ad.setTitle("Özel Sayılara Dergi Ekle");
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
                if (TextUtils.isEmpty(etOzelSayiAdi.getText().toString())) {
                    Toast.makeText(getContext(), "Dergi Adı eksik", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(etOzelSayiDesc.getText().toString())) {
                    Toast.makeText(getContext(), "Dergi Açıklama eksik", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(etOzelSayiUrl.getText().toString())) {
                    Toast.makeText(getContext(), "Dergi URL eksik", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {

                    String OzelSayiAdi = etOzelSayiAdi.getText().toString().trim();
                    String OzelSayiDesc= etOzelSayiDesc.getText().toString().trim();
                    String OzelSayiUrl = etOzelSayiUrl.getText().toString().trim();

                    String key = myref.push().getKey();
                    PDFModel2 pdfModel = new PDFModel2(key, OzelSayiAdi,OzelSayiDesc, OzelSayiUrl);
                    myref.push().setValue(pdfModel);

                    Toast.makeText(getContext(), "Özel Sayılara Dergi eklendi", Toast.LENGTH_SHORT).show();



                }
                dialog.dismiss();
            }
        });
    }
}