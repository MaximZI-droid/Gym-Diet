package ZiMax.gymdiet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


/*        CheckBox checkBox = findViewById(R.id.checkBox);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()){
                    checkBox.setText("выполнено");
                } else {
                    checkBox.setText("не выполнено");
                }
            }
        });*/

    }

    public void gymMenu(View view) {
        Intent intent = new Intent(MainActivity.this, GymActivity.class);
        startActivity(intent);
    }

    public void dietMenu(View view) {
        Intent intent = new Intent(MainActivity.this, DietActivity.class);
        startActivity(intent);
    }
}