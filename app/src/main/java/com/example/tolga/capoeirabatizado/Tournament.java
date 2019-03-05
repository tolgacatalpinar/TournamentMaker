package com.example.tolga.capoeirabatizado;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

public class Tournament extends AppCompatActivity {

    private final String MESSAGE_KEY = "KEY";
    private ArrayList<Capoeirista> capoeiristas = new ArrayList<>();
    private RelativeLayout allRoundsRl;
    private RelativeLayout rl;
    private Context context;
    private int numberOfRounds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);
        allRoundsRl = findViewById(R.id.allRoundsRl);

        context = this;

        Intent intent = getIntent();
        ArrayList<String> list = intent.getStringArrayListExtra( MESSAGE_KEY);


        // Width deneme (sil)
        numberOfRounds = calculateNumberOfRounds( list.size());
        System.out.println("numberOfRounds: " + numberOfRounds);

        //



        for( int i = 0; i < list.size(); i ++)
        {
            capoeiristas.add( new Capoeirista(list.get(i)));
        }

        // Shuffle the capoeiristas
        for( int i = 0; i < 10; i ++)
        {
            Collections.shuffle( capoeiristas);
        }

        // Show every match in relative layout as the first round
        for( int i = 0; i < capoeiristas.size(); i ++)
        {
            rl = new RelativeLayout(this);
            RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT / numberOfRounds);
            relativeParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            allRoundsRl.addView( rl);

            // devam et burdan..

            View capoeiristaView = rl.getChildAt( i + 1);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule( RelativeLayout.BELOW, capoeiristaView.getId());

            // Header
            if( i == 0)
            {
                TextView header = new TextView( context);
                header.setText(i + ". Tur");
                rl.addView( header, params);
            }


            TextView tv = new TextView( context);
            tv.setId( i);
            params.setMargins(50,30,0,0);
            tv.setText(capoeiristas.get(i).getName());
            rl.addView( tv, params);
            allRoundsRl.addView( rl);

        }
    }
    public int calculateNumberOfRounds( int size)
    {
        if( size == 1)
        {
            return 0;
        }
        else {
            if (size % 2 == 1) {
                size ++;
                return (size / 2) + calculateNumberOfRounds( size / 2);
            }
            else
                return (size / 2) + calculateNumberOfRounds( size / 2);
        }
    }

}
