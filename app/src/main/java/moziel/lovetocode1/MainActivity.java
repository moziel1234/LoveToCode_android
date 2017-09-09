package moziel.lovetocode1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        char[] chars = {'+', '-', '*', '/'};
        int[] eqArray = {1,2,4,5,7};
        Log.d("mosh", Calc.permute(chars, eqArray , 1));
        */
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView target = (TextView)findViewById(R.id.text_target);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.OnSharedPreferenceChangeListener  listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                if (key.equals("pref_num_target")) {
                    TextView target = (TextView)findViewById(R.id.text_target);
                    target.setText(prefs.getString("pref_num_target", "120"));
                }
            }
        };

        preferences.registerOnSharedPreferenceChangeListener(listener);


        target.setText(preferences.getString("pref_num_target", "120"));
        final EditText editText = (EditText) findViewById(R.id.editText);

        View linearLayoutLeftToRight =  findViewById(R.id.equation);
        View linearLayoutRightToLeft =  findViewById(R.id.equation2);

        linearLayoutLeftToRight.getBackground().setAlpha(40);
        linearLayoutRightToLeft.getBackground().setAlpha(40);

        editText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Log.d("notescreen", "This is the text: " + editText.getText());
                displayText(editText.getText());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        Handler mHandler= new Handler();

        mHandler.post(
                new Runnable()
                {
                    public void run()
                    {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                        EditText editText = (EditText) findViewById(R.id.editText);
                        editText.setFocusableInTouchMode(true);
                        editText.requestFocus();

                    }
                }
        );
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void handleEqation(RelativeLayout relativeLayout, TextView textViewToSeeFirst, CharSequence temp, String res, boolean isLeft) {

        /*
        if(relativeLayout.getChildCount() > 0)
            relativeLayout.removeAllViews();
        */
        int l = relativeLayout.getChildCount();
        for (int i=l-1; i> 0; i--) {
            relativeLayout.removeViewAt(i);

        }

        int numberOfTextViews = 1;
        TextView[] textViewArray = new TextView[20];

        textViewArray[numberOfTextViews - 1] = textViewToSeeFirst;
        for (int i=0; i< temp.length(); i++) {
            RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams
                    (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            if (isLeft) {
                lparams.addRule(RelativeLayout.LEFT_OF, textViewArray[numberOfTextViews - 1].getId());
            } else {
                lparams.addRule(RelativeLayout.RIGHT_OF, textViewArray[numberOfTextViews - 1].getId());
            }
            lparams.addRule(RelativeLayout.ALIGN_TOP, textViewArray[numberOfTextViews - 1].getId());

            TextView valueTV = new TextView(MainActivity.this);
            valueTV.setText(temp.subSequence(i,i+1));
            valueTV.setPadding(2,1,2,1);
            valueTV.setTextSize(TypedValue.COMPLEX_UNIT_DIP,35);
            GradientDrawable border = new GradientDrawable();
            border.setColor(0xFFFFFFFF); //white background
            border.setStroke(1, 0xFF000000); //black border with full opacity
            valueTV.setBackgroundDrawable(border);
            valueTV.setId(numberOfTextViews);
            textViewArray[numberOfTextViews] = valueTV;
            relativeLayout.addView(textViewArray[numberOfTextViews], lparams);
            numberOfTextViews = numberOfTextViews + 1;

            lparams = new RelativeLayout.LayoutParams
                    (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            if (isLeft) {
                lparams.addRule(RelativeLayout.LEFT_OF, textViewArray[numberOfTextViews - 1].getId());
            } else {
                lparams.addRule(RelativeLayout.RIGHT_OF, textViewArray[numberOfTextViews - 1].getId());
            }
            lparams.addRule(RelativeLayout.ALIGN_TOP, textViewArray[numberOfTextViews - 1].getId());

            if (temp.length()>1 && i<temp.length()-1) {
                TextView actionTV = new TextView(MainActivity.this);
                if (res.equals("")) {
                    actionTV.setText("?");
                } else {
                    actionTV.setText(String.valueOf(res.charAt(i)));
                }
                actionTV.setPadding(2,1,2,1);
                actionTV.setTextSize(TypedValue.COMPLEX_UNIT_DIP,25);
                actionTV.setId(numberOfTextViews);
                textViewArray[numberOfTextViews] = actionTV;
                relativeLayout.addView(textViewArray[numberOfTextViews], lparams);
                numberOfTextViews = numberOfTextViews + 1;

            }

        }
    }

    private void displayText(Editable text) {
        CharSequence temp = text.toString();
        char[] chars = {'+', '-', '*', '/'};
        int[] eqArray = new int[temp.length()];
        int[] eqArray2 = new int[temp.length()];
        for(int i = 0 ; i < temp.length() ; i++){
            eqArray[i] = Integer.parseInt(temp.subSequence(i,i+1).toString());
            eqArray2[temp.length()-1-i] = Integer.parseInt(temp.subSequence(i,i+1).toString());
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String res = Calc.permute(chars, eqArray , Integer.parseInt(preferences.getString("pref_num_target", "120")));
        String res2 = Calc.permute(chars, eqArray2 , Integer.parseInt(preferences.getString("pref_num_target", "120")));

        View relativeLayoutLeftToRight =  findViewById(R.id.equation);
        View relativeLayoutRightToLeft =  findViewById(R.id.equation2);
        TextView textViewToSeeFirst = (TextView) findViewById(R.id.textViewEq1);
        TextView textViewToSeeFirst2 = (TextView) findViewById(R.id.textViewEq2);
        handleEqation((RelativeLayout) relativeLayoutLeftToRight, textViewToSeeFirst, temp, res, false);
        handleEqation((RelativeLayout) relativeLayoutRightToLeft, textViewToSeeFirst2, new StringBuilder(temp).reverse().toString() , res2, true);
        // handleEqation((LinearLayout) linearLayoutRightToLeft, new StringBuilder(temp).reverse().toString(), res2);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, MyPreferencesActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        Handler mHandler= new Handler();

        mHandler.post(
                new Runnable()
                {
                    public void run()
                    {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                        EditText editText = (EditText) findViewById(R.id.editText);
                        editText.setFocusableInTouchMode(true);
                        editText.requestFocus();
                    }
                }
        );
    }

}
