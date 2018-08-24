package com.example.dinesh.eventapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class FrgmntTwo extends Fragment {


    private RecyclerView mUploadList;

    DatabaseReference mUsersDatabase;
    Context ctx;
    ImageView image;


    public FrgmntTwo() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frgmnt_two, container, false);

        mUploadList = (RecyclerView) view.findViewById(R.id.upload_list);
        image = (ImageView) view.findViewById(R.id.image);
        ctx = getActivity();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = current_user.getUid();

            mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("requested_events");

            mUploadList.setLayoutManager(new LinearLayoutManager(getContext()));
            mUploadList.setHasFixedSize(true);



            FirebaseRecyclerAdapter<UsersEventData, FriendsViewHolder> friendsRecyclerView = new FirebaseRecyclerAdapter<UsersEventData, FriendsViewHolder>(

                    UsersEventData.class,
                    R.layout.single_event_requested,
                    FriendsViewHolder.class,
                    mUsersDatabase
            ) {
                @Override
                protected void populateViewHolder(final FriendsViewHolder viewHolder, UsersEventData model, int position) {

                    final TextView btn = (TextView) viewHolder.itemView.findViewById(R.id.event);
                    final String list_user_id = getRef(position).getKey();

                    if (getItemCount() > 0) {

                        image.setVisibility(View.GONE);

                        mUsersDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {


                                final String userName = dataSnapshot.child("event").getValue().toString();

                                viewHolder.setName(userName);


                                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        Intent intent = new Intent(getContext(), ReqSingleEvents.class);
                                        intent.putExtra("event_id", list_user_id);
                                        startActivity(intent);

                                    }
                                });

                                btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        Intent intent = new Intent(getContext(), ReqSingleEvents.class);
                                        intent.putExtra("event_id", list_user_id);
                                        startActivity(intent);

                                    }
                                });


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            };

            mUploadList.setAdapter(friendsRecyclerView);



        } else {

            mUploadList.setVisibility(View.GONE);
        }


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();


        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = current_user.getUid();

            mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("requested_events");



            mUploadList.setLayoutManager(new LinearLayoutManager(getContext()));
            mUploadList.setHasFixedSize(true);

            FirebaseRecyclerAdapter<UsersEventData, FriendsViewHolder> friendsRecyclerView = new FirebaseRecyclerAdapter<UsersEventData, FriendsViewHolder>(

                    UsersEventData.class,
                    R.layout.single_event_requested,
                    FriendsViewHolder.class,
                    mUsersDatabase
            ) {
                @Override
                protected void populateViewHolder(final FriendsViewHolder viewHolder, UsersEventData model, int position) {

                    final TextView btn = (TextView) viewHolder.itemView.findViewById(R.id.event);
                    final String list_user_id = getRef(position).getKey();

                    if (getItemCount() > 0) {

                        image.setVisibility(View.GONE);

                        mUsersDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {


                                final String userName = dataSnapshot.child("event").getValue().toString();

                                viewHolder.setName(userName);


                                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        Intent intent = new Intent(getContext(), ReqSingleEvents.class);
                                        intent.putExtra("event_id", list_user_id);
                                        startActivity(intent);

                                    }
                                });

                                btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        Intent intent = new Intent(getContext(), ReqSingleEvents.class);
                                        intent.putExtra("event_id", list_user_id);
                                        startActivity(intent);

                                    }
                                });


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            };

            mUploadList.setAdapter(friendsRecyclerView);
        } else {
            mUploadList.setVisibility(View.GONE);
        }
    }


    public static class FriendsViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public FriendsViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }


        public void setName(String name) {
            TextView userName = (TextView) mView.findViewById(R.id.event);
            userName.setText(name);
        }


    }

    @Override
    public void onResume() {
        super.onResume();


        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = current_user.getUid();

            mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("requested_events");



            mUploadList.setLayoutManager(new LinearLayoutManager(getContext()));
            mUploadList.setHasFixedSize(true);

            FirebaseRecyclerAdapter<UsersEventData, FriendsViewHolder> friendsRecyclerView = new FirebaseRecyclerAdapter<UsersEventData, FriendsViewHolder>(

                    UsersEventData.class,
                    R.layout.single_event_requested,
                    FriendsViewHolder.class,
                    mUsersDatabase
            ) {
                @Override
                protected void populateViewHolder(final FriendsViewHolder viewHolder, UsersEventData model, int position) {

                    final TextView btn = (TextView) viewHolder.itemView.findViewById(R.id.event);
                    final String list_user_id = getRef(position).getKey();

                    if (getItemCount() > 0) {

                        image.setVisibility(View.GONE);

                        mUsersDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {


                                final String userName = dataSnapshot.child("event").getValue().toString();

                                viewHolder.setName(userName);


                                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        Intent intent = new Intent(getContext(), ReqSingleEvents.class);
                                        intent.putExtra("event_id", list_user_id);
                                        startActivity(intent);

                                    }
                                });

                                btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        Intent intent = new Intent(getContext(), ReqSingleEvents.class);
                                        intent.putExtra("event_id", list_user_id);
                                        startActivity(intent);

                                    }
                                });


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            };

            mUploadList.setAdapter(friendsRecyclerView);;
        } else {
            mUploadList.setVisibility(View.GONE);
        }

    }
}

