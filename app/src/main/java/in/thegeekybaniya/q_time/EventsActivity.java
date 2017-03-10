package in.thegeekybaniya.q_time;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.ChangeEventListener;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Kabir on 28/01/2017.
 */

public class EventsActivity extends Fragment {

    ArrayList<Events> eventList;
    Button btnAddEvent;
    ListView lv;

//    FirebaseDatabase mData= FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    DatabaseReference mrootRef= FirebaseDatabase.getInstance().getReference();
    DatabaseReference mEventRef= mrootRef.child("events");
    private static final String TAG = "EventsActivity";
     FirebaseAuth mAuth;

    FirebaseUser user ;







    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.events_activity, container, false);

        mAuth=FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        btnAddEvent= (Button) view.findViewById(R.id.Button);


        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AddEvent.class);

                startActivity(i);
            }
        });






        lv= (ListView) view.findViewById(R.id.lv2);


        FirebaseListAdapter<Events> fireBaseListAdapter= new FirebaseListAdapter<Events>(
                getActivity(),
                Events.class,
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
            protected void populateView (View v, final Events model, int position) {


//                DataSnapshot


                final TextView eventTv, likeTv, dislikeTv;

                Button likeBtn, disBtn;


                eventTv= (TextView) v.findViewById(R.id.tvEvName);
                likeTv=(TextView) v.findViewById(R.id.tvLikes);
                dislikeTv=(TextView) v.findViewById(R.id.tvDis);

                likeBtn= (Button) v.findViewById(R.id.btnLike);

                disBtn= (Button) v.findViewById(R.id.btnDis);


                eventTv.setText(model.getName());
                likeTv.setText(Integer.toString(model.getLikes()));
                dislikeTv.setText(Integer.toString(model.getDislikes()));

                likeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                       if((!(model.likeList.contains(user.getEmail().replace('.',','))))&&(!(model.dislikeList.contains(user.getEmail().replace('.',','))))){

                        model.likePressedFirst();

                        mEventRef.child(model.getKey()).child("likes").setValue(model.getLikes());

                           mEventRef.child(model.getKey()).child("dislikes").setValue(model.getDislikes());

//                           mEventRef.child(model.getKey()).child("likeList").child(Integer.toString(model.likeList.size()-1)).setValue(user.getEmail().replace('.',','));
                            model.likeList.add(user.getEmail().replace('.',','));
                           mEventRef.child(model.getKey()).child("likeList").setValue(model.likeList);
                           mEventRef.child(model.getKey()).child("dislikeList").setValue(model.dislikeList);



                       }else if ((!(model.likeList.contains(user.getEmail().replace('.',','))))&&((model.dislikeList.contains(user.getEmail().replace('.',',')))))
                        {
                            model.likePressed();

                            mEventRef.child(model.getKey()).child("likes").setValue(model.getLikes());

                            mEventRef.child(model.getKey()).child("dislikes").setValue(model.getDislikes());

//                            mEventRef.child(model.getKey()).child("likeList").child(Integer.toString(model.likeList.size()-1)).setValue(user.getEmail().replace('.',','));
                            model.dislikeList.remove(user.getEmail().replace('.',','));
                            model.likeList.add(user.getEmail().replace('.',','));
                            mEventRef.child(model.getKey()).child("likeList").setValue(model.likeList);
                            mEventRef.child(model.getKey()).child("dislikeList").setValue(model.dislikeList);

                        }
                    }
                });

                disBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if((!(model.likeList.contains(user.getEmail().replace('.',','))))&&(!(model.dislikeList.contains(user.getEmail().replace('.',','))))){

                            model.dislikePressedFirst();

                            mEventRef.child(model.getKey()).child("likes").setValue(model.getLikes());


                            mEventRef.child(model.getKey()).child("dislikes").setValue(model.getDislikes());
//                            mEventRef.child(model.getKey()).child("dislikeList").child(Integer.toString(model.dislikeList.size()-1)).setValue(user.getEmail().replace('.',','));

                            model.dislikeList.add(user.getEmail().replace('.',','));
                            mEventRef.child(model.getKey()).child("dislikeList").setValue(model.dislikeList);
                            mEventRef.child(model.getKey()).child("likeList").setValue(model.likeList);







                        }else if (((model.likeList.contains(user.getEmail().replace('.',','))))&&(!(model.dislikeList.contains(user.getEmail().replace('.',',')))))
                        {
                            model.dislikePressed();
                            mEventRef.child(model.getKey()).child("likes").setValue(model.getLikes());

                            mEventRef.child(model.getKey()).child("dislikes").setValue(model.getDislikes());
//                            mEventRef.child(model.getKey()).child("dislikeList").child(Integer.toString(model.dislikeList.size()-1)).setValue(user.getEmail().replace('.',','));
                            model.likeList.remove(user.getEmail().replace('.',','));
                            model.dislikeList.add(user.getEmail().replace('.',','));
                            mEventRef.child(model.getKey()).child("dislikeList").setValue(model.dislikeList);
                            mEventRef.child(model.getKey()).child("likeList").setValue(model.likeList);

                        }

                    }
                });





            }
        };

        lv.setAdapter(fireBaseListAdapter);








        return view;
    }






}

//        mEventRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                eventList = new ArrayList<>();
//
//                for (DataSnapshot child : dataSnapshot.getChildren()) {
//
//                    eventList.add(child.getValue(Events.class));
//
//
//                    lv=(ListView) view.findViewById(R.id.lv2);
//
//                    EventAdapter evAdp=new EventAdapter();
//
//                    lv.setAdapter(evAdp);
//
//
//
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//
//
//
//////        btnt.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
//////                Intent i = new Intent(getActivity(), LoginActivity.class);
////            }
////        });
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        return view;
//
//    }
//
//    private class EventAdapter extends BaseAdapter {
//
//
//        @Override
//        public int getCount() {
//            return eventList.size();
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return eventList.get(i);
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int i, View v, ViewGroup viewGroup) {
//
//            LayoutInflater li= getActivity().getLayoutInflater();
//
//            final Events event= (Events) getItem(i);
//            final EventHolder evholder;
//
//                if(v==null){
//                    v= li.inflate(R.layout.lucture_display1, null);
//
//                    evholder = new EventHolder();
//                    evholder.tv1= (TextView) v.findViewById(R.id.tvEvName);
//                    evholder.tv2= (TextView) v.findViewById(R.id.tvLikes);
//                    evholder.like= (Button) v.findViewById(R.id.btnLike);
//                    evholder.dislike=(Button) v.findViewById(R.id.btnDis);
//                    evholder.tv3= (TextView) v.findViewById(R.id.tvDis);
//
//                    v.setTag(evholder);
//
//
//
//                }else
//                evholder= (EventHolder) v.getTag();
//
//            evholder.tv1.setText(event.getName());
//            evholder.tv2.setText( Integer.toString(event.getLikes()));
//            evholder.tv3.setText(Integer.toString(event.getDislikes()));
//
//            evholder.like.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    event.likePressed();
//
//                    Log.d(TAG, "onClick: "+ event.getLikes());
//                    evholder.tv2.setText(Integer.toString(event.getLikes()));
//
//
//
//
//
//                }
//            });
//
//            evholder.dislike.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    event.dislikePressed();
//
//
//                    Log.d(TAG, "onClick: "+ event.getDislikes());
//                    evholder.tv3.setText( Integer.toString(event.getDislikes()));
//
//
//                }
//            });
//
//
//
//
//            return v;
//        }
//    }
//
//    private class EventHolder {
//        TextView tv1, tv2,tv3;
//        Button like, dislike;
//
//
//    }
