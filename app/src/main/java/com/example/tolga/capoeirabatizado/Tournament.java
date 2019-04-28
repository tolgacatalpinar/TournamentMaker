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
import android.graphics.Color;
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

import static android.graphics.Color.GRAY;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

public class Tournament extends AppCompatActivity {

    ArrayList< LinearLayout> rounds;
    ArrayList<String> roundsAsStrings;
    ArrayList<LinearLayout> pairLayout;
    LinearLayout.LayoutParams params;
    LinearLayout.LayoutParams params2;
    private final String MESSAGE_KEY = "KEY";
    private ArrayList<Capoeirista> capoeiristas = new ArrayList<>();
    private LinearLayout allRoundsLl;
    private Button cancelButton;
    private Button chooseButton;
    private TextView firstCapoeiristaText;
    private TextView secondCapoeiristaText;
    private TextView infoText;
    private Context context;
    private int numberOfRounds;
    private int currentRound;
    private int titleNumber;
    private int numberOfClickedPair;
    private Button matchingButton;
    private int controlSelectOfWinner = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);
        allRoundsLl = findViewById(R.id.allRoundsLl);
        matchingButton = findViewById(R.id.matchingButton);
        matchingButton.setVisibility( View.INVISIBLE);
        context = this;

        cancelButton = findViewById(R.id.cancelButton);
        chooseButton = findViewById(R.id.chooseButton);
        firstCapoeiristaText = findViewById(R.id.firstCapoeiristaText);
        secondCapoeiristaText = findViewById(R.id.secondCapoeiristaText);
        infoText = findViewById(R.id.infoText);
        cancelButton.setVisibility(View.INVISIBLE);
        chooseButton.setVisibility(View.INVISIBLE);
        firstCapoeiristaText.setVisibility(View.INVISIBLE);
        secondCapoeiristaText.setVisibility(View.INVISIBLE);
        infoText.setVisibility(View.INVISIBLE);


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



        params = new LinearLayout.LayoutParams(400, LinearLayout.LayoutParams.WRAP_CONTENT);
        params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(50,20,0,0);
        params2.setMargins(50,20,0,50);

        // Turların string sayılarını arraylistte depola
        // Tur isimleri, altındaki textview'ların width ini belirliyo. O yüzden küçük genişlikteki tur başlığı, capoeirista ismini kısıtlıyo.
        // Aynı zamanda toplam tur sayısı da ekranın tamamını kaplayan linear layout un hücrelerinin genişliğini etkiliyor.
        roundsAsStrings = new ArrayList<>();
        for( int i = 0; i < numberOfRounds; i ++)
        {
            if( numberOfRounds - i > 2) {
                roundsAsStrings.add(i + 1 + ". Tur");
                roundsAsStrings.add("--------");
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
        roundsAsStrings.add("Kazanan");
        roundsAsStrings.add("-------");
        // capoeiristaların isimlerini textview a çevir
        final ArrayList<TextView> capoeiristasOfCurrentRound = new ArrayList<TextView>();
        final ArrayList<Capoeirista> tempCapoeiristas = new ArrayList<>();
        final ArrayList<Capoeirista> loserCapoeiristas = new ArrayList<>();
        for( int i = 0; i < capoeiristas.size(); i++) {
            tempCapoeiristas.add(new Capoeirista(capoeiristas.get(i).getName()));
        }
        for( int i = 0; i < capoeiristas.size(); i ++)
        {
            TextView view = new TextView( context);
            view.setText(i + 1 + "- " + capoeiristas.get(i).getName());
            capoeiristasOfCurrentRound.add( view);
        }
        // Tek sayılı capoeirista için
        if( capoeiristas.size() % 2 == 1)
        {
            TextView view = new TextView( context);
            view.setText( capoeiristas.size() + 1 + "- ");
            capoeiristasOfCurrentRound.add( view);
        }
        // Capoeiristaları ikili olarak eşleştirip arraylistte depola
        pairLayout = new ArrayList<>();
        for( int i = 0; i < capoeiristasOfCurrentRound.size() - 1; i += 2)
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
            final int index = i;
            temp.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // Bug var, birden çok tıklanırsa???
                    //if( event.getAction() == MotionEvent.)
                    cancelButton.setVisibility(View.VISIBLE);
                    chooseButton.setVisibility(View.VISIBLE);
                    firstCapoeiristaText.setVisibility(View.VISIBLE);
                    secondCapoeiristaText.setVisibility(View.VISIBLE);
                    infoText.setVisibility(View.VISIBLE);
                    firstCapoeiristaText.setText( capoeiristasOfCurrentRound.get(index).getText());
                    secondCapoeiristaText.setText( capoeiristasOfCurrentRound.get( index + 1).getText());

                    firstCapoeiristaText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            capoeiristasOfCurrentRound.get( index).setBackgroundColor( GREEN);
                            capoeiristasOfCurrentRound.get( index + 1).setBackgroundColor( RED);
                            controlSelectOfWinner = 1;
                        }
                    });
                    secondCapoeiristaText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            capoeiristasOfCurrentRound.get( index).setBackgroundColor( RED);
                            capoeiristasOfCurrentRound.get( index + 1).setBackgroundColor( GREEN);
                            controlSelectOfWinner = 2;
                        }
                    });
                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cancelButton.setVisibility(View.INVISIBLE);
                            chooseButton.setVisibility(View.INVISIBLE);
                            firstCapoeiristaText.setVisibility(View.INVISIBLE);
                            secondCapoeiristaText.setVisibility(View.INVISIBLE);
                            infoText.setVisibility(View.INVISIBLE);
                        }
                    });
                    chooseButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cancelButton.setVisibility(View.INVISIBLE);
                            chooseButton.setVisibility(View.INVISIBLE);
                            firstCapoeiristaText.setVisibility(View.INVISIBLE);
                            secondCapoeiristaText.setVisibility(View.INVISIBLE);
                            infoText.setVisibility(View.INVISIBLE);
                            if( controlSelectOfWinner == 1)
                            {
                                capoeiristasOfCurrentRound.get( index).setBackgroundColor( GREEN);
                                capoeiristasOfCurrentRound.get( index + 1).setBackgroundColor( RED);
                                String firstCapo = tempCapoeiristas.get(index).getName();
                                String secondCapo = tempCapoeiristas.get(index + 1).getName();
                                if( doesExist( capoeiristas, secondCapo)) {
                                    removeCapoeirista(capoeiristas, secondCapo);
                                }
                                if( !doesExist( capoeiristas, firstCapo)) {
                                    addCapoeirista(capoeiristas, firstCapo);
                                }
                                if( !doesExist( loserCapoeiristas, secondCapo)) {
                                    addCapoeirista(loserCapoeiristas, secondCapo);
                                }
                            }
                            else
                            {
                                capoeiristasOfCurrentRound.get( index).setBackgroundColor( RED);
                                capoeiristasOfCurrentRound.get( index + 1).setBackgroundColor( GREEN);
                                String firstCapo = tempCapoeiristas.get(index).getName();
                                String secondCapo = tempCapoeiristas.get(index + 1).getName();
                                if( doesExist( capoeiristas, firstCapo))
                                    removeCapoeirista(capoeiristas, firstCapo);
                                if( !doesExist( capoeiristas, secondCapo))
                                    addCapoeirista( capoeiristas, secondCapo);
                                if( !doesExist( loserCapoeiristas, firstCapo)) {
                                    addCapoeirista(loserCapoeiristas, firstCapo);
                                }
                            }
                            numberOfClickedPair ++;
                            if( tempCapoeiristas.size() % 2 == 1 && numberOfClickedPair == pairLayout.size() - 1)
                            {
                                Collections.shuffle( loserCapoeiristas);
                                String name = loserCapoeiristas.get( 0).getName();
                                capoeiristasOfCurrentRound.get( capoeiristasOfCurrentRound.size() - 1).setText( tempCapoeiristas.size() + 1 + "- " + name);
                                tempCapoeiristas.add( new Capoeirista( name));
                            }
                            if( numberOfClickedPair == pairLayout.size())
                            {
                                matchingButton.setVisibility( View.VISIBLE);
                            }
                        }
                    });
                    return true;
                }
            });
            capoeiristasOfCurrentRound.get( index).setBackgroundColor( GRAY);
            capoeiristasOfCurrentRound.get( index + 1).setBackgroundColor( GRAY);
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
        allRoundsLl.addView( rounds.get( currentRound), params);
        class buttonClick implements View.OnClickListener
        {
            @Override
            public void onClick(View v)
            {
                matchingButton.setVisibility(View.INVISIBLE);
                currentRound ++;
                numberOfClickedPair = 0;
                titleNumber += 2;

                final ArrayList<TextView> capoeiristasOfCurrentRound = new ArrayList<TextView>();
                Collections.shuffle( capoeiristas);
                final ArrayList<Capoeirista> loserCapoeiristas = new ArrayList<>();
                final ArrayList<Capoeirista> tempCapoeiristas = new ArrayList<>();
                for( int i = 0; i < capoeiristas.size(); i++) {
                    tempCapoeiristas.add(new Capoeirista(capoeiristas.get(i).getName()));
                }
                for( int i = 0; i < capoeiristas.size(); i ++)
                {
                    TextView view = new TextView( context);
                    view.setText(i + 1 + "- " + capoeiristas.get(i).getName());
                    capoeiristasOfCurrentRound.add( view);
                }
                // Tek sayılı capoeirista için
                if( capoeiristas.size() % 2 == 1)
                {
                    TextView view = new TextView( context);
                    view.setText( capoeiristas.size() + 1 + "- ");
                    capoeiristasOfCurrentRound.add( view);
                }
                pairLayout = new ArrayList<>();
                for( int i = 0; i < capoeiristasOfCurrentRound.size() - 1; i += 2)
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
                    final int index = i;
                    temp.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            // Bug var, scroll yapıldığında basılmasın istiyorum ama alttaki satır çalışmıyo.
                            if( event.getAction() != MotionEvent.ACTION_SCROLL)
                            {
                                // Bug var, birden çok tıklanırsa???
                                cancelButton.setVisibility(View.VISIBLE);
                                chooseButton.setVisibility(View.VISIBLE);
                                firstCapoeiristaText.setVisibility(View.VISIBLE);
                                secondCapoeiristaText.setVisibility(View.VISIBLE);
                                infoText.setVisibility(View.VISIBLE);
                                firstCapoeiristaText.setText( capoeiristasOfCurrentRound.get(index).getText());
                                secondCapoeiristaText.setText( capoeiristasOfCurrentRound.get( index + 1).getText());

                                firstCapoeiristaText.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        capoeiristasOfCurrentRound.get( index).setBackgroundColor( GREEN);
                                        capoeiristasOfCurrentRound.get( index + 1).setBackgroundColor( RED);
                                        controlSelectOfWinner = 1;
                                    }
                                });
                                secondCapoeiristaText.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        capoeiristasOfCurrentRound.get( index).setBackgroundColor( RED);
                                        capoeiristasOfCurrentRound.get( index + 1).setBackgroundColor( GREEN);
                                        controlSelectOfWinner = 2;
                                    }
                                });
                                cancelButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        cancelButton.setVisibility(View.INVISIBLE);
                                        chooseButton.setVisibility(View.INVISIBLE);
                                        firstCapoeiristaText.setVisibility(View.INVISIBLE);
                                        secondCapoeiristaText.setVisibility(View.INVISIBLE);
                                        infoText.setVisibility(View.INVISIBLE);
                                    }
                                });
                                chooseButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        cancelButton.setVisibility(View.INVISIBLE);
                                        chooseButton.setVisibility(View.INVISIBLE);
                                        firstCapoeiristaText.setVisibility(View.INVISIBLE);
                                        secondCapoeiristaText.setVisibility(View.INVISIBLE);
                                        infoText.setVisibility(View.INVISIBLE);
                                        if( controlSelectOfWinner == 1)
                                        {
                                            capoeiristasOfCurrentRound.get( index).setBackgroundColor( GREEN);
                                            capoeiristasOfCurrentRound.get( index + 1).setBackgroundColor( RED);
                                            String firstCapo = tempCapoeiristas.get(index).getName();
                                            String secondCapo = tempCapoeiristas.get(index + 1).getName();
                                            if( doesExist( capoeiristas, secondCapo))
                                                removeCapoeirista(capoeiristas, secondCapo);
                                            if( !doesExist( capoeiristas, firstCapo))
                                                addCapoeirista( capoeiristas, firstCapo);
                                            if( !doesExist( loserCapoeiristas, secondCapo)) {
                                                addCapoeirista(loserCapoeiristas, secondCapo);
                                            }
                                        }
                                        else
                                        {
                                            capoeiristasOfCurrentRound.get( index).setBackgroundColor( RED);
                                            capoeiristasOfCurrentRound.get( index + 1).setBackgroundColor( GREEN);
                                            String firstCapo = tempCapoeiristas.get(index).getName();
                                            String secondCapo = tempCapoeiristas.get(index + 1).getName();
                                            if( doesExist( capoeiristas, firstCapo))
                                                removeCapoeirista(capoeiristas, firstCapo);
                                            if( !doesExist( capoeiristas, secondCapo))
                                                addCapoeirista( capoeiristas, secondCapo);
                                            if( !doesExist( loserCapoeiristas, firstCapo)) {
                                                addCapoeirista(loserCapoeiristas, firstCapo);
                                            }
                                        }
                                        numberOfClickedPair ++;
                                        if( tempCapoeiristas.size() % 2 == 1 && numberOfClickedPair == pairLayout.size() - 1)
                                        {
                                            Collections.shuffle( loserCapoeiristas);
                                            String name = loserCapoeiristas.get( 0).getName();
                                            capoeiristasOfCurrentRound.get( capoeiristasOfCurrentRound.size() - 1).setText( tempCapoeiristas.size() + 1 + "- " + name);
                                            tempCapoeiristas.add( new Capoeirista( name));
                                        }
                                        if( numberOfClickedPair == pairLayout.size())
                                        {
                                            System.out.println( currentRound + " and " + numberOfRounds);
                                            if( currentRound + 1 != numberOfRounds)
                                            {
                                                matchingButton.setVisibility( View.VISIBLE);
                                            }
                                            // KAZANANI GÖSTERMEK İÇİN...
                                            else
                                            {
                                                LinearLayout winnerLayout = new LinearLayout( context);
                                                winnerLayout.setOrientation(LinearLayout.VERTICAL);

                                                TextView view1 = new TextView( context);
                                                TextView view2 = new TextView( context);
                                                view1.setText(roundsAsStrings.get(roundsAsStrings.size() - 2));
                                                view2.setText(roundsAsStrings.get(roundsAsStrings.size() - 1));
                                                ViewGroup parent = (ViewGroup) view1.getParent();
                                                ViewGroup parent2 = (ViewGroup) view2.getParent();
                                                if( parent != null)
                                                    parent.removeView(view1);
                                                if( parent2 != null)
                                                    parent.removeView(view2);
                                                winnerLayout.addView( view1, params);
                                                winnerLayout.addView( view2, params);
                                                if( controlSelectOfWinner == 1)
                                                {
                                                    TextView view = new TextView(context);
                                                    view.setText( tempCapoeiristas.get( index).getName());

                                                    winnerLayout.addView(view, params);
                                                }
                                                else
                                                {
                                                    TextView view = new TextView(context);
                                                    view.setText( tempCapoeiristas.get( index + 1).getName());
                                                    winnerLayout.addView(view, params);
                                                }
                                                allRoundsLl.addView( winnerLayout, params);
                                            }
                                        }
                                    }
                                });
                                return true;
                            }
                            return false;
                        }
                    });
                    capoeiristasOfCurrentRound.get( index).setBackgroundColor( GRAY);
                    capoeiristasOfCurrentRound.get( index + 1).setBackgroundColor( GRAY);
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
                allRoundsLl.addView( rounds.get( currentRound), params);
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
    public void removeCapoeirista( ArrayList<Capoeirista> list, String name)
    {
        for( int i = 0; i < list.size(); i ++)
        {
            if( list.get(i).getName().equals( name))
                list.remove(i);
        }
    }
    public boolean doesExist( ArrayList<Capoeirista> list, String name)
    {
        for( int i = 0; i < list.size(); i ++)
        {
            if( list.get(i).getName().equals( name))
                return true;
        }
        return false;
    }
    public void addCapoeirista( ArrayList<Capoeirista> list, String name)
    {
        list.add( new Capoeirista(name));
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
