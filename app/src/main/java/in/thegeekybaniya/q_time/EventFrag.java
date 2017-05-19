package in.thegeekybaniya.q_time;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.ChangeEventListener;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Kabir on 25/04/2017.
 */

public class EventFrag extends Fragment {



    private static final String TAG = "EventsActivity";
    ArrayList<Events> eventList;
    Button btnAddEvent;

    //    FirebaseDatabase mData= FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    ListView lv;
    DatabaseReference mrootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mEventRef = mrootRef.child("events");
    FirebaseAuth mAuth;

    FirebaseUser user;
    FloatingActionButton fab;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.events_activity, container, false);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

//        btnAddEvent = (Button) view.findViewById(R.id.Button);
        fab= (FloatingActionButton) view.findViewById(R.id.fab);


//        btnAddEvent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getActivity(), AddEvent.class);
//
//                startActivity(i);
//            }
//        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddEvent.class);

                startActivity(i);

            }
        });


        lv = (ListView) view.findViewById(R.id.lv2);


        mEventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        final FirebaseListAdapter<Event> fireBaseListAdapter = new FirebaseListAdapter<Event>(
                getActivity(),
                Event.class,
                R.layout.lucture_display1,
                mEventRef
        ) {
            @Override
            protected void onChildChanged(ChangeEventListener.EventType type, int index, int oldIndex) {
                super.onChildChanged(type, index, oldIndex);
            }

            @Override
            protected void onDataChanged() {
                super.onDataChanged();
            }

            @Override
            protected void populateView(View v, final Event model, int position) {


//                DataSnapshot


                final TextView eventTv, likeTv, dislikeTv;

                final ImageButton likeBtn, disBtn;


                eventTv = (TextView) v.findViewById(R.id.tvEvName);
                likeTv = (TextView) v.findViewById(R.id.tvLikes);
                dislikeTv = (TextView) v.findViewById(R.id.tvDis);

                likeBtn = (ImageButton) v.findViewById(R.id.btnLike);

                disBtn = (ImageButton) v.findViewById(R.id.btnDis);


                eventTv.setText(model.getName());
                likeTv.setText(Integer.toString(model.getLikes()));
                dislikeTv.setText(Integer.toString(model.getDislikes()));
                likeBtn.setImageResource(R.drawable.like);


                disBtn.setBackgroundResource(R.drawable.dislike);



                if ((!(model.likeList.contains(user.getEmail().replace('.', ',')))) && (!(model.dislikeList.contains(user.getEmail().replace('.', ','))))) {

                    disBtn.setBackgroundResource(R.drawable.dislike);

                } else if ((!(model.likeList.contains(user.getEmail().replace('.', ',')))) && ((model.dislikeList.contains(user.getEmail().replace('.', ','))))) {

                    disBtn.setImageResource(R.drawable.disliked);
                    likeBtn.setImageResource(R.drawable.like);

                } else if (((model.likeList.contains(user.getEmail().replace('.', ',')))) && (!(model.dislikeList.contains(user.getEmail().replace('.', ','))))) {
                    likeBtn.setImageResource(R.drawable.liked);
                    disBtn.setImageResource(R.drawable.dislike);
                }


                likeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if ((!(model.likeList.contains(user.getEmail().replace('.', ',')))) && (!(model.dislikeList.contains(user.getEmail().replace('.', ','))))) {

                            model.likePressedFirst();
                            likeBtn.setImageResource(R.drawable.liked);
                            disBtn.setImageResource(R.drawable.dislike);


                            mEventRef.child(model.getKey()).child("likes").setValue(model.getLikes());

                            mEventRef.child(model.getKey()).child("dislikes").setValue(model.getDislikes());

//                           mEventRef.child(model.getKey()).child("likeList").child(Integer.toString(model.likeList.size()-1)).setValue(user.getEmail().replace('.',','));
                            model.likeList.add(user.getEmail().replace('.', ','));
                            mEventRef.child(model.getKey()).child("likeList").setValue(model.likeList);
                            mEventRef.child(model.getKey()).child("dislikeList").setValue(model.dislikeList);


                        } else if ((!(model.likeList.contains(user.getEmail().replace('.', ',')))) && ((model.dislikeList.contains(user.getEmail().replace('.', ','))))) {
                            model.likePressed();
                            likeBtn.setImageResource(R.drawable.liked);
                            disBtn.setImageResource(R.drawable.dislike);


                            mEventRef.child(model.getKey()).child("likes").setValue(model.getLikes());

                            mEventRef.child(model.getKey()).child("dislikes").setValue(model.getDislikes());

//                            mEventRef.child(model.getKey()).child("likeList").child(Integer.toString(model.likeList.size()-1)).setValue(user.getEmail().replace('.',','));
                            model.dislikeList.remove(user.getEmail().replace('.', ','));
                            model.likeList.add(user.getEmail().replace('.', ','));
                            mEventRef.child(model.getKey()).child("likeList").setValue(model.likeList);
                            mEventRef.child(model.getKey()).child("dislikeList").setValue(model.dislikeList);

                        }
                    }
                });

                disBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if ((!(model.likeList.contains(user.getEmail().replace('.', ',')))) && (!(model.dislikeList.contains(user.getEmail().replace('.', ','))))) {

                            model.dislikePressedFirst();
                            disBtn.setImageResource(R.drawable.disliked);
                            likeBtn.setImageResource(R.drawable.like);


                            mEventRef.child(model.getKey()).child("likes").setValue(model.getLikes());


                            mEventRef.child(model.getKey()).child("dislikes").setValue(model.getDislikes());
//                            mEventRef.child(model.getKey()).child("dislikeList").child(Integer.toString(model.dislikeList.size()-1)).setValue(user.getEmail().replace('.',','));

                            model.dislikeList.add(user.getEmail().replace('.', ','));
                            mEventRef.child(model.getKey()).child("dislikeList").setValue(model.dislikeList);
                            mEventRef.child(model.getKey()).child("likeList").setValue(model.likeList);


                        } else if (((model.likeList.contains(user.getEmail().replace('.', ',')))) && (!(model.dislikeList.contains(user.getEmail().replace('.', ','))))) {
                            model.dislikePressed();
                            disBtn.setImageResource(R.drawable.disliked);
                            likeBtn.setImageResource(R.drawable.like);


                            mEventRef.child(model.getKey()).child("likes").setValue(model.getLikes());

                            mEventRef.child(model.getKey()).child("dislikes").setValue(model.getDislikes());
//                            mEventRef.child(model.getKey()).child("dislikeList").child(Integer.toString(model.dislikeList.size()-1)).setValue(user.getEmail().replace('.',','));
                            model.likeList.remove(user.getEmail().replace('.', ','));
                            model.dislikeList.add(user.getEmail().replace('.', ','));
                            mEventRef.child(model.getKey()).child("dislikeList").setValue(model.dislikeList);
                            mEventRef.child(model.getKey()).child("likeList").setValue(model.likeList);

                        }

                    }
                });


            }
        };

        lv.setAdapter(fireBaseListAdapter);
        lv.setItemsCanFocus(true);

        lv.setClickable(true);
        lv.setFocusable(true);

        AdapterView.OnItemClickListener itemCLickListener = new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Event model = fireBaseListAdapter.getItem(position);

                Log.d(TAG, "onItemClick: " + model.toString());

                Intent i = new Intent(getActivity(), EventResponse.class);

                i.putStringArrayListExtra("likelist", model.getLikeList());
                i.putStringArrayListExtra("dislikelist", model.getDislikeList());

                startActivity(i);


            }
        };



        lv.setOnItemClickListener(itemCLickListener);

        lv.setItemsCanFocus(true);

        lv.setClickable(true);


        return view;
    }



}
