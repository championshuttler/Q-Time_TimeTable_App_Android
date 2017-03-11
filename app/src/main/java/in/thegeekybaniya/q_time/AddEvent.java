package in.thegeekybaniya.q_time;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEvent extends AppCompatActivity {


    EditText etEventName;

    Button btnAdd;
    Events event ;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference().child("events");

    String key;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);


        etEventName= (EditText) findViewById(R.id.etEventName);
        btnAdd= (Button) findViewById(R.id.addEvent);



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                event =new Events(etEventName.getText().toString());

                key=mData.push().getKey();


                event.setKey(key);

                mData.child(key).setValue(event);

                finish();



            }
        });


    }
}
