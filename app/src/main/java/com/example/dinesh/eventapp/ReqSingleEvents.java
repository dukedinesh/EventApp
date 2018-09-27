package com.example.dinesh.eventapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ss.com.bannerslider.Slider;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.event.OnSlideClickListener;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class ReqSingleEvents extends AppCompatActivity {

    Toolbar toolbar;
    String event,url,event_name;
    TextView locaion, fromtxt, tilltxt, event_nametxt, destxt;
    ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    DatabaseReference mUserDatabase;
    DatabaseReference mDatabase;
    DatabaseReference mUserEvent;
    private Slider slider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_single_event);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        event = getIntent().getStringExtra("event_id");
        this.getSupportActionBar().setTitle(event);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        firebaseAuth = FirebaseAuth.getInstance();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Events").child(event);

        fromtxt = (TextView) findViewById(R.id.from);
        tilltxt = (TextView) findViewById(R.id.till);
        locaion = (TextView) findViewById(R.id.location);
        event_nametxt = (TextView) findViewById(R.id.event_name);
        destxt = (TextView) findViewById(R.id.des);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        slider = findViewById(R.id.banner_slider1);


        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                progressBar.setVisibility(View.VISIBLE);


                Slider.init(new PicassoImageLoadingService(ReqSingleEvents.this));
                slider.setAdapter(new MainSliderAdapter());

                url = dataSnapshot.child("Images").getValue().toString();
                Log.d("DDDDDDDDD", url);

                event_name = dataSnapshot.child("event_name").getValue().toString();
                final String from = dataSnapshot.child("from").getValue().toString();
                final String till = dataSnapshot.child("till").getValue().toString();
                final String location = dataSnapshot.child("location").getValue().toString();
                final String des = dataSnapshot.child("des").getValue().toString();


                String f = from;
                String t = till;

                String[] splitFrom = f.split("20");
                String[] splitTill = t.split("20");

                String firstSubString = splitFrom[0];
                String firstSubString1 = splitTill[0];

                event_nametxt.setText(event_name);
                fromtxt.setText(firstSubString + " - ");
                tilltxt.setText(firstSubString1);
                locaion.setText(location);
                destxt.setText(des);

                progressBar.setVisibility(View.GONE);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressBar.setVisibility(View.GONE);
            }
        });

        slider.setOnSlideClickListener(new OnSlideClickListener() {
            @Override
            public void onSlideClick(int position) {

                //Toast.makeText(SingleEventActivity.this,"hello ", Toast.LENGTH_SHORT).show();

            }
        });
    }


    class MainSliderAdapter extends SliderAdapter {

        @Override
        public int getItemCount() {
            return 3;
        }

        @Override
        public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
            switch (position) {
                case 0:
                    viewHolder.bindImageSlide(url);
                    break;
                case 1:
                    viewHolder.bindImageSlide(/*"https://assets.materialup.com/uploads/20ded50d-cc85-4e72-9ce3-452671cf7a6d/preview.jpg"*/url);
                    break;
                case 2:
                    viewHolder.bindImageSlide(/*"https://assets.materialup.com/uploads/76d63bbc-54a1-450a-a462-d90056be881b/preview.png"*/url);
                    break;
            }
        }
    }

}
