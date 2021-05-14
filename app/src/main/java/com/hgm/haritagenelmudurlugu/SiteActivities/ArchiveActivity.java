package com.hgm.haritagenelmudurlugu.SiteActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hgm.haritagenelmudurlugu.R;
import com.hgm.haritagenelmudurlugu.pdf.ItemClickListener;
import com.hgm.haritagenelmudurlugu.pdf.PDFActivity;
import com.hgm.haritagenelmudurlugu.pdf.PDFAdapter;
import com.hgm.haritagenelmudurlugu.pdf.PDFModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArchiveActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    public static List<PDFModel> list;
    DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);

        database = FirebaseDatabase.getInstance().getReference("dergiArsivi");
        recyclerView = findViewById(R.id.RV);
        list = new ArrayList<>();
        //listeler();
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(ArchiveActivity.this, PDFActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        };
        PDFAdapter pdfAdapter = new PDFAdapter(list, this, itemClickListener);


        recyclerView.setAdapter(pdfAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PDFModel pdfModel = dataSnapshot.getValue(PDFModel.class);
                    list.add(pdfModel);

                }
                pdfAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
/*
    private void listeler() {
        list.add(new PDFModel("Harita Dergisi 165. Sayı", "https://www.harita.gov.tr/uploads/files/mapmagazines/harita-dergisi-165-sayi-1276.pdf"));
        list.add(new PDFModel("Harita Dergisi 164. Sayı", "https://www.harita.gov.tr/uploads/files/mapmagazines/harita-dergisi-164-sayi-1274.pdf"));
        list.add(new PDFModel("Harita Dergisi 163. Sayı", "https://www.harita.gov.tr/uploads/files/mapmagazines/harita-dergisi-163-sayi-56.pdf"));
        list.add(new PDFModel("Harita Dergisi 162. Sayı", "https://www.harita.gov.tr/uploads/files/mapmagazines/harita-dergisi-162-sayi-54.pdf"));
        list.add(new PDFModel("Harita Dergisi 161. Sayı", "https://www.harita.gov.tr/uploads/files/mapmagazines/harita-dergisi-161-sayi-53.pdf"));
        list.add(new PDFModel("Harita Dergisi 160. Sayı", "https://www.harita.gov.tr/uploads/files/mapmagazines/harita-dergisi-160-sayi-52.pdf"));
        list.add(new PDFModel("Harita Dergisi 159. Sayı", "https://www.harita.gov.tr/uploads/files/mapmagazines/harita-dergisi-159-sayi-51.pdf"));
        list.add(new PDFModel("Harita Dergisi 158. Sayı", "https://www.harita.gov.tr/uploads/files/mapmagazines/harita-dergisi-158-sayi-50.pdf"));
    }

 */
}
