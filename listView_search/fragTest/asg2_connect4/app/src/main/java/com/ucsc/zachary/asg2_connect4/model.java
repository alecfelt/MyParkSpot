package com.ucsc.zachary.asg2_connect4;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import java.util.Observable;

/**
 * Created by zachary on 11/6/17.
 */

public class model extends Observable{

    private FragmentActivity mActivity;
    //private Controller controller;

    public model(FragmentActivity mActivity){
        this.mActivity = mActivity;
    }

    // Function to switch between fragments on the MainActivity
    // Takes in an int which just represents which action is desired.
    public void changeFrag(int screenNum){
        //mActivity.getSupportFragmentManager().findFragmentByTag()
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                mActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.disallowAddToBackStack();

        if (screenNum == 0){
            mainScreenFragment mFrag = new mainScreenFragment();
            mActivity.getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mFrag).commit();
        }
        if (screenNum == 1) { // Goes from MainMenuFragment to GameFragment
            Fragment mFrag = mActivity.getSupportFragmentManager().findFragmentById(R.id.mainScreenFragment);
            if (mFrag != null){ // mFrag actually contains a Fragment
                if (mFrag.isVisible()) { // mFrag is currently being displayed on MainActivity
                    fragmentTransaction.remove(mFrag);
                }
            }
            Fragment gFrag = mActivity.getSupportFragmentManager().findFragmentById(R.id.gameFragment);
            if (gFrag != null){ // gFrag already exists, just reattach
                if (gFrag.isDetached()){
                    fragmentTransaction.attach(gFrag);
                }
            } else { // gFrag doesn't exist yet, create it and add
                gFrag = new gameFragment();
                fragmentTransaction.replace(R.id.container, gFrag).commit();
            }
        }
        if (screenNum == 2) { // Goes from GameFragment to MainMenuFragment
            Fragment gFrag = mActivity.getSupportFragmentManager().findFragmentById(R.id.gameFragment);
            if (gFrag != null) { // mFrag actually contains a Fragment
                if (gFrag.isVisible()) { // mFrag is currently being displayed on MainActivity
                    fragmentTransaction.remove(gFrag);
                }
            }
            Fragment mFrag = mActivity.getSupportFragmentManager().findFragmentById(R.id.mainScreenFragment);
            if (mFrag != null) { // mFrag already exists, just reattach
                if (mFrag.isDetached()){
                    fragmentTransaction.attach(mFrag);
                }
            } else { // mFrag doesn't exist yet, create it and add
                mFrag = new mainScreenFragment();
                fragmentTransaction.replace(R.id.container, mFrag).commit();
            }
        }

        // Submit all commits to the FragmentManager
        //fragmentTransaction.commitNow();
    }

    public void gameLogic() {

    }

}
