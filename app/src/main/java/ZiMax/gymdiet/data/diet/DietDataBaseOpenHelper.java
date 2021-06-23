package ZiMax.gymdiet.data.diet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ZiMax.gymdiet.data.diet.DietContract.*;

public class DietDataBaseOpenHelper extends SQLiteOpenHelper {


    public DietDataBaseOpenHelper(Context context) {
        super(context, DietContract.DATABASE_NAME, null, DietContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DIET_TABLE = "CREATE TABLE " + DietEmpty.TABLE_NAME + "("
                + DietEmpty.KEY_ID + " INTEGER PRIMARY KEY,"
                + DietEmpty.KEY_FOOD + " TEXT,"
                + DietEmpty.KEY_PROTEIN + " INTEGER,"
                + DietEmpty.KEY_FAT + " INTEGER,"
                + DietEmpty.KEY_CARBOHYDRATES + " INTEGER,"
                + DietEmpty.KEY_KCAL + " INTEGER,"
                + DietEmpty.KEY_DAY_OF_WEEK + " TEXT,"
                + DietEmpty.KEY_NOTE + " TEXT" + ")";
        db.execSQL(CREATE_DIET_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DietContract.DATABASE_NAME);
        onCreate(db);
    }
}
