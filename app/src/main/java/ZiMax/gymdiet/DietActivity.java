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

public class DietActivity extends AppCompatActivity {

    public Intent intent;
    public FloatingActionButton floatingActionButton;

    public static final int DIET_CODE_FOR_ACTIVITY_DAY = 222;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);
        setTitle("Дневник питания");

        floatingActionButton = findViewById(R.id.dietFloatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DietActivity.this, AddNewDietActivity.class);
                startActivity(intent);
            }
        });

    }

    public void openMonday(View view) {
        intent = new Intent(DietActivity.this, MondayActivity.class);
        intent.putExtra("diet", DIET_CODE_FOR_ACTIVITY_DAY);
        startActivity(intent);
    }

    public void openTuesday(View view) {
        intent = new Intent(DietActivity.this, TuesdayActivity.class);
        intent.putExtra("diet", DIET_CODE_FOR_ACTIVITY_DAY);
        startActivity(intent);
    }

    public void openWednesday(View view) {
        intent = new Intent(DietActivity.this, WednesdayActivity.class);
        intent.putExtra("diet", DIET_CODE_FOR_ACTIVITY_DAY);
        startActivity(intent);
    }

    public void openThursday(View view) {
        intent = new Intent(DietActivity.this, ThursdayActivity.class);
        intent.putExtra("diet", DIET_CODE_FOR_ACTIVITY_DAY);
        startActivity(intent);
    }

    public void openFriday(View view) {
        intent = new Intent(DietActivity.this, FridayActivity.class);
        intent.putExtra("diet", DIET_CODE_FOR_ACTIVITY_DAY);
        startActivity(intent);
    }

    public void openSaturday(View view) {
        intent = new Intent(DietActivity.this, SaturdayActivity.class);
        intent.putExtra("diet", DIET_CODE_FOR_ACTIVITY_DAY);
        startActivity(intent);
    }

    public void openSunday(View view) {
        intent = new Intent(DietActivity.this, SundayActivity.class);
        intent.putExtra("diet", DIET_CODE_FOR_ACTIVITY_DAY);
        startActivity(intent);
    }
}