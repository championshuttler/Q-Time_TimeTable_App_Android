package in.thegeekybaniya.q_time;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    private static final String TAG = "LoginActivity";
    private static int RC_SIGN_IN = 0;
    private GoogleApiClient mGoogleApiCLient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    private DatabaseReference mDatabase, mPutUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDatabase = MyDatabaseUtil.getDatabase().getReference();


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged: AUTH" + user.getDisplayName());

//                    mPutUser=mDatabase.push();
//
//
//
//                    User user1 = new User(user.getDisplayName(), user.getEmail(), user.getUid());
//
//                    mPutUser.setValue(user1);







                } else {
                    Log.d(TAG, "onAuthStateChanged: AuthCancel" + "User Logged Out");
                }
            }
        };


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiCLient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


            findViewById(R.id.sign_in_button).setOnClickListener(this);
            findViewById(R.id.sign_out_btn).setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener!=null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    private void signin(){
        Intent signInIntent= Auth.GoogleSignInApi.getSignInIntent(mGoogleApiCLient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result= Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if(result.isSuccess()){
                GoogleSignInAccount account= result.getSignInAccount();
                FirebaseAuthWithGoogle(account);


//                userToDB();




                Intent i = new Intent(this, MainActivity.class);




                startActivityForResult(i, 1);
//                userToDB();

            }
            else{
                Log.d(TAG, "onActivityResult: LOGIN FAILED!");
            }
        }
    }

    private void userToDB() {

        FirebaseUser user = mAuth.getCurrentUser();



        mPutUser=mDatabase.child("users").child(encodeEmail(user.getEmail()));



        User user1 = new User(user.getDisplayName(), user.getEmail(), user.getUid());

        mPutUser.setValue(user1);

    }
    public static String encodeEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    private void FirebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential= GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        mAuth.signInWithCredential(credential)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "onComplete: AUTH"+ " Signin with Creds COmplete"+ task.isSuccessful());


            userToDB();

                if (!task.isSuccessful()) {
                    Log.w(TAG, "signInWithCredential", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
            }
            }


        });
    }


    private void signout(){
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.d(TAG, "onConnectionFailed: Connection Failed!");



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sign_in_button: signin();
                break;
            case R.id.sign_out_btn: signout();
                break;
    }
}
}
