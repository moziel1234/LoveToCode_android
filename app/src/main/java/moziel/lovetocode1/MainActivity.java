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
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView target = (TextView)findViewById(R.id.text_target);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.OnSharedPreferenceChangeListener  listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                if (key.equals("pref_num_target")) {
                    TextView target = (TextView)findViewById(R.id.text_target);
                    target.setText(prefs.getString("pref_num_target", "0"));
                }
            }
        };

        preferences.registerOnSharedPreferenceChangeListener(listener);


        target.setText(preferences.getString("pref_num_target", "0"));
        final EditText editText = (EditText) findViewById(R.id.editText);



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

    private void displayText(Editable text) {
        CharSequence temp = text.toString();
        View linearLayout =  findViewById(R.id.equation);
        if(((LinearLayout) linearLayout).getChildCount() > 0)
            ((LinearLayout) linearLayout).removeAllViews();

        for (int i=0; i< temp.length(); i++) {
            TextView valueTV = new TextView(this);
            valueTV.setText(temp.subSequence(i,i+1));
            valueTV.setPadding(2,1,2,1);
            valueTV.setTextSize(TypedValue.COMPLEX_UNIT_DIP,35);
            valueTV.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
            GradientDrawable border = new GradientDrawable();
            border.setColor(0xFFFFFFFF); //white background
            border.setStroke(1, 0xFF000000); //black border with full opacity
            valueTV.setBackgroundDrawable(border);
            ((LinearLayout) linearLayout).addView(valueTV);
            if (temp.length()>1 && i<temp.length()-1) {
                TextView actionTV = new TextView(this);
                actionTV.setText("?");
                actionTV.setPadding(2,1,2,1);
                actionTV.setTextSize(TypedValue.COMPLEX_UNIT_DIP,25);
                actionTV.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
                ((LinearLayout) linearLayout).addView(actionTV);
            }
        }
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
