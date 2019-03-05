package com.example.tolga.capoeirabatizado;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

public class CreateTournamentActivity extends AppCompatActivity {

    private EditText editCapoeiristaName;
    private Button dismissBut;
    private Button addBut;
    private Button addCapoeiristaBut;
    private Button matchBut;
    private RelativeLayout kullanicilarRl;
    private Context context;
    private ArrayList<String> capoeiristas;
    private final String MESSAGE_KEY = "KEY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tournament);

        for( int i = 0; i < 20; i ++)
        {
            System.out.println(randomNumber( 10));
        }

        editCapoeiristaName = findViewById(R.id.capoeiristaNameText);
        dismissBut = findViewById(R.id.dismissButton);
        addBut = findViewById(R.id.addButton);
        addCapoeiristaBut = findViewById(R.id.addCapoeiristaButton);
        matchBut = findViewById(R.id.matchButton);
        kullanicilarRl = findViewById(R.id.kullancilarRl);
        capoeiristas = new ArrayList<>();


        editCapoeiristaName.setVisibility(View.INVISIBLE);
        dismissBut.setVisibility(View.INVISIBLE);
        addBut.setVisibility(View.INVISIBLE);


        addCapoeiristaBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCapoeiristaName.setVisibility(View.VISIBLE);
                dismissBut.setVisibility(View.VISIBLE);
                addBut.setVisibility(View.VISIBLE);
            }
        });
        context=this;
        dismissBut.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                editCapoeiristaName.setVisibility(View.INVISIBLE);
                dismissBut.setVisibility(View.INVISIBLE);
                addBut.setVisibility(View.INVISIBLE);
            }
        });
        TextView tv = new TextView( this);

        addBut.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                View lastView = kullanicilarRl.getChildAt( kullanicilarRl.getChildCount() - 1);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule( RelativeLayout.BELOW, lastView.getId());
                Log.d( "count: " + kullanicilarRl.getChildCount(), "652");

                String capoeiristaName = editCapoeiristaName.getText().toString();
                capoeiristas.add( capoeiristaName);
                TextView tv = new TextView( context);
                params.setMargins(50,30,0,0);
                tv.setId(kullanicilarRl.getChildCount());

                tv.setText(capoeiristaName);
                kullanicilarRl.addView( tv, params);

//                kullanicilarRl.addView( temp);
//                editCapoeiristaName.setText("");
//                dummyText.setText("");
//                System.out.println("printed: " + capoeiristaName);

            }
        });
        matchBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplication(), Tournament.class);
                intent.putExtra(MESSAGE_KEY, capoeiristas);
                startActivity(intent);
            }
        });
    }
    public int randomNumber(int size)
    {
        int number = (int)(Math.random() * size + 1);
        return number;
    }
}
