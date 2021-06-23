package ZiMax.gymdiet.data.gym;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import ZiMax.gymdiet.data.gym.GymContract;
import ZiMax.gymdiet.data.gym.GymContract.*;
import ZiMax.gymdiet.data.gym.GymDataBaseOpenHelper;

public class GymContentProvider extends ContentProvider {

    GymDataBaseOpenHelper gymDataBaseOpenHelper;

    private static final UriMatcher gymUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int GYM = 1;
    private static final int GYM_ID = 2;

    static {
        gymUriMatcher.addURI(GymContract.AUTHORITY, GymContract.PATH_GYM, GYM);
        gymUriMatcher.addURI(GymContract.AUTHORITY, GymContract.PATH_GYM + "/#", GYM_ID);
    }

    @Override
    public boolean onCreate() {
        gymDataBaseOpenHelper = new GymDataBaseOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = gymDataBaseOpenHelper.getReadableDatabase();
        Cursor cursor;
        int match = gymUriMatcher.match(uri);

        switch (match) {
            case GYM:
                cursor = database.query(GymEmpty.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case GYM_ID:
                selection = GymEmpty.KEY_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(GymEmpty.TABLE_NAME, projection, selection,
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
        int match = gymUriMatcher.match(uri);

        switch (match) {
            case GYM:
                return GymEmpty.CONTENT_MULTIPLE_ITEMS;
            case GYM_ID:
                return GymEmpty.CONTENT_SINGLE_ITEM;
            default:
                throw new IllegalArgumentException("Unknown Uri " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase database = gymDataBaseOpenHelper.getWritableDatabase();
        int match = gymUriMatcher.match(uri);

        switch (match) {
            case GYM:
                long id = database.insert(GymEmpty.TABLE_NAME, null, values);
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
        SQLiteDatabase database = gymDataBaseOpenHelper.getWritableDatabase();
        int match = gymUriMatcher.match(uri);
        int lineDeleted;

        switch (match) {
            case GYM:
                lineDeleted = database.delete(GymEmpty.TABLE_NAME, selection, selectionArgs);
                break;
            case GYM_ID:
                selection = GymEmpty.KEY_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                lineDeleted = database.delete(GymEmpty.TABLE_NAME, selection, selectionArgs);
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
        SQLiteDatabase database = gymDataBaseOpenHelper.getWritableDatabase();
        int match = gymUriMatcher.match(uri);
        int lineUpdated;

        switch (match){
            case GYM:
                lineUpdated = database.update(GymEmpty.TABLE_NAME, values, selection, selectionArgs);
                break;
            case GYM_ID:
                selection = GymEmpty._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                lineUpdated = database.update(GymEmpty.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Can't update this Uri " + uri);
        }
        if (lineUpdated != 0)
            getContext().getContentResolver().notifyChange(uri,null);
        return lineUpdated;
    }
}