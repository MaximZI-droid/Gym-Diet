package ZiMax.gymdiet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ZiMax.gymdiet.day.FridayActivity;
import ZiMax.gymdiet.day.MondayActivity;
import ZiMax.gymdiet.day.SaturdayActivity;
import ZiMax.gymdiet.day.SundayActivity;
import ZiMax.gymdiet.day.ThursdayActivity;
import ZiMax.gymdiet.day.TuesdayActivity;
import ZiMax.gymdiet.day.WednesdayActivity;

public class GymActivity extends AppCompatActivity {

    public Intent intent;
    public FloatingActionButton floatingActionButton;

    public static final int GYM_CODE_FOR_ACTIVITY_DAY = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym);
        setTitle("Дневник тренировок");

        floatingActionButton = findViewById(R.id.gumFloatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GymActivity.this, AddNewGymActivity.class);
                startActivity(intent);
            }
        });
    }

    public void openMonday(View view) {
        intent = new Intent(GymActivity.this, MondayActivity.class);
        intent.putExtra("gym", GYM_CODE_FOR_ACTIVITY_DAY);
        startActivity(intent);
    }

    public void openTuesday(View view) {
        intent = new Intent(GymActivity.this, TuesdayActivity.class);
        intent.putExtra("gym", GYM_CODE_FOR_ACTIVITY_DAY);
        startActivity(intent);

    }

    public void openWednesday(View view) {
        intent = new Intent(GymActivity.this, WednesdayActivity.class);
        intent.putExtra("gym", GYM_CODE_FOR_ACTIVITY_DAY);
        startActivity(intent);
    }

    public void openThursday(View view) {
        intent = new Intent(GymActivity.this, ThursdayActivity.class);
        intent.putExtra("gym", GYM_CODE_FOR_ACTIVITY_DAY);
        startActivity(intent);
    }

    public void openFriday(View view) {
        intent = new Intent(GymActivity.this, FridayActivity.class);
        intent.putExtra("gym", GYM_CODE_FOR_ACTIVITY_DAY);
        startActivity(intent);
    }

    public void openSaturday(View view) {
        intent = new Intent(GymActivity.this, SaturdayActivity.class);
        intent.putExtra("gym", GYM_CODE_FOR_ACTIVITY_DAY);
        startActivity(intent);
    }

    public void openSunday(View view) {
        intent = new Intent(GymActivity.this, SundayActivity.class);
        intent.putExtra("gym", GYM_CODE_FOR_ACTIVITY_DAY);
        startActivity(intent);
    }
}