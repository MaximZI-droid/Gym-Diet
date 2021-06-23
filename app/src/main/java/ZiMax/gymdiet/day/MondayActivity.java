package ZiMax.gymdiet.day;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ListView;

import java.security.Provider;

import ZiMax.gymdiet.AddNewDietActivity;
import ZiMax.gymdiet.DietCursorAdapter;
import ZiMax.gymdiet.GymActivity;
import ZiMax.gymdiet.GymCursorAdapter;
import ZiMax.gymdiet.R;
import ZiMax.gymdiet.data.diet.DietContract;
import ZiMax.gymdiet.data.diet.DietContract.*;
import ZiMax.gymdiet.data.gym.GymContract.*;

public class MondayActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    ListView ListView;

    private static final int DIET_LOADER = 11;
    private static final int GYM_LOADER = 22;

    DietCursorAdapter dietCursorAdapter;
    GymCursorAdapter gymCursorAdapter;

    int diet;
    int gym;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monday);

        Intent intent = getIntent();
        diet = getIntent().getIntExtra("diet", 0);
        gym = getIntent().getIntExtra("gym", 0);


        if (gym == GymActivity.GYM_CODE_FOR_ACTIVITY_DAY) {
            setTitle("Тренировки в Понедельник");
            ListView = findViewById(R.id.dataListView);
            gymCursorAdapter = new GymCursorAdapter(this, null, false);
            ListView.setAdapter(gymCursorAdapter);
            getSupportLoaderManager().initLoader(GYM_LOADER, null, this);

            ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(MondayActivity.this, AddNewDietActivity.class);

                    Uri currentMember = ContentUris.withAppendedId(GymEmpty.CONTENT_URI, id);

                    intent.setData(currentMember);
                    startActivity(intent);
                }
            });

        } else {
            setTitle("Диета в Понедельник");

            ListView = findViewById(R.id.dataListView);
            dietCursorAdapter = new DietCursorAdapter(this, null, false);
            ListView.setAdapter(dietCursorAdapter);
            getSupportLoaderManager().initLoader(DIET_LOADER, null, this);

            ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(MondayActivity.this, AddNewDietActivity.class);

                    Uri currentMember = ContentUris.withAppendedId(DietEmpty.CONTENT_URI, id);

                    intent.setData(currentMember);
                    startActivity(intent);
                }
            });
        }
    }


    /*dietCursorAdapter.setFilterQueryProvider(new FilterQueryProvider() {
                @Override
                public Cursor runQuery(CharSequence constraint) {
                    String[] projection = {DietEmpty.KEY_ID, DietEmpty.KEY_FOOD, DietEmpty.KEY_PROTEIN,
                            DietEmpty.KEY_FAT, DietEmpty.KEY_CARBOHYDRATES, DietEmpty.KEY_KCAL, DietEmpty.KEY_NOTE};
                    String[] selectionArgs = {String.valueOf(DaysOfDietWeek.Понедельник)};
                    Cursor cursor = managedQuery(DietEmpty.CONTENT_URI, projection, DietEmpty.KEY_DAY_OF_WEEK, selectionArgs,  null);
                    return cursor;
                }
            });*/

 /*   private String filter;
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter : filter = "COLUMN_NAME = value";
            getLoaderManager().restartLoader(LOADER_ID, null, MainActivity.this);
            break;
            default: break; }
            return super.onOptionsItemSelected(item); }
    @Override
    public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader( MainActivity.this,
                // Parent activity context SomeContentProvider.CONTENT_URI,
                // Table to query projection,
                // Projection to return filter,
                // No selection clause null,
                // No selection arguments null
                // Default sort order ); }*/

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        CursorLoader cursorLoader;

        if (gym == GymActivity.GYM_CODE_FOR_ACTIVITY_DAY) {
            String[] projection = {GymEmpty.KEY_ID, GymEmpty.KEY_EXERCISE, GymEmpty.KEY_WEIGHT,
                    GymEmpty.KEY_APPROACHES, GymEmpty.KEY_REPEAT, GymEmpty.KEY_NOTE};

            cursorLoader = new CursorLoader(this, GymEmpty.CONTENT_URI, projection,
                    null, null, null);

        } else {

            String[] projection = {DietEmpty.KEY_ID, DietEmpty.KEY_FOOD, DietEmpty.KEY_PROTEIN,
                    DietEmpty.KEY_FAT, DietEmpty.KEY_CARBOHYDRATES, DietEmpty.KEY_KCAL, DietEmpty.KEY_NOTE};
            //String[] selectionArgs = {"1","2"}; - строка 1 и 2
            //String selection = DietEmpty.KEY_ID +"=?";


            //пишем null, тк у нас выводятся все строки и столбцы
            //выбираем запись по столбцу ID
            //projection - имена столбцов, кт хотим вывести {"LastName","Gender"}
            //selection - это отбор KEY_ID = BaseColumns._ID + =?
            //selectionArgs - аргументы отбора, возвращает методами последнее значение, в нашем случае номер строки
            // String selectArg[] = {DaysOfDietWeek.Понедельник.toString()};
            cursorLoader = new CursorLoader(this, DietEmpty.CONTENT_URI, projection,
                    null, null, null);

        }
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (gym == GymActivity.GYM_CODE_FOR_ACTIVITY_DAY) {
            gymCursorAdapter.swapCursor(data);
        } else {
            dietCursorAdapter.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (gym == GymActivity.GYM_CODE_FOR_ACTIVITY_DAY) {
            gymCursorAdapter.swapCursor(null);
        } else {
            dietCursorAdapter.swapCursor(null);
        }
    }

}