package com.cs121.myparkspot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    /*
     * Globals/Utilities: Global Variables, toast(String)
     * Author: Alec Felt
     *
     * Purpose: Display text to activity view
     *
     * @param: String
     * @return: void
     * TODO: none
     */

    private static final String TAG = "SignUpActivity: ";
    private Button signup;
    private EditText first, middle, last, phone, street, state, city, zip;

    /*
     * Activity Lifecycle Functions: onCreate(Bundle)
     * Author: Alec Felt
     *
     * Purpose: Set activity View, find Views, set Firebase globals
     *
     * @param: Bundle
     * @return: none
     * TODO: none
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signup = findViewById(R.id.finish_signup_button);
        signup.setOnClickListener(this);
        first = findViewById(R.id.firstName);
        last = findViewById(R.id.lastName);
        middle = findViewById(R.id.middleName);
        phone = findViewById(R.id.phone);
        street = findViewById(R.id.street);
        state = findViewById(R.id.state);
        city = findViewById(R.id.city);
        zip = findViewById(R.id.zip);
    }

    /*
     * Click Listener Function: onClick(View)
     * Author: Alec Felt
     *
     * Purpose: Check the result of processText() then push parkingSpot and navigate to HomeActivity
     *
     * @param: View
     * @return: void
     * TODO: none
     */

    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick()");
        signup(); navigation.navToHome(this);
    }

    /*
     * Firebase Function: signup()
     * Author: Alec Felt
     *
     * Purpose: Gather text from EditText Views, create and push user object
     * to database, navigate to HomeActivity
     *
     * @param: void
     * @return: void
     * TODO: Error message
     */

    public void signup() {
        Log.i(TAG, "signup()");
        address a = new address(street.getText().toString(),
                state.getText().toString(),
                city.getText().toString(),
                zip.getText().toString());

        user u = new user(first.getText().toString(),
                middle.getText().toString(),
                last.getText().toString(),
                phone.getText().toString(),
                FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                a);
        database.pushUser(this, u);
    }
}
