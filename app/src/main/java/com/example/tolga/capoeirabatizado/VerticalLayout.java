package com.example.tolga.capoeirabatizado;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class VerticalLayout extends LinearLayout {
    int size;
    ArrayList<String> capoeiristas;
    public VerticalLayout(Context context, String title, ArrayList<String> capoeiristas)
    {
        super(context);
        this.capoeiristas = capoeiristas;
        size = capoeiristas.size();
        this.setOrientation(VERTICAL);
        String seperator = "";
        for( int i = 0; i < title.length() * 3 / 2; i ++)
        {
            seperator += "-";
        }
        PairView firstPair = new PairView(title, seperator, context, 0);
        firstPair.disable();
        firstPair.setBackgroundColor(0);
        addPair( firstPair);
        addCapoeiristas();
    }
    // Constructor for winner
    public VerticalLayout(Context context, String title, String winner)
    {
        super(context);
        this.setOrientation(VERTICAL);
        String seperator = "";
        for( int i = 0; i < title.length() * 3 / 2; i ++)
        {
            seperator += "-";
        }
        PairView firstPair = new PairView(title, seperator, context, 0);
        firstPair.disable();
        firstPair.setBackgroundColor(0);
        addPair( firstPair);

        PairView temp = new PairView(winner, "", context, 0);
        temp.firstView.setBackgroundColor(Color.GREEN);
        addPair( temp);
    }
    public void addPair( PairView pair)
    {
        ViewGroup parent = (ViewGroup) pair.getParent();
        ViewGroup parent2 = (ViewGroup) pair.getParent();

        if( parent != null)
            parent.removeView(pair);
        if( parent2 != null)
            parent.removeView(pair);
        this.addView( pair);
    }
    public void disable()
    {
        for( int i = 1; i < getChildCount(); i ++)
        {
            ((PairView)getChildAt(i)).disable();
        }
    }

    public void addCapoeiristas()
    {
        if( size % 2 == 0 && size > 0)
        {
            for (int i = 0; i < size - 1; i += 2)
            {
                PairView pair = new PairView(i + 1 + "- " + capoeiristas.get(i), i + 2 + "- " + capoeiristas.get( i + 1), super.getContext(), i);
                pair.toGray();
                addPair( pair);
            }
        }
        else if( size % 2 == 1)
        {
            for( int i = 0; i < size - 1; i += 2)
            {
                PairView pair = new PairView(i + 1 + "- " + capoeiristas.get(i), i + 2 + "- " + capoeiristas.get( i + 1), super.getContext(), i);
                pair.toGray();
                addPair( pair);
            }
            PairView lastPair = new PairView(size + "- " + capoeiristas.get(size - 1), (size + 1) + "- " + "", super.getContext(), size - 1);
            lastPair.toGray();
            addPair( lastPair);
        }
    }
}
