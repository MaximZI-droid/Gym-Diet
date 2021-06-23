package ZiMax.gymdiet.data.gym;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ZiMax.gymdiet.data.gym.GymContract.*;

public class GymDataBaseOpenHelper extends SQLiteOpenHelper {

    public GymDataBaseOpenHelper(Context context) {
        super(context, GymContract.DATABASE_NAME, null, GymContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GYM_TABLE = "CREATE TABLE " + GymEmpty.TABLE_NAME + "("
                + GymEmpty.KEY_ID + " INTEGER PRIMARY KEY,"
                + GymEmpty.KEY_EXERCISE + "TEXT,"
                + GymEmpty.KEY_WEIGHT + "INTEGER,"
                + GymEmpty.KEY_APPROACHES + "INTEGER,"
                + GymEmpty.KEY_REPEAT + "INTEGER,"
                + GymEmpty.KEY_DAY_OF_WEEK + "TEXT,"
                + GymEmpty.KEY_NOTE + "TEXT" + ")";
        db.execSQL(CREATE_GYM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GymContract.DATABASE_NAME);
        onCreate(db);
    }
}
