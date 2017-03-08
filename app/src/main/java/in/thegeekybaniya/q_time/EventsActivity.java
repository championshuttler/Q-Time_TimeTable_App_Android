package in.thegeekybaniya.q_time;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Kabir on 28/01/2017.
 */

public class EventsActivity extends Fragment {

    ArrayList<Events> eventList;
    Button btnt;
    ListView lv;

    DatabaseReference mrootRef= FirebaseDatabase.getInstance().getReference();
    DatabaseReference mEventRef= mrootRef.child("events");






    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.events_activity, container, false);

        mEventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                eventList = new ArrayList<>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    eventList.add(child.getValue(Events.class));


                    lv=(ListView) view.findViewById(R.id.lv2);

                    EventAdapter evAdp=new EventAdapter();

                    lv.setAdapter(evAdp);



                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




////        btnt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent i = new Intent(getActivity(), LoginActivity.class);
//            }
//        });















        return view;

    }

    private class EventAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return eventList.size();
        }

        @Override
        public Object getItem(int i) {
            return eventList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View v, ViewGroup viewGroup) {

            LayoutInflater li= getActivity().getLayoutInflater();

            final Events event= (Events) getItem(i);
            final EventHolder evholder;

                if(v==null){
                    v= li.inflate(R.layout.lucture_display1, null);

                    evholder = new EventHolder();
                    evholder.tv1= (TextView) v.findViewById(R.id.tvEvName);
                    evholder.tv2= (TextView) v.findViewById(R.id.tvLikes);
                    evholder.like= (Button) v.findViewById(R.id.btnLike);
                    evholder.dislike=(Button) v.findViewById(R.id.btnDis);
                    evholder.tv3= (TextView) v.findViewById(R.id.tvDis);

                    v.setTag(evholder);



                }else
                evholder= (EventHolder) v.getTag();

//            evholder.tv1.setText(event.getName());
//            evholder.tv2.setText( event.getLikes());
//            evholder.tv3.setText(event.getDislikes());

            evholder.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {




                }
            });




            return v;
        }
    }

    private class EventHolder {
        TextView tv1, tv2,tv3;
        Button like, dislike;

        boolean likestate=false, dislikestate=false;

    }
}
