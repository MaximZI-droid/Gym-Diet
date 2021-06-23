package ZiMax.gymdiet.data.diet;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import ZiMax.gymdiet.data.diet.DietContract.*;

public class DietContentProvider extends ContentProvider {

    DietDataBaseOpenHelper dietDataBaseOpenHelper;

    private static final UriMatcher dietUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int DIET = 1;
    private static final int DIET_ID = 2;

    static {
        dietUriMatcher.addURI(DietContract.AUTHORITY, DietContract.PATH_DIET, DIET);
        dietUriMatcher.addURI(DietContract.AUTHORITY, DietContract.PATH_DIET + "/#", DIET_ID);
    }

    @Override
    public boolean onCreate() {
        dietDataBaseOpenHelper = new DietDataBaseOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = dietDataBaseOpenHelper.getReadableDatabase();
        Cursor cursor;
        int match = dietUriMatcher.match(uri);

        switch (match) {
            case DIET:
                cursor = database.query(DietEmpty.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case DIET_ID:
                selection = DietEmpty.KEY_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(DietEmpty.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Can't query Uri " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        int match = dietUriMatcher.match(uri);

        switch (match) {
            case DIET:
                return DietEmpty.CONTENT_MULTIPLE_ITEMS;
            case DIET_ID:
                return DietEmpty.CONTENT_SINGLE_ITEM;
            default:
                throw new IllegalArgumentException("Unknown Uri " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase database = dietDataBaseOpenHelper.getWritableDatabase();
        int match = dietUriMatcher.match(uri);

        switch (match) {
            case DIET:
                long id = database.insert(DietEmpty.TABLE_NAME, null, values);
                if (id == -1) {
                    Log.e("insertInDietBase", "Data insertion into the table failed, for " + id);
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            default:
                throw new IllegalArgumentException("Can't insert Uri " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = dietDataBaseOpenHelper.getWritableDatabase();
        int match = dietUriMatcher.match(uri);
        int lineDeleted;

        switch (match) {
            case DIET:
                lineDeleted = database.delete(DietEmpty.TABLE_NAME, selection, selectionArgs);
                break;
            case DIET_ID:
                selection = DietEmpty.KEY_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                lineDeleted = database.delete(DietEmpty.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Can't delete Uri " + uri);
        }
        if (lineDeleted != 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return lineDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase database = dietDataBaseOpenHelper.getWritableDatabase();
        int match = dietUriMatcher.match(uri);
        int lineUpdated;

        switch (match){
            case DIET:
                lineUpdated = database.update(DietEmpty.TABLE_NAME, values, selection, selectionArgs);
                break;
            case DIET_ID:
                selection = DietEmpty._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                lineUpdated = database.update(DietEmpty.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Can't update this Uri " + uri);
        }
        if (lineUpdated != 0)
            getContext().getContentResolver().notifyChange(uri,null);
        return lineUpdated;
    }
}