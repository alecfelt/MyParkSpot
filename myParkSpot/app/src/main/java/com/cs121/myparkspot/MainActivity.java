package com.cs121.myparkspot;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by alecfelt on 11/4/17.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity:";

    /*
     * Global variables: globals
     * Author: Alec Felt
     *
     * Purpose: Have these variables in the global scope
     *
     * @param: none
     * @return: none
     * TODO: none
     */

    private Button login, signup;
    private EditText email, password;

    /*
     * Acitvity Lifecycle functions: onCreate(Bundle), onStart()
     * Author: Alec Felt
     *
     * Purpose: Find views, set listeners, set main view, check firebase user
     *
     * @param: Bundle savedInstance
     * @return: none
     * TODO: none
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.login_button);
        login.setOnClickListener(this);
        signup = findViewById(R.id.signup_button);
        signup.setOnClickListener(this);
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
        database.autoLogin(this);
    }

    /*
     * Click Listener function: onClick(View)
     * Author: Alec Felt
     *
     * Purpose: pull text from EditText Views, Firebase login/signup
     *
     * @param: Bundle savedInstance
     * @return: none
     * TODO: none
     */

    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick()");
        String e = email.getText().toString(), p = password.getText().toString();
        if(e.equals("") || p.equals("")) {
            utility.toast(this, "Bad input");
        } else {
            if(v == login) {
                database.login(this, e, p);
            }
            else {
                database.signup(this, e, p);
            }
        }
    }

}
