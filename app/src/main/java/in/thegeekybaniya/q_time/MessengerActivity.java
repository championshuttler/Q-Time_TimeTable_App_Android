package in.thegeekybaniya.q_time;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

/**
 * Created by Kabir on 28/01/2017.
 */

public class MessengerActivity extends Fragment {

        FirebaseDatabase mDB= FirebaseDatabase.getInstance();

    DatabaseReference mMsgRef;
FirebaseAuth mAuth;

    FirebaseUser user;
    ListView lv;


    Button send;
    TextView tv;
    EditText et;

    String message;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();



//        mDB.setPersistenceEnabled(true);

        mMsgRef=mDB.getReference();

        mMsgRef=mMsgRef.child("messages");

        mMsgRef.orderByKey();




        final String[] message = {null};


        View v= inflater.inflate(R.layout.messenger_activity, container, false);


        lv= (ListView) v.findViewById(R.id.lv);


        send= (Button) v.findViewById(R.id.button2);
        et= (EditText) v.findViewById(R.id.editText2);



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            if(!(et.getText().toString().isEmpty()))  {


                Message m = new Message(user.getDisplayName(), user.getEmail().replace('.',','), et.getText().toString(), ServerValue.TIMESTAMP);

                mMsgRef.push().setValue(m);


            }




            }
        });


        FirebaseListAdapter<Message> messageFirebaseListAdapter= new FirebaseListAdapter<Message>(getActivity(),
                Message.class,
                android.R.layout.two_line_list_item,
                mMsgRef) {
            @Override
            protected void populateView(View v, Message model, int position) {


                TextView l1, l2;

                l1= (TextView) v.findViewById(android.R.id.text1);

                l2= (TextView) v.findViewById(android.R.id.text2);


                if(user.getEmail().replace('.',',')==model.getEnEmail()){
                    l1.setText("You");


                }else
                {
                    l1.setText(model.getName());

                }



                l2.setText(model.getMessage());





            }
        };

        lv.setAdapter(messageFirebaseListAdapter);






        return v;

    }
}
