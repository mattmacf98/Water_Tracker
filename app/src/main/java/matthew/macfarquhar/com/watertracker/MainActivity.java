package matthew.macfarquhar.com.watertracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    int num;

    public int getDate() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set up sharedpref to get key-value sets
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        //get date
        int oldDate = Integer.parseInt(sharedPreferences.getString("date_day","1"));
        int todayDate = getDate();
        if (oldDate != todayDate) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("ounces_of_water","0");
            editor.putString("date_day",todayDate+"");
            editor.commit();
        }

        //initiate num
        num = Integer.parseInt(sharedPreferences.getString("ounces_of_water","0"));


        //update progress text
        updateProgress();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ounces_of_water",num+"");
        editor.commit();
    }

    public void updateProgress() {

        if (num < 0) {
            num = 0;
        }

        TextView progressText = (TextView)findViewById(R.id.progress_text);
        progressText.setText("You drank "+num+"/64 oz of \nwater today!");

        //reset edittext
        EditText water_to_be_added = (EditText)findViewById(R.id.enter_water_text);
        water_to_be_added.setText("");
        water_to_be_added.setHint("Enter Water");

        //sets new image
        ImageView cup = (ImageView)findViewById(R.id.cup_img);
        if(num >= 64) {
            cup.setImageResource(R.drawable.full_cup);
        } else if (num >= 48) {
            cup.setImageResource(R.drawable.three_fourth_cup);
        } else if (num >= 32) {
            cup.setImageResource(R.drawable.half_cup);
        } else if (num >= 16) {
            cup.setImageResource(R.drawable.one_fourth_cup);
        } else {
            cup.setImageResource(R.drawable.empty_cup);
        }
    }

    public void setEightOz(View view) {
        EditText water_to_be_added = (EditText)findViewById(R.id.enter_water_text);
        water_to_be_added.setText("8");
    }

    public void setTwelveOz(View view) {
        EditText water_to_be_added = (EditText)findViewById(R.id.enter_water_text);
        water_to_be_added.setText("12");
    }

    public void setSeventeenOz(View view) {
        EditText water_to_be_added = (EditText)findViewById(R.id.enter_water_text);
        water_to_be_added.setText("17");
    }

    public void incrementWaterText(View view) {
        //gets the current string in the edittext and changes to an int
        //if none present treats it as 0
        int water_add;
        EditText water_to_be_added = (EditText)findViewById(R.id.enter_water_text);
        if (water_to_be_added.getText().toString().equals("")) {
            water_add = 0;
        } else {
            water_add = Integer.parseInt(water_to_be_added.getText().toString());
        }

        //adds water
        num += water_add;
        updateProgress();
    }

    public void decrementWaterText(View view) {
        //gets the current string in the edittext and changes to an int
        //if none present treats it as 0
        int water_subtract;
        EditText water_to_be_subtracted = (EditText)findViewById(R.id.enter_water_text);
        if (water_to_be_subtracted.getText().toString().equals("")) {
            water_subtract = 0;
        } else {
            water_subtract = Integer.parseInt(water_to_be_subtracted.getText().toString());
        }

        //subtracts water
        num -= water_subtract;
        updateProgress();

    }
}
