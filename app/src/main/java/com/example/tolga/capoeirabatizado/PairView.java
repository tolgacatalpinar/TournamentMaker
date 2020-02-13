package com.example.tolga.capoeirabatizado;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.graphics.Color.GRAY;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

public class PairView extends LinearLayout {

    TextView firstView;
    TextView secondView;
    String firstName;
    String secondName;
    boolean isSelected;
    int winner;
    boolean disable;


    public PairView( String firstName, String secondName, Context context, int firstIndex)
    {
        super(context);
        disable = false;
        firstView = new TextView( context);
        secondView = new TextView( context);
        this.firstName = firstName.substring(3);
        if( secondName.length() > 3)
            this.secondName = secondName.substring(3);
        firstView.setText( firstName);
        secondView.setText( secondName);

        this.setOrientation(LinearLayout.VERTICAL);
        ViewGroup parent = (ViewGroup) firstView.getParent();
        ViewGroup parent2 = (ViewGroup) secondView.getParent();

        if( parent != null)
            parent.removeView(firstView);
        if( parent2 != null)
            parent.removeView(secondView);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(400, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(400, LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(50,20,0,0);
        params2.setMargins(50,20,0,50);

        this.addView( firstView, params);
        this.addView( secondView, params2);



    }
    public void toGray()
    {
        firstView.setBackgroundColor(GRAY);
        secondView.setBackgroundColor(GRAY);
    }
//    public void setListener( final Button cancelButton, final Button chooseButton, final TextView firstCapoeiristaText, final TextView secondCapoeiristaText, final TextView infoText)
//    {
//        this.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if( !disable)
//                {
//                    cancelButton.setVisibility(View.VISIBLE);
//                    chooseButton.setVisibility(View.VISIBLE);
//                    firstCapoeiristaText.setVisibility(View.VISIBLE);
//                    secondCapoeiristaText.setVisibility(View.VISIBLE);
//                    infoText.setVisibility(View.VISIBLE);
//                    firstCapoeiristaText.setText( firstView.getText());
//                    secondCapoeiristaText.setText( secondView.getText());
//
//                    firstCapoeiristaText.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            firstCapoeiristaText.setBackgroundColor( GREEN);
//                            firstCapoeiristaText.setBackgroundColor( RED);
//                            firstView.setBackgroundColor(GREEN);
//                            secondView.setBackgroundColor(RED);
//                            winner = 1;
//                        }
//                    });
//                    secondCapoeiristaText.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            secondCapoeiristaText.setBackgroundColor( RED);
//                            secondCapoeiristaText.setBackgroundColor( GREEN);
//                            firstView.setBackgroundColor(RED);
//                            secondView.setBackgroundColor(GREEN);
//                            winner = 2;
//                        }
//                    });
//
//                    cancelButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            cancelButton.setVisibility(View.INVISIBLE);
//                            chooseButton.setVisibility(View.INVISIBLE);
//                            firstCapoeiristaText.setVisibility(View.INVISIBLE);
//                            secondCapoeiristaText.setVisibility(View.INVISIBLE);
//                            infoText.setVisibility(View.INVISIBLE);
//
//                            if( winner == 1)
//                            {
//                                firstView.setBackgroundColor( GREEN);
//                                secondView.setBackgroundColor( RED);
//                            }
//                            else if( winner == 2)
//                            {
//                                firstView.setBackgroundColor( RED);
//                                secondView.setBackgroundColor( GREEN);
//                            }
//                            else if( winner == 0)
//                            {
//                                firstView.setBackgroundColor( GRAY);
//                                secondView.setBackgroundColor( GRAY);
//                            }
//                            else
//                                Toast.makeText(getContext(), "Değişiklikler geri alındı!", Toast.LENGTH_LONG).show();
//                        }
//                    });
//
//                    chooseButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            cancelButton.setVisibility(View.INVISIBLE);
//                            chooseButton.setVisibility(View.INVISIBLE);
//                            firstCapoeiristaText.setVisibility(View.INVISIBLE);
//                            secondCapoeiristaText.setVisibility(View.INVISIBLE);
//                            infoText.setVisibility(View.INVISIBLE);
//
//                            if( winner == 1)
//                            {
//                                firstView.setBackgroundColor( GREEN);
//                                secondView.setBackgroundColor( RED);
//                                isSelected = true;
//                            }
//                            else if( winner == 2)
//                            {
//                                firstView.setBackgroundColor( RED);
//                                secondView.setBackgroundColor( GREEN);
//                                isSelected = true;
//                            }
//                            else
//                                Toast.makeText(getContext(), "Kazananı seçmedin!", Toast.LENGTH_LONG).show();
//                        }
//                    });
//                }
//                else
//                    Toast.makeText(getContext(), "Bu eşleşme değiştirilemez!", Toast.LENGTH_LONG).show();
//
//            }
//        });
//    }
    public void disable()
    {
        disable = true;
    }
    public void enable() {
        disable = false;
    }
    public String getFirstName()
    {
        return firstName;
    }
    public String getSecondName()
    {
        return secondName;
    }
}
