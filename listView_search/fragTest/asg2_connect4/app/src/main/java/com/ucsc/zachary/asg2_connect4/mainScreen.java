package com.ucsc.zachary.asg2_connect4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Observable;
import java.util.Observer;

public class mainScreen extends AppCompatActivity implements Observer{

    private model Model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        /*
        mainScreenFragment mScreenFrag = new mainScreenFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, mScreenFrag).commit();
        */
        Model = new model(this);
        Model.changeFrag(0);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}