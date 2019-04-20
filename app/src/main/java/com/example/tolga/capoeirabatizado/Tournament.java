package com.example.tolga.capoeirabatizado;

/**
 * Yapılacaklar
 * Eşleştirmelerden biri tıklandığında aşağıdaki eşleştir butonu çıkıyor. Hepsi tıklandığında çıkması lazım
 * tournament activity'e winner-loser belirlemek için butonlar, textview lar eklenmeli. Her pair için bu view'lar değişecek sürekli
 * Loser ve winner'lara göre bir sonraki turda kalan capoeirstaları belirleyip, "eslestir"in onclick ine onları koycam
 * Eğer 1 tane capoeirista açıkta kalıyorsa onu da kaybedenlerden biriyle eşleştircem.
 */


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import static android.graphics.Color.GREEN;

public class Tournament extends AppCompatActivity {

    ArrayList< LinearLayout> rounds;
    ArrayList<String> roundsAsStrings;
    ArrayList<LinearLayout> pairLayout;
    LinearLayout.LayoutParams params;
    LinearLayout.LayoutParams params2;
    private final String MESSAGE_KEY = "KEY";
    private ArrayList<Capoeirista> capoeiristas = new ArrayList<>();
    private LinearLayout allRoundsLl;
    private RelativeLayout rl;
    private Context context;
    private int numberOfRounds;
    private int currentRound;
    private int titleNumber;
    private int numberOfClickedPair;
    private Button matchingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);
        allRoundsLl = findViewById(R.id.allRoundsLl);
        matchingButton = findViewById(R.id.matchingButton);
        matchingButton.setVisibility( View.INVISIBLE);

        context = this;

        Intent intent = getIntent();
        ArrayList<String> list = intent.getStringArrayListExtra( MESSAGE_KEY);
        currentRound = 0;
        titleNumber = 0;
        numberOfClickedPair = 0;

        for( int i = 0; i < list.size(); i ++)
        {
            capoeiristas.add( new Capoeirista(list.get(i)));
        }

        numberOfRounds = calculateNumberOfRounds( capoeiristas.size());
        System.out.println("Round Number: " + numberOfRounds);

        // Shuffle the capoeiristas
        for( int i = 0; i < 10; i ++)
        {
            Collections.shuffle( capoeiristas);
        }


        rounds = new ArrayList<>();
        for(int i = 0; i < numberOfRounds; i ++)
        {
            LinearLayout temp = new LinearLayout( context);
            temp.setOrientation(LinearLayout.VERTICAL);
            rounds.add(temp);
        }



        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(50,20,0,0);
        params2.setMargins(50,20,0,50);

        // Turların string sayılarını arraylistte depola
        roundsAsStrings = new ArrayList<>();
        for( int i = 0; i < numberOfRounds; i ++)
        {
            if( numberOfRounds - i > 2) {
                roundsAsStrings.add(i + 1 + ". Tur");
                roundsAsStrings.add("-----");
            }
            else if( numberOfRounds - i == 2)
            {
                roundsAsStrings.add("Yarı Final");
                roundsAsStrings.add("---------");
            }
            else if( numberOfRounds - i == 1)
            {
                roundsAsStrings.add("Final");
                roundsAsStrings.add("-----");
            }
        }
        // capoeiristaların isimlerini textview a çevir
        ArrayList<TextView> capoeiristasOfCurrentRound = new ArrayList<TextView>();
        for( int i = 0; i < capoeiristas.size(); i ++)
        {
            TextView view = new TextView( context);
            view.setText(i + 1 + "- " + capoeiristas.get(i).getName());
            capoeiristasOfCurrentRound.add( view);
        }
        // Capoeiristaları ikili olarak eşleştirip arraylistte depola
        pairLayout = new ArrayList<>();
        for( int i = 0; i < capoeiristas.size() - 1; i += 2)
        {
            LinearLayout temp = new LinearLayout( context);
            temp.setOrientation(LinearLayout.VERTICAL);
            ViewGroup parent = (ViewGroup) capoeiristasOfCurrentRound.get(i).getParent();
            ViewGroup parent2 = (ViewGroup) capoeiristasOfCurrentRound.get(i + 1).getParent();

            if( parent != null)
                parent.removeView(capoeiristasOfCurrentRound.get(i));
            if( parent2 != null)
                parent.removeView(capoeiristasOfCurrentRound.get( i + 1));
            temp.addView( capoeiristasOfCurrentRound.get( i), params);
            temp.addView( capoeiristasOfCurrentRound.get( i + 1), params2);
            temp.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // Bug var, birden çok tıklanırsa???
                    //if( event.getAction() == MotionEvent.)
                    numberOfClickedPair ++;
                    v.setBackgroundColor(GREEN);
                    if( numberOfClickedPair == pairLayout.size())
                    {
                        matchingButton.setVisibility( View.VISIBLE);
                    }
                    return true;
                }
            });
            pairLayout.add( temp);
        }

        // İkili eşleştirmeleri mevcut turun altında göster
        for( int i = 0; i < pairLayout.size(); i ++)
        {
            if( i == 0)
            {
                TextView text1 = new TextView( context);
                TextView text2 = new TextView( context);
                text1.setText( roundsAsStrings.get( titleNumber));
                text2.setText(roundsAsStrings.get(titleNumber + 1));
                rounds.get(currentRound).addView(text1, params);
                rounds.get(currentRound).addView(text2, params);
            }
            rounds.get( currentRound).addView( pairLayout.get( i));
        }
        allRoundsLl.addView( rounds.get( currentRound));
        class buttonClick implements View.OnClickListener
        {
            @Override
            public void onClick(View v)
            {
                matchingButton.setVisibility(View.INVISIBLE);
                currentRound ++;
                numberOfClickedPair = 0;
                titleNumber += 2;

                ArrayList<TextView> capoeiristasOfCurrentRound = new ArrayList<TextView>();
                for( int i = 0; i < capoeiristas.size(); i ++)
                {
                    TextView view = new TextView( context);
                    view.setText(i + 1 + "- " + capoeiristas.get(i).getName());
                    capoeiristasOfCurrentRound.add( view);
                }

                pairLayout = new ArrayList<>();
                for( int i = 0; i < capoeiristas.size() - 1; i += 2)
                {
                    LinearLayout temp = new LinearLayout( context);
                    temp.setOrientation(LinearLayout.VERTICAL);
                    ViewGroup parent = (ViewGroup) capoeiristasOfCurrentRound.get(i).getParent();
                    ViewGroup parent2 = (ViewGroup) capoeiristasOfCurrentRound.get(i + 1).getParent();

                    if( parent != null)
                        parent.removeView(capoeiristasOfCurrentRound.get(i));
                    if( parent2 != null)
                        parent.removeView(capoeiristasOfCurrentRound.get( i + 1));
                    temp.addView( capoeiristasOfCurrentRound.get( i), params);
                    temp.addView( capoeiristasOfCurrentRound.get( i + 1), params2);
                    temp.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            // Bug var, birden çok tıklanırsa???
                            //if( event.getAction() == MotionEvent.ACTION_BUTTON_PRESS)
                            numberOfClickedPair ++;
                            v.setBackgroundColor(GREEN);
                            if( numberOfClickedPair == pairLayout.size())
                            {
                                matchingButton.setVisibility( View.VISIBLE);
                            }
                            return true;
                        }
                    });
                    pairLayout.add( temp);
                }
                for( int i = 0; i < pairLayout.size(); i ++)
                {
                    if( i == 0)
                    {
                        TextView text1 = new TextView( context);
                        TextView text2 = new TextView( context);
                        text1.setText( roundsAsStrings.get( titleNumber));
                        text2.setText(roundsAsStrings.get(titleNumber + 1));
                        rounds.get(currentRound).addView(text1, params);
                        rounds.get(currentRound).addView(text2, params);
                    }
                    rounds.get( currentRound).addView( pairLayout.get( i));
                }
                allRoundsLl.addView( rounds.get( currentRound));
            }
        }
        matchingButton.setOnClickListener(new buttonClick());




    }
    // BU METHOD hatalı olabilir
    public int calculateNumberOfRounds( int size)
    {
        if( size <= 1)
        {
            return 0;
        }
        else {
            if (size % 2 == 1) {
                size ++;
                return 1 + calculateNumberOfRounds( size / 2);
            }
            else
                return 1 + calculateNumberOfRounds( size / 2);
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
