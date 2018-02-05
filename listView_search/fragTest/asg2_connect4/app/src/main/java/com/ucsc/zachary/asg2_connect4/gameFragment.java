package com.ucsc.zachary.asg2_connect4;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class gameFragment extends Fragment {

    public CanvasDraw draw;

    public gameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        draw = new CanvasDraw(getContext());
        container.addView(new CanvasDraw(getContext()));
        Button returnToMenu = (Button) view.findViewById(R.id.returnToMenu);
        returnToMenu.setOnClickListener(new Controller(this, returnToMenu));

        return view;
    }


}
