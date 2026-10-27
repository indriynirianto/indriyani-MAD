package com.example.midexam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "student_db";
    private static final String TB_NAME = "student";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_STUDENT_ID = "student_id";
    private static final String COL_AGE = "age";

    private static final String CREATE_TABLE = "CREATE TABLE " + TB_NAME + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NAME + " TEXT NOT NULL, "
            + COL_STUDENT_ID + " TEXT NOT NULL UNIQUE, "
            + COL_AGE + " INTEGER NOT NULL CHECK(age BETWEEN 17 AND 50));";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
        onCreate(db);
    }

    public void addStudent(StudentModel studentModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, studentModel.getName());
        contentValues.put(COL_STUDENT_ID, studentModel.getStudentId());
        contentValues.put(COL_AGE, studentModel.getAge());
        getWritableDatabase().insertOrThrow(TB_NAME, null, contentValues);
    }

    public void updateStudent(StudentModel studentModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, studentModel.getName());
        contentValues.put(COL_AGE, studentModel.getAge());
        getWritableDatabase().update(TB_NAME, contentValues, COL_ID + " = ?",
                new String[]{String.valueOf(studentModel.getId())});
    }

    public void deleteStudent(int id) {
        getWritableDatabase().delete(TB_NAME, COL_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public List<StudentModel> getStudentList() {
        String viewData = "SELECT id, name, student_id, age FROM " + TB_NAME;
        List<StudentModel> students = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery(viewData, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String studentId = cursor.getString(2);
                int age = cursor.getInt(3);
                students.add(new StudentModel(id, name, studentId, age));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return students;
    }
}
