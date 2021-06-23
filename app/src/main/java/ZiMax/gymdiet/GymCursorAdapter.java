package ZiMax.gymdiet;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import ZiMax.gymdiet.data.gym.GymContract;

public class GymCursorAdapter extends CursorAdapter {
    public GymCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.gym_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView exerciseTextView = view.findViewById(R.id.exerciseTextView);
        TextView WeAppRepTextView = view.findViewById(R.id.WeAppRepTextView);
        TextView noteTextView = view.findViewById(R.id.noteTextView);

        String exercise = cursor.getString(cursor.getColumnIndexOrThrow(GymContract.GymEmpty.KEY_EXERCISE));
        String weight = cursor.getString(cursor.getColumnIndexOrThrow(GymContract.GymEmpty.KEY_WEIGHT));
        String approaches = cursor.getString(cursor.getColumnIndexOrThrow(GymContract.GymEmpty.KEY_APPROACHES));
        String repeat = cursor.getString(cursor.getColumnIndexOrThrow(GymContract.GymEmpty.KEY_REPEAT));
        String noteGym = cursor.getString(cursor.getColumnIndexOrThrow(GymContract.GymEmpty.KEY_NOTE));
        String allInfo = weight + " "+ approaches + " " + repeat;

        exerciseTextView.setText(exercise);
        WeAppRepTextView.setText(allInfo);
        noteTextView.setText(noteGym);

    }
}
