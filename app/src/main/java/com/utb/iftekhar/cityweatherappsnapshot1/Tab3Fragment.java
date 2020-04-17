package com.utb.iftekhar.cityweatherappsnapshot1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by syedy on 02-04-2020.
 */

public class Tab3Fragment extends Fragment {

    private static final String TAG="Tab3Fragment";
    private Button button;
    private ImageView imageView;
    private int current_image=0;
    int[] images={R.drawable.about2,R.drawable.about3,
            R.drawable.about4,R.drawable.about5};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab3_frag, container, false);
        button=(Button)view.findViewById(R.id.button3);
        imageView=(ImageView)view.findViewById(R.id.imageView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(), "Testing button click 3", Toast.LENGTH_SHORT).show();
                current_image++;
                current_image=current_image%images.length;
                imageView.setImageResource(images[current_image]);

            }
        });
        return view;
    }
}
