package com.neko.brokenmaster;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    View.OnClickListener instHandler = new View.OnClickListener() {
        public void onClick(View v) {
            String inst = new String();
            inst = "Find the \"First Locked Position\"\n\n" +
                    "Set the dial to 0.\n" +
                    "Apply full pressure upward on the shackle as if trying to open it.\n" +
                    "Rotate dial to the left (towards 10) hard until the dial gets locked.\n" +
                    "Notice how the dial is locked into a small groove. If you're directly between two digits such as 3 and 4, release the shackle and turn the dial left further until you're into the next locked groove. However, if the dial is between two half digits (e.g., 2.5 and 3.5), then enter the digit in-between (e.g., 3) into First Locked Position in the calculator below.\n" +
                    "\nFind the \"Second Locked Position\"\n\n" +
                    "Do all of the above again until you find the second digit below 11 that is between two half digits (e.g., 5.5 and 6.5), and enter the whole number (e.g., 7) into Second Locked Position in the calculator below.\n" +
                    "\nFind the \"Resistant Location\"\n\n" +
                    "Apply half as much pressure to the shackle so that you can turn the dial.\n" +
                    "Rotate dial to the right until you feel resistance. Rotate the dial to the right several more times to ensure you're feeling resistance at the same exact location.\n" +
                    "Enter this number into Resistant Location. If the resistance begins at a half number, such as 14.5, enter 14.5.\n" +
                    "\nInput the Numbers into The Calculator\n\n" +
                    "Make sure all three numbers are entered into the calculator at the top of this page, then click Find Combos. We now have 20 possible combos, but we'll reduce this further. Keep reading!\n" +
                    "\nFind the Right \"Third Digit\"\n\n" +
                    "Set the dial to the first possibility for the Third Digit.\n" +
                    "Apply full pressure upward on the shackle as if trying to open it.\n" +
                    "Turn the dial and note how much give there is.\n" +
                    "Loosen the shackle and set the dial to the second possibility for the Third Digit.\n" +
                    "Apply full pressure upward on the shackle as if trying to open it.\n" +
                    "If there is more give on the second digit, click the second digit in the calculator above. Otherwise, click the first digit.\n" +
                    "\nTest Out the 8 Combinations on Your Lock\n\n" +
                    "You are left with 8 possible combinations. Test them all until one works with the standard instructions below.";

            AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
            dlg.setMessage(inst).create().show();
        }
    };
    private EditText firstlocked = null;
    private EditText secondlocked = null;
    private EditText resistant = null;
    private Button getCombos = null;
    private EditText first = null;
    private EditText second = null;
    private EditText third = null;
    private Button thirdFirst = null;
    private Button thirdSecond = null;
    View.OnClickListener comboHandler = new View.OnClickListener() {
        public void onClick(View v) {
            Integer f = Integer.valueOf(firstlocked.getText().toString());
            Integer s = Integer.valueOf(secondlocked.getText().toString());
            Double r = Double.valueOf(resistant.getText().toString());
            Integer t;
            calcPossibilities(f, s, r, 0);
        }
    };
    View.OnClickListener firstThirdHandler = new View.OnClickListener() {
        public void onClick(View v) {
            Integer f = Integer.valueOf(firstlocked.getText().toString());
            Integer s = Integer.valueOf(secondlocked.getText().toString());
            Double r = Double.valueOf(resistant.getText().toString());
            Integer t = Integer.valueOf(thirdFirst.getText().toString());
            calcPossibilities(f, s, r, 1);
        }
    };
    View.OnClickListener secondThirdHandler = new View.OnClickListener() {
        public void onClick(View v) {
            Integer f = Integer.valueOf(firstlocked.getText().toString());
            Integer s = Integer.valueOf(secondlocked.getText().toString());
            Double r = Double.valueOf(resistant.getText().toString());
            Integer t = Integer.valueOf(thirdSecond.getText().toString());
            calcPossibilities(f, s, r, 2);
        }
    };
    private Button instructions = null;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("FIRST_THIRD", thirdFirst.getText().toString());
        savedInstanceState.putString("SECOND_THIRD", thirdSecond.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            thirdFirst.setText(savedInstanceState.getString("FIRST_THIRD"));
            thirdSecond.setText(savedInstanceState.getString("SECOND_THIRD"));
        }

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
        instructions = (Button) this.findViewById(R.id.buttonInst);

        getCombos.setOnClickListener(comboHandler);
        thirdFirst.setOnClickListener(firstThirdHandler);
        thirdSecond.setOnClickListener(secondThirdHandler);
        instructions.setOnClickListener(instHandler);

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

    //

    private void calcPossibilities(Integer l1, Integer l2, Double r, Integer x) {
        ArrayList<Double> s = new ArrayList<Double>();// = [];
        ArrayList<Double> t = new ArrayList<Double>();// = [];

        Double f = (Math.ceil(r) + 5.0) % 40.0;

        Double mod = f % 4.0;

        for (Double i = 0.0; i < 4; i++) {
            if (((10.0 * i) + l1) % 4.0 == mod)
                t.add(Double.valueOf((10 * i) + l1));

            if (((10.0 * i) + l2) % 4.0 == mod)
                t.add(Double.valueOf((10 * i) + l2));
        }

        for (Double i = 0.0; i < 10; i++) {
            Double tmp = ((mod + 2) % 4) + (4 * i);

            if (x == 0 || ((t.get(x - 1) + 2) % 40 != tmp && (t.get(x - 1) - 2) % 40 != tmp))
                s.add(tmp);
        }

        String listString = "";



        //System.out.println(listString);


        first.setText(listString += f.intValue());
        listString = "";
        for (Double st : s) {
            listString += st.intValue() + ", ";
        }
        second.setText(listString);
        listString = "";
        for (Double st : t) {
            listString += st.intValue() + ", ";
        }
        third.setText(listString);
        listString = "";

        if (t.size() != 0) {
            if (x == 0) {

                thirdFirst.setText(listString += t.get(0).intValue());
                listString = "";
                thirdSecond.setText(listString += t.get(1).intValue());

            } else {
                listString = "";
                third.setText(listString += t.get(x - 1).intValue());
            }
        }
    }
}