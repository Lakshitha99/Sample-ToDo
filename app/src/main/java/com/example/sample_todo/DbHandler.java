package com.example.sample_todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "todo";
    private static final String TABLE_NAME = "todo";

    //column names
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String STARTED = "started";
    private static final String FINISHED = "finished";


    public DbHandler(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String TABLE_CREATE_QUERY = "CREATE TABLE " + TABLE_NAME + " " +
                "("
                + ID + " INTEGER PRIMARY KEY " + "AUTOINCREMENT,"
                + TITLE + " TEXT,"
                + DESCRIPTION + " TEXT,"
                + STARTED + " TEXT,"
                + FINISHED + " TEXT" +
                ");";
        /*CREATE TABLE todo (id,title,desc,star,finished);*/

        db.execSQL(TABLE_CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;
        // Drop older table if existed
        db.execSQL(DROP_TABLE_QUERY);
        // create tables again
        onCreate(db);
    }

    //data insert/ add a single todo
    public void addToDo(ToDo toDo) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(TITLE, toDo.getTitle());
        contentValues.put(DESCRIPTION, toDo.getDescription());
        contentValues.put(STARTED, toDo.getStarted());
        contentValues.put(FINISHED, toDo.getFinished());

        //save to table
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        //close database
        sqLiteDatabase.close();
    }

    // Count todo table records
    public int countToDo() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT*FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();
    }

    //get all todos into a list
    public List<ToDo> getAllToDos() {

        List<ToDo> toDos = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT*FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                //create new todo object
                ToDo toDo = new ToDo();
                //
                toDo.setId(cursor.getInt(0));
                toDo.setTitle(cursor.getString(1));
                toDo.setDescription(cursor.getString(2));
                toDo.setStarted(cursor.getLong(3));
                toDo.setFinished(cursor.getLong(4));

                //toDos =[obj,objs,]
                toDos.add(toDo);

            } while (cursor.moveToNext());
        }
        return toDos;
    }

    // delete item
    public void deleteToDo(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, "id =?", new String[]{String.valueOf(id)});
        db.close();
    }

    // get a single todo
    public ToDo getSingleToDo(int id) {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(TABLE_NAME,new String[]{ID,TITLE,DESCRIPTION,STARTED, FINISHED},ID + "= ?",new String[]{String.valueOf(id)},null,null,null);
        ToDo toDo;
        if(cursor != null){
             cursor.moveToFirst();
           toDo = new ToDo(
                   cursor.getInt(0),
                   cursor.getString(1),
                   cursor.getString(2),
                   cursor.getLong(3),
                   cursor.getLong(4)

           );
           return toDo;
        }
        return null;
    }

    // update a single todo
    public int updateSingleToDo(ToDo toDo){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(TITLE, toDo.getTitle());
        contentValues.put(DESCRIPTION, toDo.getDescription());
        contentValues.put(STARTED, toDo.getStarted());
        contentValues.put(FINISHED, toDo.getFinished());

        int status = db.update(TABLE_NAME,contentValues,ID +" =?",new String[]{String.valueOf(toDo.getId())});
        db.close();
        return status;
    }
}


