package ZiMax.gymdiet.data.diet;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class DietContract {
    private DietContract() {
    }

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "diet";

    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "ZiMax.gymdiet";
    public static final String PATH_DIET = "diet";


    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);


    public static final class DietEmpty implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_DIET);

        public static final String TABLE_NAME = "diet";

        public static final String KEY_ID = BaseColumns._ID;
        public static final String KEY_FOOD = "food";
        public static final String KEY_PROTEIN = "protein";
        public static final String KEY_FAT = "fat";
        public static final String KEY_CARBOHYDRATES = "carbohydrates";
        public static final String KEY_KCAL = "kcal";
        public static final String KEY_DAY_OF_WEEK = "day";
        public static final String KEY_NOTE = "note";
        public static final int NULL_DAY_OF_WEEK = 0;

        public static final int MONDAY = 1;
        public static final int TUESDAY = 2;
        public static final int WEDNESDAY = 3;
        public static final int THURSDAY = 4;
        public static final int FRIDAY = 5;
        public static final int SATURDAY = 6;
        public static final int SUNDAY = 7;

        public static final String CONTENT_MULTIPLE_ITEMS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + PATH_DIET;
        public static final String CONTENT_SINGLE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + PATH_DIET;
    }

    public enum DaysOfDietWeek{
        Понедельник(1),Вторник(2),Среда(3),Четверг(4),
        Пятница(5),Суббота(6),Воскресенье(7);

       private int numberDayOfWeek;
        DaysOfDietWeek(int numberDayOfWeek) {
            this.numberDayOfWeek = numberDayOfWeek;
        }

        public int getNumberDayOfWeek() {
            return numberDayOfWeek;
        }
    }
}
