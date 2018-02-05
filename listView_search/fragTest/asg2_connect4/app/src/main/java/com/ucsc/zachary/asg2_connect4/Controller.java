package com.ucsc.zachary.asg2_connect4;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by zachary on 11/9/17.
 */

public class Controller implements View.OnClickListener {

    private Fragment currentFrag;
    private model Model;

    public Controller(Fragment currentFrag, View view){
        this.currentFrag =  currentFrag;
        Model = new model(currentFrag.getActivity());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startGameButton: {
                Toast.makeText(currentFrag.getContext(),"You Clicked Start Game!",Toast.LENGTH_LONG).show();
                Model.changeFrag(1);
                break;
            }
            case R.id.resumeGameButton: {
                Toast.makeText(currentFrag.getContext(),"You Clicked Resume Game!",Toast.LENGTH_LONG).show();
                Model.changeFrag(1);
                break;
            }
            case R.id.exitButton: {
                Toast.makeText(currentFrag.getContext(),"You Clicked Exit!",Toast.LENGTH_LONG).show();
                Model.changeFrag(2);
                break;
            }
            case R.id.returnToMenu: {
                Toast.makeText(currentFrag.getContext(), "You Clicked Return To Menu", Toast.LENGTH_LONG).show();

                Model.changeFrag(2);
                break;
            }
        }
    }
}
