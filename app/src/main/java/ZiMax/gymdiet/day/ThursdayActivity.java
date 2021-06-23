package ZiMax.gymdiet.day;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import ZiMax.gymdiet.GymActivity;
import ZiMax.gymdiet.R;

public class ThursdayActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thursday);

        Intent intent = getIntent();
        int diet = getIntent().getIntExtra("diet", 0);
        int gym = getIntent().getIntExtra("gym", 0);


        //проверяем как произошел запуск этого Activity, если = null (значит по кнопке "добавить")
        if (gym == GymActivity.GYM_CODE_FOR_ACTIVITY_DAY) {
            setTitle("Тренировки в Четверг");
            //invalidateOptionsMenu();
            //после этого метода сразу ззапускается nPrepareOptionsMenu(Menu menu)
            // который мы переопределяем
        } else {
            setTitle("Диета в Четверг");
        }
    }
}