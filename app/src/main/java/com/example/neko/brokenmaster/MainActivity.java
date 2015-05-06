package com.example.neko.brokenmaster;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;


public class MainActivity extends ActionBarActivity {

    private EditText firstlocked = null;
    private EditText secondlocked = null;
    private EditText resistant = null;

    private Button getCombos = null;

    private EditText first = null;
    private EditText second = null;
    private EditText third = null;

    private Button thirdFirst = null;
    private Button thirdSecond = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstlocked = (EditText) this.findViewById(R.id.editFirstLocked);
        secondlocked = (EditText) this.findViewById(R.id.editSecondLocked);
        resistant = (EditText) this.findViewById(R.id.editResistant);

        getCombos = (Button) this.findViewById(R.id.butFindCombos);

        first = (EditText) this.findViewById(R.id.editFirst);
        second = (EditText) this.findViewById(R.id.editSecond);
        third = (EditText) this.findViewById(R.id.editThird);

        thirdFirst = (Button) this.findViewById(R.id.buttonThirdFirst);
        thirdSecond = (Button) this.findViewById(R.id.buttonThirdSecond);

        getCombos.setOnClickListener(comboHandler);
        thirdFirst.setOnClickListener(firstThirdHandler);
        thirdSecond.setOnClickListener(secondThirdHandler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener comboHandler = new View.OnClickListener() {
        public void onClick(View v) {
            Integer f = Integer.valueOf(firstlocked.getText().toString());
            Double s = Double.valueOf(secondlocked.getText().toString());
            Double r = Double.valueOf(resistant.getText().toString());;
            Integer t;
            calcPossibilities(f, s, r, 0);
        }
    };

    View.OnClickListener firstThirdHandler = new View.OnClickListener() {
        public void onClick(View v) {
            Integer f = Integer.valueOf(firstlocked.getText().toString());
            Double s = Double.valueOf(secondlocked.getText().toString());
            Double r = Double.valueOf(resistant.getText().toString());;
            Integer t = Integer.valueOf(thirdFirst.getText().toString());
            calcPossibilities(f, s, r, 1);
        }
    };

    View.OnClickListener secondThirdHandler = new View.OnClickListener() {
        public void onClick(View v) {
            Integer f = Integer.valueOf(firstlocked.getText().toString());
            Double s = Double.valueOf(secondlocked.getText().toString());
            Double r = Double.valueOf(resistant.getText().toString());;
            Integer t = Integer.valueOf(thirdSecond.getText().toString());
            calcPossibilities(f, s, r, 2);
        }
    };

    //

    private void calcPossibilities(Integer l1, Double l2, Double r, Integer x){
        ArrayList<Double> s = new ArrayList<Double>();// = [];
        ArrayList<Double> t= new ArrayList<Double>();// = [];

        Double f = (Math.ceil(r) + 5) % 40;

        Double mod = f % 4;

        for (Integer i = 0; i < 4; i++)
        {
            if (((10 * i) + l1) % 4 == mod)
                t.add(Double.valueOf((10 * i) + l1));

            if (((10 * i) + l2) % 4 == mod)
                t.add((10 * i) + l2);
        }

        for (Integer i = 0; i < 10; i++)
        {
            Double tmp = ((mod + 2) % 4) + (4 * i);

            if (x==0 || ( (t.get(x-1)+2)%40 != tmp && (t.get(x-1)-2)%40 != tmp))
                s.add(tmp);
        }

        String listString = "";



        //System.out.println(listString);


        first.setText(listString += f.intValue());
        listString = "";
        for (Double st : s)
        {
            listString += st.intValue() + ", ";
        }
        second.setText(listString);
        listString = "";
        for (Double st : t)
        {
            listString += st.intValue() + ", ";
        }
        third.setText(listString);
        listString = "";

        if(x==0) {
            thirdFirst.setText(listString += t.get(0).intValue());
            listString = "";
            thirdSecond.setText(listString += t.get(1).intValue());
        }else{
            listString = "";
            third.setText(listString += t.get(x-1).intValue());
        }
    }
}