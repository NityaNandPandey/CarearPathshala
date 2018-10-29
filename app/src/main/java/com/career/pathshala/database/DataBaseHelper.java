package com.career.pathshala.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {


    private static DataBaseHelper mInstance = null;

    private static String DB_PATH = "/data/data/com.career.pathshala/databases/";

    //private static String DB_NAME="budget";
    private static String DB_NAME = "currentaffair_database";

    private Context myContext;
    private SQLiteDatabase myDataBase;

    private boolean dbExist = false;

    public static DataBaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DataBaseHelper(context.getApplicationContext(), DB_NAME, null, 1);
        }
        return mInstance;
    }

    public DataBaseHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    public void createDataBase() throws IOException {
        Log.e("...", "createDatabase");
        dbExist = checkDataBase();
        Log.e("..", "check database= " + dbExist);
        if (!dbExist) {
            Log.e("...", "db not exists");
            this.getReadableDatabase();
            copyDataBase();
        } else if (dbExist) {
            Log.e("...", "db  exists");
            this.getReadableDatabase();
            copyDataBase();
        }
    }

    private boolean checkDataBase() {
        Log.e("..", "check database");
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
            Log.e("....", "try checkDB= " + checkDB);

        } catch (SQLiteException e) {
            // database does't exist yet.
            Log.e("....", "catch checkDB= " + checkDB);
            e.printStackTrace();
        }

        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;

    }

    private void copyDataBase() throws IOException {
        // Open your local db as the input stream

        Log.e("...", "copydatabase");
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //		db.execSQL("Create table " + ConstantValues.TABLE1 + " ( "
        //				+ ConstantValues.TABLE1_COLUMN1 + " integer primary key autoincrement,"
        //				+ ConstantValues.TABLE1_COLUMN2 + " text );");

        //		db.execSQL("Create table " + ConstantsValues.TABLE_NAME2 + " ( "
        //				+ ConstantsValues.COLUMN3 + " integer primary key autoincrement,"
        //				+ ConstantsValues.COLUMN4 + " text,"
        //				+ ConstantsValues.COLUMN5 + " integer,"
        //				+ ConstantsValues.COLUMN6 + " integer );");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}

