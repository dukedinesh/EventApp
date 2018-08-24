package com.example.dinesh.eventapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import ss.com.bannerslider.Slider;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.event.OnSlideClickListener;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class SingleEventActivity extends AppCompatActivity {

    Toolbar toolbar;
    DatabaseReference mUserDatabase;
    DatabaseReference mDatabase;
    DatabaseReference mUserEvent;
    String url, event;
    private Slider slider;
    TextView locaion, fromtxt, tilltxt, event_nametxt, destxt;
    ProgressBar progressBar;
    Button register_btn;
    private FirebaseAuth firebaseAuth;
    ProgressDialog dialog;
    String event_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_event);


        toolbar = (Toolbar) findViewById(R.id.toolbar);

        this.setSupportActionBar(toolbar);
        event = getIntent().getStringExtra("event_id");
        final String s = ((Event) this.getApplicationContext()).getSomeVariable();
        this.getSupportActionBar().setTitle(s);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        firebaseAuth = FirebaseAuth.getInstance();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Events").child(s);

        fromtxt = (TextView) findViewById(R.id.from);
        tilltxt = (TextView) findViewById(R.id.till);
        locaion = (TextView) findViewById(R.id.location);
        event_nametxt = (TextView) findViewById(R.id.event_name);
        destxt = (TextView) findViewById(R.id.des);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        register_btn = (Button) findViewById(R.id.register);
        slider = findViewById(R.id.banner_slider1);


        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                progressBar.setVisibility(View.VISIBLE);


                Slider.init(new PicassoImageLoadingService(SingleEventActivity.this));
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


        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (firebaseAuth.getCurrentUser() != null) {


                    dialog = ProgressDialog.show(SingleEventActivity.this, "Requesting",
                            "Please wait...", true);

                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = current_user.getUid();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("requested_events").child(s);
                    mUserEvent = FirebaseDatabase.getInstance().getReference().child("Events").child(s).child("requested_users").child(uid);


                    Map userMap = new HashMap();
                    userMap.put("requested_user", uid);
                    userMap.put("status", "pending");

                    final Map map = new HashMap();
                    map.put("event",s);
                    map.put("status", "pending");

                    mUserEvent.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                mDatabase.updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {


                                        dialog.dismiss();

                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                SingleEventActivity.this);

                                        // set title
                                        alertDialogBuilder.setTitle("Request Sent.");

                                        // set dialog message
                                        alertDialogBuilder
                                                .setMessage("You will be Informed, as soon as your request approves.")
                                                .setCancelable(false)
                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        // if this button is clicked, close
                                                        // current activity
                                                        SingleEventActivity.this.finish();
                                                    }
                                                });

                                        // create alert dialog
                                        AlertDialog alertDialog = alertDialogBuilder.create();

                                        // show it
                                        alertDialog.show();
                                        alertDialog.getButton(Dialog.BUTTON_POSITIVE);
                                        alertDialog.getButton(Dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#607D8A"));


                                    }
                                });

                            }

                        }
                    });

                } else {

                    Intent intent = new Intent(SingleEventActivity.this, LoginActivity.class);
                    intent.putExtra("event_id", s);
                    startActivity(intent);


                }

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
                    viewHolder.bindImageSlide("https://assets.materialup.com/uploads/20ded50d-cc85-4e72-9ce3-452671cf7a6d/preview.jpg");
                    break;
                case 2:
                    viewHolder.bindImageSlide(/*"https://assets.materialup.com/uploads/76d63bbc-54a1-450a-a462-d90056be881b/preview.png"*/url);
                    break;
            }
        }
    }

}
