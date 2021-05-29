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
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        ItemClickListener itemClickListener = new ItemClickListener() {
            //interface ile tıklanma olayınde ne yapılacağı seçilir
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

}
