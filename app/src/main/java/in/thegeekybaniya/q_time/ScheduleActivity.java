package in.thegeekybaniya.q_time;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Kabir on 28/01/2017.
 */



public class ScheduleActivity extends Fragment {

    static boolean calledAlready = false;


//    FirebaseDatabase mDB= FirebaseDatabase.getInstance();






    Calendar calendar = Calendar.getInstance();
    int day = calendar.get(Calendar.DAY_OF_WEEK);






    ListView lv;
    Lectures l;

    ArrayList<Lectures> lectureList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        mDB.setPersistenceEnabled(true);
//


//        if (!calledAlready)
//        {
//            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//            calledAlready = true;
//        }
//
        FirebaseDatabase mDB = FirebaseDatabase.getInstance();
//
        DatabaseReference mrootRef=mDB.getReference();



        DatabaseReference mDayRef= mrootRef.child("days");





        final View v=inflater.inflate(R.layout.schedule_activity, container,false);


        switch (day) {
            case Calendar.SUNDAY:
                mDayRef=mDayRef.child("monday");
                break;
                // Current day is Sunday

            case Calendar.MONDAY:
                mDayRef=mDayRef.child("monday");
                break;
                // Current day is Monday

            case Calendar.TUESDAY:
                mDayRef= mDayRef.child("tuesday");
                break;

            case Calendar.WEDNESDAY:
                mDayRef= mDayRef.child("wednesday");
                break;

            case Calendar.THURSDAY:
                mDayRef= mDayRef.child("thursday");
                break;

            case Calendar.FRIDAY:
                mDayRef= mDayRef.child("friday");
                break;

            case Calendar.SATURDAY:
                mDayRef= mDayRef.child("monday");
                break;
                // etc.
        }


        mDayRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                lectureList= new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    lectureList.add(child.getValue(Lectures.class));


                }

                lv= (ListView) v.findViewById(R.id.lv);

                LectureAdapter lecAdapter= new LectureAdapter();

                lv.setAdapter(lecAdapter);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





//        lectureList.add(new Lectures("CHL", "Dr.Jasleen","10:05", "11:00", "1008",1));
//        lectureList.add(new Lectures("PHL", "Mrs.Shweta","11:30", "12:25", "1008",2));
//        lectureList.add(new Lectures("EML", "Dr.Jaskaran","12:25", "01:20", "1008",3));
//        lectureList.add(new Lectures("ET", "Mr.Manoj","02:15", "03:10", "1008",4));
//        lectureList.add(new Lectures("ED", "Mr.Soumil","03:40", "04:35", "1008" , 5));
//        lectureList.add(new Lectures("CHL", "Dr.Jasleen","04:35", "05:30", "1008", 6));







        return v;

    }

    private class LectureAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return lectureList.size();
        }

        @Override
        public Object getItem(int i) {
            return lectureList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater li= getActivity().getLayoutInflater();

            Lectures lec= (Lectures) getItem(i);

            LectureViewHolder lvHolder;
            if(view==null) {

                view = li.inflate(R.layout.lecture_display, null);

                lvHolder = new LectureViewHolder();
                lvHolder.tv1 = (TextView) view.findViewById(R.id.tv1);
                lvHolder.tv2 = (TextView) view.findViewById(R.id.tv2);
                lvHolder.tv3 = (TextView) view.findViewById(R.id.tv3);

                view.setTag(lvHolder);
            }else
                lvHolder= (LectureViewHolder) view.getTag();

            lvHolder.tv1.setText("Subject:"  + lec.getSubject());
            lvHolder.tv2.setText("Start Time:"+lec.getStartTime() +"  End Time:"+lec.getEndTime() );
            lvHolder.tv3.setText("Teacher:"+ lec.getTeacher()+ "   Room No.: "+ lec.getRoomno() );

//            lvHolder.tv1.setText("Hello");
//            lvHolder.tv2.setText("Hello");
//            lvHolder.tv3.setText("Hello");



            return view;

        }


    }

    private class LectureViewHolder {

        TextView tv1,tv2,tv3;
    }
}
