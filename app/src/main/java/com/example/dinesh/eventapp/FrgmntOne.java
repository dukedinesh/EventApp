package com.example.dinesh.eventapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("ValidFragment")
public class FrgmntOne extends Fragment {

    private RecyclerView mUploadList;
    ProgressBar progressBar;
    private DatabaseReference mFriendsDatabase;
    private DatabaseReference mUsersDatabase;
    Context ctx;


    public FrgmntOne() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);


        mUploadList = (RecyclerView) view.findViewById(R.id.upload_list);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        ctx = getActivity();


        mFriendsDatabase = FirebaseDatabase.getInstance().getReference().child("Events");
        mFriendsDatabase.keepSynced(true);

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Events");
        mUsersDatabase.keepSynced(true);



        //RecyclerView

        mUploadList.setLayoutManager(new LinearLayoutManager(getContext()));
        mUploadList.setHasFixedSize(true);


        //uploadListAdapter = new UploadListAdapter(fileNameList);
        progressBar.setVisibility(View.VISIBLE);


        return view;
    }

    public void firebaseSearch(String searchText) {

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        Query firebaseSearchQuery = rootRef.child("Events").orderByChild("event_name/").startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerAdapter<MyDataSetGet, FriendsViewHolder> friendsRecyclerView = new FirebaseRecyclerAdapter<MyDataSetGet, FriendsViewHolder>(

                MyDataSetGet.class,
                R.layout.list_item_single,
                FriendsViewHolder.class,
                firebaseSearchQuery

        ) {

            @Override
            protected void populateViewHolder(final FriendsViewHolder viewHolder, MyDataSetGet model, int position) {

                final String list_user_id = getRef(position).getKey();


                final Button btn = (Button) viewHolder.itemView.findViewById(R.id.btn_register);

                mFriendsDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        final String userName = dataSnapshot.child("event_name").getValue().toString();
                        final String from = dataSnapshot.child("from").getValue().toString();
                        final String till = dataSnapshot.child("till").getValue().toString();
                        final String location = dataSnapshot.child("location").getValue().toString();
                        final String image = dataSnapshot.child("Images").getValue().toString();


                        String f = from;
                        String t = till;

                        String[] splitFrom = f.split("20");
                        String[] splitTill = t.split("20");

                        String firstSubString = splitFrom[0];
                        String firstSubString1 = splitTill[0];

                        viewHolder.setName(userName);
                        viewHolder.setFrom(firstSubString);
                        viewHolder.setTill(firstSubString1);
                        viewHolder.setLocation(location);
                        viewHolder.setImage(image);


                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent chatIntent = new Intent(getContext(), SingleEventActivity.class);
                                final String s = ((Event) ctx.getApplicationContext()).setSomeVariable(list_user_id);
                                chatIntent.putExtra("event_id", list_user_id);
                                startActivity(chatIntent);


                            }
                        });

                        progressBar.setVisibility(View.GONE);

                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent chatIntent = new Intent(getContext(), SingleEventActivity.class);
                                final String s1 = ((Event) ctx.getApplicationContext()).setSomeVariable(list_user_id);
                                chatIntent.putExtra("event_id", list_user_id);
                                startActivity(chatIntent);

                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        progressBar.setVisibility(View.GONE);
                    }
                });


            }


        };


        mUploadList.setAdapter(friendsRecyclerView);
    }

    @Override
    public void onStart() {
        super.onStart();


        FirebaseRecyclerAdapter<MyDataSetGet, FriendsViewHolder> friendsRecyclerView = new FirebaseRecyclerAdapter<MyDataSetGet, FriendsViewHolder>(

                MyDataSetGet.class,
                R.layout.list_item_single,
                FriendsViewHolder.class,
                mFriendsDatabase

        ) {
            @Override
            protected void populateViewHolder(final FriendsViewHolder viewHolder, MyDataSetGet model, int position) {

                final String list_user_id = getRef(position).getKey();


                final Button btn = (Button) viewHolder.itemView.findViewById(R.id.btn_register);

                mUsersDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        final String userName = dataSnapshot.child("event_name").getValue().toString();
                        final String from = dataSnapshot.child("from").getValue().toString();
                        final String till = dataSnapshot.child("till").getValue().toString();
                        final String location = dataSnapshot.child("location").getValue().toString();
                        final String image = dataSnapshot.child("Images").getValue().toString();


                        String f = from;
                        String t = till;

                        String[] splitFrom = f.split("20");
                        String[] splitTill = t.split("20");

                        String firstSubString = splitFrom[0];
                        String firstSubString1 = splitTill[0];

                        viewHolder.setName(userName);
                        viewHolder.setFrom(firstSubString);
                        viewHolder.setTill(firstSubString1);
                        viewHolder.setLocation(location);
                        viewHolder.setImage(image);


                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent chatIntent = new Intent(getContext(), SingleEventActivity.class);
                                final String s = ((Event) ctx.getApplicationContext()).setSomeVariable(list_user_id);
                                chatIntent.putExtra("event_id", list_user_id);
                                startActivity(chatIntent);


                            }
                        });

                        progressBar.setVisibility(View.GONE);

                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent chatIntent = new Intent(getContext(), SingleEventActivity.class);
                                final String s1 = ((Event) ctx.getApplicationContext()).setSomeVariable(list_user_id);
                                chatIntent.putExtra("event_id", list_user_id);
                                startActivity(chatIntent);

                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        progressBar.setVisibility(View.GONE);
                    }
                });

            }
        };

        mUploadList.setAdapter(friendsRecyclerView);


    }

    public static class FriendsViewHolder extends RecyclerView.ViewHolder {

        View mView;


        public FriendsViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }


        public void setName(String name) {
            TextView userName = (TextView) mView.findViewById(R.id.event_name);
            userName.setText(name);
        }


        public void setFrom(String from) {

            TextView fromTxt = (TextView) mView.findViewById(R.id.from);
            fromTxt.setText(from);

        }

        public void setTill(String till) {

            TextView tillTxt = (TextView) mView.findViewById(R.id.till);
            tillTxt.setText(till);

        }

        public void setLocation(String location) {

            TextView locationTxt = (TextView) mView.findViewById(R.id.location);
            locationTxt.setText(location);

        }

        public void setImage(String image) {


            if (!image.equals("default")) {

                ImageView imageView = (ImageView) mView.findViewById(R.id.main_image);
                Picasso
                        .with(mView.getContext())
                        .load(image)
                        .placeholder(R.drawable.place)
                        .into(imageView);

            }


        }

    }


}

