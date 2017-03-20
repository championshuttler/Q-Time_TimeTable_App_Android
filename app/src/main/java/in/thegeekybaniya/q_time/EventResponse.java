package in.thegeekybaniya.q_time;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventResponse extends AppCompatActivity {

    ArrayList<String> likeList, disLikeList, likeNames, disLikeNames;

    ListView lvLike, lvDislike;

    FirebaseDatabase mRootRef= FirebaseDatabase.getInstance();

    DatabaseReference mUsers;

    private static final String TAG = "EventResponse";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_response);

        mUsers = mRootRef.getReference().child("users");



        Intent i= getIntent();

        i.getExtras();

        likeList=i.getStringArrayListExtra("likelist");
        disLikeList=i.getStringArrayListExtra("dislikelist");

        lvLike= (ListView) findViewById(R.id.lvLike);

        lvDislike= (ListView) findViewById(R.id.lvDislike);

        likeNames= new ArrayList<>();
        disLikeNames= new ArrayList<>();

        for (String enEmail:
                likeList) {

            if(enEmail.equals("abcd")){continue;}
            DatabaseReference mUser;

            mUser= mUsers.child(enEmail);



            mUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    User u= dataSnapshot.getValue(User.class);

                    likeNames.add(u.getName());



                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });






        }


        for (String enEmail:
                disLikeList) {

            if(enEmail.equals("abcd")){continue;}


            DatabaseReference mUser;

            mUser= mUsers.child(enEmail);



            mUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    User u= dataSnapshot.getValue(User.class);

                    disLikeNames.add(u.getName());



                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });






        }




        ArrayAdapter<String> likeArrayAdapter =
                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, likeNames);


        ArrayAdapter<String> dislikeArrayAdapter =
                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, disLikeNames);


        lvLike.setAdapter(likeArrayAdapter);
        lvDislike.setAdapter(dislikeArrayAdapter);





    }
}
