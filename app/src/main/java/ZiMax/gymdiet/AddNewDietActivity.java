package ZiMax.gymdiet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import ZiMax.gymdiet.data.diet.DietContract.*;

public class AddNewDietActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Spinner dietSpinner;
    private ArrayAdapter spinnerAdapter;

    private EditText foodEditText;
    private EditText proteinEditText;
    private EditText fatEditText;
    private EditText carbEditText;
    private EditText kcalEditText;
    private EditText noteDietEditText;
    private int dayOfWeek;

    private static final int EDIT_DIET_LOADER = 1;
    private Uri currentDietUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_diet);

        foodEditText = findViewById(R.id.foodEditText);
        proteinEditText = findViewById(R.id.proteinEditText);
        fatEditText = findViewById(R.id.fatEditText);
        carbEditText = findViewById(R.id.carbEditText);
        kcalEditText = findViewById(R.id.kcalEditText);
        noteDietEditText = findViewById(R.id.dietNoteEditText);

        Intent intent = getIntent();
        currentDietUri = intent.getData();

        if (currentDietUri == null) {
            setTitle("Добавить новый продукт");
            invalidateOptionsMenu();
        } else {
            setTitle("Редактировать продукт");
            getSupportLoaderManager().initLoader(EDIT_DIET_LOADER, null, this);
        }

        dietSpinner = findViewById(R.id.spinner);
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_days_of_week, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dietSpinner.setAdapter(spinnerAdapter);

        dietSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedGender = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selectedGender)) {
                    if (selectedGender.equals("Понедельник")) {
                        dayOfWeek = DaysOfDietWeek.Понедельник.getNumberDayOfWeek();
                    } else if (selectedGender.equals("Вторник")) {
                        dayOfWeek = DaysOfDietWeek.Вторник.getNumberDayOfWeek();
                    } else if (selectedGender.equals("Среда")) {
                        dayOfWeek = DaysOfDietWeek.Среда.getNumberDayOfWeek();
                    } else if (selectedGender.equals("Четверг")) {
                        dayOfWeek = DaysOfDietWeek.Четверг.getNumberDayOfWeek();
                    } else if (selectedGender.equals("Пятница")) {
                        dayOfWeek = DaysOfDietWeek.Пятница.getNumberDayOfWeek();
                    } else if (selectedGender.equals("Суббота")) {
                        dayOfWeek = DaysOfDietWeek.Суббота.getNumberDayOfWeek();
                    } else if (selectedGender.equals("Воскресенье")) {
                        dayOfWeek = DaysOfDietWeek.Воскресенье.getNumberDayOfWeek();
                    } else {
                        dayOfWeek = DietEmpty.NULL_DAY_OF_WEEK;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dayOfWeek = DietEmpty.NULL_DAY_OF_WEEK;
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        if (currentDietUri == null) {
            MenuItem menuItem = menu.findItem(R.id.deleteDiet);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu_diet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveDiet:
                saveDiet();
                return true;
            case R.id.deleteDiet:
                showDeleteDietDialog();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveDiet() {

        String food = foodEditText.getText().toString().trim();
        String protein = proteinEditText.getText().toString().trim();
        String fat = fatEditText.getText().toString().trim();
        String carb = carbEditText.getText().toString().trim();
        String kcal = kcalEditText.getText().toString().trim();
        String noteDiet = noteDietEditText.getText().toString().trim();

        if (protein.matches("\\d+") && fat.matches("\\d+") && carb.matches("\\d+")
                && carb.matches("\\d+") && kcal.matches("\\d+")) {
        } else {
            Toast.makeText(this, "Необходимо ввести цифры Б/Ж/У/ккал", Toast.LENGTH_LONG).show();
        }

        if (TextUtils.isEmpty(food) || TextUtils.isEmpty(protein) || TextUtils.isEmpty(fat) ||
                TextUtils.isEmpty(kcal) || TextUtils.isEmpty(carb) || dayOfWeek == DietEmpty.NULL_DAY_OF_WEEK) {
            Toast.makeText(this, "Необходимо указать все параметры", Toast.LENGTH_LONG).show();
            return;
        }


        ContentValues dietContentValues = new ContentValues();
        dietContentValues.put(DietEmpty.KEY_FOOD, food);
        dietContentValues.put(DietEmpty.KEY_PROTEIN, protein);
        dietContentValues.put(DietEmpty.KEY_FAT, fat);
        dietContentValues.put(DietEmpty.KEY_CARBOHYDRATES, carb);
        dietContentValues.put(DietEmpty.KEY_KCAL, kcal);
        dietContentValues.put(DietEmpty.KEY_NOTE, noteDiet);
        dietContentValues.put(DietEmpty.KEY_DAY_OF_WEEK, dayOfWeek);

        if (currentDietUri == null) {
            ContentResolver contentResolver = getContentResolver();
            Uri uri = contentResolver.insert(DietEmpty.CONTENT_URI, dietContentValues);

            if (uri == null) {
                Toast.makeText(this, "Сохранение не удалось", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Сохранено", Toast.LENGTH_LONG).show();
            }
        } else {
            int rowsChanged = getContentResolver().update(currentDietUri, dietContentValues,
                    null, null);
            if (rowsChanged == 0) {
                Toast.makeText(this, "Сохранение не удалось", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Обновлено", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void deleteDiet() {
        if (currentDietUri != null) {
            int rowsDeleted = getContentResolver().delete(currentDietUri, null, null);
            if (rowsDeleted == 0) {
                Toast.makeText(this, "Удаление не произошло", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Удалено", Toast.LENGTH_LONG).show();
            }
        }
        finish();
    }

    private void showDeleteDietDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Вы действительно хотите удалить запись?");
        builder.setPositiveButton("Да",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteDiet();
                    }
                });
        builder.setNegativeButton("Нет",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {DietEmpty.KEY_ID, DietEmpty.KEY_FOOD, DietEmpty.KEY_PROTEIN, DietEmpty.KEY_FAT,
                DietEmpty.KEY_CARBOHYDRATES, DietEmpty.KEY_KCAL, DietEmpty.KEY_DAY_OF_WEEK, DietEmpty.KEY_NOTE};

        CursorLoader cursorLoader = new CursorLoader(this, currentDietUri, projection,
                null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.moveToFirst()) {
            int foodKey = data.getColumnIndex(DietEmpty.KEY_FOOD);
            int proteinKey = data.getColumnIndex(DietEmpty.KEY_PROTEIN);
            int fatKey = data.getColumnIndex(DietEmpty.KEY_FAT);
            int carbKey = data.getColumnIndex(DietEmpty.KEY_CARBOHYDRATES);
            int kcalKey = data.getColumnIndex(DietEmpty.KEY_KCAL);
            int dayKey = data.getColumnIndex(DietEmpty.KEY_DAY_OF_WEEK);
            int noteKey = data.getColumnIndex(DietEmpty.KEY_NOTE);

            String food = data.getString(foodKey);
            String protein = data.getString(proteinKey);
            String fat = data.getString(fatKey);
            String carb = data.getString(carbKey);
            String kcal = data.getString(kcalKey);
            int day = data.getInt(dayKey);
            String note = data.getString(noteKey);

            foodEditText.setText(food);
            proteinEditText.setText(protein);
            fatEditText.setText(fat);
            carbEditText.setText(carb);
            kcalEditText.setText(kcal);
            noteDietEditText.setText(note);

            switch (day) {

                case DietEmpty.MONDAY:
                    dietSpinner.setSelection(1);
                    break;
                case DietEmpty.TUESDAY:
                    dietSpinner.setSelection(2);
                    break;
                case DietEmpty.WEDNESDAY:
                    dietSpinner.setSelection(3);
                    break;
                case DietEmpty.THURSDAY:
                    dietSpinner.setSelection(4);
                    break;
                case DietEmpty.FRIDAY:
                    dietSpinner.setSelection(5);
                    break;
                case DietEmpty.SATURDAY:
                    dietSpinner.setSelection(6);
                    break;
                case DietEmpty.SUNDAY:
                    dietSpinner.setSelection(7);
                    break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}