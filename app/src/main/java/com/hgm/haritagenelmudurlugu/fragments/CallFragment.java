package com.hgm.haritagenelmudurlugu.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hgm.haritagenelmudurlugu.Admin.LoginActivity;
import com.hgm.haritagenelmudurlugu.MainActivity;
import com.hgm.haritagenelmudurlugu.R;
import com.hgm.haritagenelmudurlugu.SiteActivities.EntranceActivity;


public class CallFragment extends Fragment {
    TextView call_title;
    ImageView btninstagram,btntwitter;
static int i=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_call, container, false);

        call_title = rootView.findViewById(R.id.call_title);
        btninstagram = rootView.findViewById(R.id.btninstagram);
        btntwitter = rootView.findViewById(R.id.btntwitter);
//social media buttons
        call_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if (i==5){
                    i=0;
                    Intent intent=new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);

                }

            }
        });

        btninstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent instagramintent=openinstagram(getActivity());
                startActivity(instagramintent);

            }
        });

        btntwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent twitterinten=opentwitter(getActivity());
                startActivity(twitterinten);
            }
        });


        return rootView;
    }

    public static Intent openinstagram(Context context){
        try {
            context.getPackageManager()
                    .getPackageInfo("com.instagram",0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/harita_turkiye/"));
        }
        catch (Exception e){
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/harita_turkiye/"));

        }
    }

    public static Intent opentwitter(Context context){
        try {
            context.getPackageManager()
                    .getPackageInfo("com.twitter",0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/harita_turkiye"));
        }
        catch (Exception e){
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/harita_turkiye"));

        }
    }
}