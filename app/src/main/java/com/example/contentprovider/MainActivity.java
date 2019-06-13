package com.example.contentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText text_name;
    TextView text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_name = (EditText)findViewById(R.id.textName);
        text_view = (TextView)findViewById(R.id.result);
    }

    /* This method helps to have the cursor already in the position where you enter the name
     * You do not have to click to display the cursor in that position.
     *
        public boolean onTouchEvent(MotionEvent event) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            return true;
        }
    *
    */

    // add students' detail
    public void onClickAddDetails(View view) {
        ContentValues values = new ContentValues();
        values.put(UsersProvider.name, text_name.getText().toString());
        Uri uri = getContentResolver().insert(UsersProvider.CONTENT_URI, values);
       // getContentResolver().insert(UsersProvider.CONTENT_URI, values);

        Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(getBaseContext(), "New Record Inserted", Toast.LENGTH_SHORT).show();
    }

    // Retrieve students' detail
    public void onClickShowDetails(View view) {
        Cursor cursor = getContentResolver().query(UsersProvider.CONTENT_URI, null, null, null, null);
        if(cursor.moveToFirst()) {
            StringBuilder stringBuilder = new StringBuilder();
            while (!cursor.isAfterLast()) {
                stringBuilder.append("\n" + cursor.getString(cursor.getColumnIndex("id"))
                    + ": " + cursor.getString(cursor.getColumnIndex("name")));
                cursor.moveToNext();
            }
            text_view.setText(stringBuilder);
        }
        else {
            text_view.setText("No Records Found");
        }
    }
}
