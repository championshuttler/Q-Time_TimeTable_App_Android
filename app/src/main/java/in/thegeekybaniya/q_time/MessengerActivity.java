package in.thegeekybaniya.q_time;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Kabir on 28/01/2017.
 */

public class MessengerActivity extends Fragment {


    Button send;
    TextView tv;
    EditText et;

    String message;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final String[] message = {null};


        View v= inflater.inflate(R.layout.messenger_activity, container, false);


        send= (Button) v.findViewById(R.id.btn);
        tv= (TextView) v.findViewById(R.id.tev);
        et= (EditText) v.findViewById(R.id.edt);



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                message[0] = et.getText().toString();

                tv.append("\nYou:"+ message[0]);



            }
        });



        return v;

    }
}
