package ZiMax.gymdiet;

import androidx.annotation.NonNull;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import ZiMax.gymdiet.data.diet.DietContract;
import ZiMax.gymdiet.data.gym.GymContract.*;

public class AddNewGymActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public Spinner gymSpinner;
    public ArrayAdapter spinnerAdapter;

    private EditText exerciseEditText;
    private EditText approachesEditText;
    private EditText repeatEditText;
    private EditText weightEditText;
    private EditText gymNoteEditText;
    private int dayOfWeek;

    private static final int EDIT_GYM_LOADER = 2;
    private Uri currentGymUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_exercise);

        exerciseEditText = findViewById(R.id.exerciseEditText);
        approachesEditText = findViewById(R.id.approachesEditText);
        repeatEditText = findViewById(R.id.repeatEditText);
        weightEditText = findViewById(R.id.weightEditText);
        gymNoteEditText = findViewById(R.id.gymNoteEditText);

        Intent intent = getIntent();
        currentGymUri = intent.getData();

        if (currentGymUri == null) {
            setTitle("Добавить новое упражнение");
            invalidateOptionsMenu();
        } else {
            setTitle("Редактировать упражнение");
            getSupportLoaderManager().initLoader(EDIT_GYM_LOADER, null, this);
        }

        gymSpinner = findViewById(R.id.spinner);
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_days_of_week, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gymSpinner.setAdapter(spinnerAdapter);

        gymSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedGender = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selectedGender)) {
                    if (selectedGender.equals("Понедельник")) {
                        dayOfWeek = DaysOfGymWeek.Понедельник.getNumberDayOfWeek();
                    } else if (selectedGender.equals("Вторник")) {
                        dayOfWeek = DaysOfGymWeek.Вторник.getNumberDayOfWeek();
                    } else if (selectedGender.equals("Среда")) {
                        dayOfWeek = DaysOfGymWeek.Среда.getNumberDayOfWeek();
                    } else if (selectedGender.equals("Четверг")) {
                        dayOfWeek = DaysOfGymWeek.Четверг.getNumberDayOfWeek();
                    } else if (selectedGender.equals("Пятница")) {
                        dayOfWeek = DaysOfGymWeek.Пятница.getNumberDayOfWeek();
                    } else if (selectedGender.equals("Суббота")) {
                        dayOfWeek = DaysOfGymWeek.Суббота.getNumberDayOfWeek();
                    } else if (selectedGender.equals("Воскресенье")) {
                        dayOfWeek = DaysOfGymWeek.Воскресенье.getNumberDayOfWeek();
                    } else {
                        dayOfWeek = GymEmpty.NULL_DAY_OF_WEEK;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dayOfWeek = GymEmpty.NULL_DAY_OF_WEEK;
            }
        });
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (currentGymUri == null) {
            MenuItem menuItem = menu.findItem(R.id.deleteGym);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu_gym, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveGym:
                saveGym();
                return true;
            case R.id.deleteGym:
                showDeleteGymDialog();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveGym() {

        String exercise = exerciseEditText.getText().toString().trim();
        String approaches = approachesEditText.getText().toString().trim();
        String repeat = repeatEditText.getText().toString().trim();
        String weight = weightEditText.getText().toString().trim();
        String noteGym = gymNoteEditText.getText().toString().trim();

        if (approaches.matches("\\d+") && repeat.matches("\\d+") && weight.matches("\\d+")) {
        } else {
            Toast.makeText(this, "Необходимо ввести вес, подходы, повторения", Toast.LENGTH_LONG).show();
        }

        if (TextUtils.isEmpty(exercise) || TextUtils.isEmpty(weight) || TextUtils.isEmpty(approaches) ||
                TextUtils.isEmpty(repeat) || dayOfWeek == GymEmpty.NULL_DAY_OF_WEEK) {
            Toast.makeText(this, "Необходимо указать все параметры", Toast.LENGTH_LONG).show();
            return;
        }


        ContentValues gymContentValues = new ContentValues();
        gymContentValues.put(GymEmpty.KEY_EXERCISE, exercise);
        gymContentValues.put(GymEmpty.KEY_APPROACHES, approaches);
        gymContentValues.put(GymEmpty.KEY_REPEAT, repeat);
        gymContentValues.put(GymEmpty.KEY_WEIGHT, weight);
        gymContentValues.put(GymEmpty.KEY_NOTE, noteGym);
        gymContentValues.put(GymEmpty.KEY_DAY_OF_WEEK, dayOfWeek);

        if (currentGymUri == null) {
            ContentResolver contentResolver = getContentResolver();
            Uri uri = contentResolver.insert(GymEmpty.CONTENT_URI, gymContentValues);

            if (uri == null) {
                Toast.makeText(this, "Сохранение не удалось", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Сохранено", Toast.LENGTH_LONG).show();
            }
        } else {
            int rowsChanged = getContentResolver().update(currentGymUri, gymContentValues,
                    null, null);
            if (rowsChanged == 0) {
                Toast.makeText(this, "Сохранение не удалось", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Обновлено", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void deleteGym() {
        if (currentGymUri != null) {
            int rowsDeleted = getContentResolver().delete(currentGymUri, null, null);
            if (rowsDeleted == 0) {
                Toast.makeText(this, "Удаление не произошло", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Удалено", Toast.LENGTH_LONG).show();
            }
        }
        finish();
    }

    private void showDeleteGymDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Вы действительно хотите удалить запись?");
        builder.setPositiveButton("Да",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteGym();
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
        String[] projection = {GymEmpty.KEY_ID, GymEmpty.KEY_EXERCISE, GymEmpty.KEY_WEIGHT,
                GymEmpty.KEY_APPROACHES, GymEmpty.KEY_REPEAT, GymEmpty.KEY_NOTE, GymEmpty.KEY_DAY_OF_WEEK};

        CursorLoader cursorLoader = new CursorLoader(this, GymEmpty.CONTENT_URI, projection,
                null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.moveToFirst()) {

            int exerciseKey = data.getColumnIndex(GymEmpty.KEY_EXERCISE);
            int approachesKey = data.getColumnIndex(GymEmpty.KEY_APPROACHES);
            int repeatKey = data.getColumnIndex(GymEmpty.KEY_REPEAT);
            int weightKey = data.getColumnIndex(GymEmpty.KEY_WEIGHT);
            int dayKey = data.getColumnIndex(GymEmpty.KEY_DAY_OF_WEEK);
            int noteKey = data.getColumnIndex(GymEmpty.KEY_NOTE);

            String exercise = data.getString(exerciseKey);
            String approaches = data.getString(approachesKey);
            String repeat = data.getString(repeatKey);
            String weight = data.getString(weightKey);
            int day = data.getInt(dayKey);
            String note = data.getString(noteKey);

            exerciseEditText.setText(exercise);
            approachesEditText.setText(approaches);
            repeatEditText.setText(repeat);
            weightEditText.setText(weight);
            gymNoteEditText.setText(note);

            switch (day) {

                case GymEmpty.MONDAY:
                    gymSpinner.setSelection(1);
                    break;
                case GymEmpty.TUESDAY:
                    gymSpinner.setSelection(2);
                    break;
                case GymEmpty.WEDNESDAY:
                    gymSpinner.setSelection(3);
                    break;
                case GymEmpty.THURSDAY:
                    gymSpinner.setSelection(4);
                    break;
                case GymEmpty.FRIDAY:
                    gymSpinner.setSelection(5);
                    break;
                case GymEmpty.SATURDAY:
                    gymSpinner.setSelection(6);
                    break;
                case GymEmpty.SUNDAY:
                    gymSpinner.setSelection(7);
                    break;
            }
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}