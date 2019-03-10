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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

public class Tournament extends AppCompatActivity {

    private final String MESSAGE_KEY = "KEY";
    private ArrayList<Capoeirista> capoeiristas = new ArrayList<>();
    private LinearLayout allRoundsLl;
    private RelativeLayout rl;
    private Context context;
    private int numberOfRounds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);
        allRoundsLl = findViewById(R.id.allRoundsLl);

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
//        LinearLayout roundLinear = new LinearLayout( this);
//        roundLinear.setOrientation( LinearLayout.VERTICAL);
//
//        TextView roundNumber = new TextView( this);
//        roundNumber.setId( roundLinear.getChildCount());
//        roundNumber.setText( "1. Tur");
//
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        params.setMargins( 50, 30, 0, 0);
//        roundNumber.setLayoutParams( params);
//        roundLinear.addView( roundNumber);
//
//
//        for( int i = 0; i < capoeiristas.size(); i ++)
//        {
//
//            TextView tv = new TextView( this);
//            tv.setId(roundLinear.getChildCount());
//            tv.setText( capoeiristas.get(i).getName());
//            roundLinear.addView( tv, params);
//
//        }
//        allRoundsLl.addView( roundLinear, params);

        LinearLayout roundLinear = new LinearLayout( this);
        roundLinear.setOrientation( LinearLayout.VERTICAL);
        addTextViewIntoLinearLayout( roundLinear, "1.Tur");
        addTextViewIntoLinearLayout( roundLinear, "-----");

        for( int i = 0; i < capoeiristas.size() - 1; i += 2)
        {
            RelativeLayout rl = new RelativeLayout( this);
            rl.setId( allRoundsLl.getChildCount());
            TextView firstCapoeirista = new TextView( this);
            firstCapoeirista.setText( capoeiristas.get(i).getName());
            firstCapoeirista.setId( rl.getChildCount());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            params.setMargins( 50, 30, 0, 0);
            rl.addView(firstCapoeirista);

            addTextViewIntoRelativeLayout( rl, capoeiristas.get(i + 1).getName(), rl.getChildAt(rl.getChildCount() - 1));
            addViewIntoLinearLayout( roundLinear, rl);
            addTextViewIntoLinearLayout( roundLinear, "");
        }
        addViewIntoLinearLayout( allRoundsLl, roundLinear);

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

    public void addTextViewIntoLinearLayout( LinearLayout layout, String text)
    {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins( 50, 30, 0, 0);
        TextView tv = new TextView( this);
        tv.setId(layout.getChildCount());
        tv.setText( text);
        layout.addView( tv, params);
    }
    public void addTextViewIntoRelativeLayout( RelativeLayout rl, String text, View lastView)
    {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        params.addRule( RelativeLayout.BELOW, lastView.getId());
        TextView tv = new TextView( this);
        tv.setId(rl.getChildCount());
        tv.setText( text);
        rl.addView(tv, params);
//        params.setMargins( 50, 30, 0, 0);
    }
    public void addViewIntoLinearLayout( LinearLayout layout, View view)
    {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins( 50, 30, 0, 0);
        layout.addView( view, params);
    }

}
