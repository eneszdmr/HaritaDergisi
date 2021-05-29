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
import com.hgm.haritagenelmudurlugu.pdf.PDFActivity2;
import com.hgm.haritagenelmudurlugu.pdf.PDFAdapter;
import com.hgm.haritagenelmudurlugu.pdf.PDFAdapter2;
import com.hgm.haritagenelmudurlugu.pdf.PDFModel;
import com.hgm.haritagenelmudurlugu.pdf.PDFModel2;

import java.util.ArrayList;
import java.util.List;

public class OzelSayilarActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    public static List<PDFModel2> listOzel;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ozel_sayilar);
        recyclerView = findViewById(R.id.RV);
        database= FirebaseDatabase.getInstance().getReference("ozelSayilar");
       // listelerim();
        //örnek olarak bir array list oluşturur ve içerisine veritabanından gelen veriler listelenir
        listOzel = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));

        ItemClickListener itemClickListener=new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent=new Intent(OzelSayilarActivity.this, PDFActivity2.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        };
        PDFAdapter2 pdfAdapter=new PDFAdapter2(listOzel,this,itemClickListener);
        recyclerView.setAdapter(pdfAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    PDFModel2 pdfModel =dataSnapshot.getValue(PDFModel2.class);
                    listOzel.add(pdfModel);
                }
                pdfAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
/*
    private void listelerim() {
        listOzel = new ArrayList<>();
        listOzel.add(new PDFModel2("HARİTA DERGİSİ 18' İNCİ ÖZEL SAYISI", "1st International Symposium of the International Gravity Field Service GRAVITY FIELD OF THE EARTH”","https://www.harita.gov.tr/uploads/files/mapmagazinespecialissues/harita-dergisi-18inci-ozel-sayisi-37.pdf"));
        listOzel.add(new PDFModel2("HARİTA DERGİSİ 17' İNCİ ÖZEL SAYISI", "ANTALYA-II, BODRUM-II, ERDEK VE  MENTEŞ MAREOGRAF İSTASYONLARINA AİT 1984-2002 YILLARI ARASI DENİZ SEVİYESİ VE JEODEZİK ÖLÇÜLERİN DEĞERLENDİRİLMESİ","https://www.harita.gov.tr/uploads/files/mapmagazinespecialissues/harita-dergisi-17inci-ozel-sayisi-36.pdf"));
        listOzel.add(new PDFModel2("HARİTA DERGİSİ 16' INCI ÖZEL SAYISI", "T‹RKİYE ULUSAL TEMEL GPS AĞI-1999 ( TUTGA-99A )","https://www.harita.gov.tr/uploads/files/mapmagazinespecialissues/harita-dergisi-16inci-ozel-sayisi-35.pdf"));
        listOzel.add(new PDFModel2("HARİTA DERGİSİ 15' İNCİ ÖZEL SAYISI", "HARITACILIK BILIMI TARIHI","https://www.harita.gov.tr/uploads/files/mapmagazinespecialissues/harita-dergisi-15inci-ozel-sayisi-34.pdf"));
        listOzel.add(new PDFModel2("HARİTA DERGİSİ 14' ÜNCÜ ÖZEL SAYISI", "Kartografya' da Otomasyon","https://www.harita.gov.tr/uploads/files/mapmagazinespecialissues/harita-dergisi-14uncu-ozel-sayisi-33.pdf"));
        listOzel.add(new PDFModel2("HARİTA DERGİSİ 13' ÜNCÜ ÖZEL SAYISI", "PERSPEKTİF İZDÜŞÜM VE BLOKDİYAGRAM ÇİZİMLERİ","https://www.harita.gov.tr/uploads/files/mapmagazinespecialissues/harita-dergisi-13uncu-ozel-sayisi-32.pdf"));
        listOzel.add(new PDFModel2("HARİTA DERGİSİ 1' İNCİ ÖZEL SAYISI", "asdasdasddasdasdadada","https://www.harita.gov.tr/uploads/files/mapmagazinespecialissues/harita-dergisi-1inci-ozel-sayisi-20.pdf"));
        listOzel.add(new PDFModel2("HARİTA DERGİSİ 1' İNCİ ÖZEL SAYISI", "asdasdasddasdasdadada","https://www.harita.gov.tr/uploads/files/mapmagazinespecialissues/harita-dergisi-1inci-ozel-sayisi-20.pdf"));
        listOzel.add(new PDFModel2("HARİTA DERGİSİ 1' İNCİ ÖZEL SAYISI", "asdasdasddasdasdadada","https://www.harita.gov.tr/uploads/files/mapmagazinespecialissues/harita-dergisi-1inci-ozel-sayisi-20.pdf"));
        listOzel.add(new PDFModel2("HARİTA DERGİSİ 1' İNCİ ÖZEL SAYISI", "asdasdasddasdasdadada","https://www.harita.gov.tr/uploads/files/mapmagazinespecialissues/harita-dergisi-1inci-ozel-sayisi-20.pdf"));
        listOzel.add(new PDFModel2("HARİTA DERGİSİ 1' İNCİ ÖZEL SAYISI", "asdasdasddasdasdadada","https://www.harita.gov.tr/uploads/files/mapmagazinespecialissues/harita-dergisi-1inci-ozel-sayisi-20.pdf"));
    }

 */
}