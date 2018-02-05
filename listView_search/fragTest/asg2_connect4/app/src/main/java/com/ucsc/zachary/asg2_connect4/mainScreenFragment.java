package com.ucsc.zachary.asg2_connect4;


import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class mainScreenFragment extends Fragment {

    private CanvasDraw draw;

    public mainScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_screen, container, false);

        Button startGameButton = (Button) view.findViewById(R.id.startGameButton);
        Button resumeGameButton = (Button) view.findViewById(R.id.resumeGameButton);
        Button exitButton = (Button) view.findViewById(R.id.exitButton);

        startGameButton.setOnClickListener(new Controller(this, startGameButton));
        resumeGameButton.setOnClickListener(new Controller(this, resumeGameButton));
        exitButton.setOnClickListener(new Controller(this, exitButton));

        return view;
    }
}
