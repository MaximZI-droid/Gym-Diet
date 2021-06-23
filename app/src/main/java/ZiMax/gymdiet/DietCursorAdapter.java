package ZiMax.gymdiet;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import ZiMax.gymdiet.data.diet.DietContract.*;

public class DietCursorAdapter extends CursorAdapter {
    public DietCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.diet_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView foodTextView = view.findViewById(R.id.foodTextView);
        TextView prFatCarbKcalTextView = view.findViewById(R.id.prFatCarbKcalTextView);
        TextView noteDietTextView = view.findViewById(R.id.noteDietTextView);

        String food = cursor.getString(cursor.getColumnIndexOrThrow(DietEmpty.KEY_FOOD));
        String protein = cursor.getString(cursor.getColumnIndexOrThrow(DietEmpty.KEY_PROTEIN));
        String fat = cursor.getString(cursor.getColumnIndexOrThrow(DietEmpty.KEY_FAT));
        String carb = cursor.getString(cursor.getColumnIndexOrThrow(DietEmpty.KEY_CARBOHYDRATES));
        String kcal = cursor.getString(cursor.getColumnIndexOrThrow(DietEmpty.KEY_KCAL));
        String noteDiet = cursor.getString(cursor.getColumnIndexOrThrow(DietEmpty.KEY_NOTE));
        String allInfo = "Б: " + protein + ", Ж: " + fat + ", У: " + carb + ", ккал: " + kcal;

            foodTextView.setText(food);
            prFatCarbKcalTextView.setText(allInfo);
            noteDietTextView.setText(noteDiet);
    }
}
