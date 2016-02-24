package com.siweiwang.webview1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//import android.widget.EditText;

//import java.security.Key;

/*
 * Created by Siwei on 2/22/2016.
 */
public class HotOrNot {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "person_name";
    public static final String KEY_HOTNESS = "HOTNESS";

    private static final String DATABASE_NAME = "HotOrNotdb";
    private static final String DATABASE_TABLE = "peopleTABLE";
    private static final int DATABASE_VERSION = 1;

    private DbHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public HotOrNot(Context c) {
        ourContext = c;
    }

    public HotOrNot open(){
        ourHelper = new DbHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();

      return this;

    }

    public HotOrNot close(){
         ourHelper.close();
        return this;
    }

    public long createEntry(String name, String hotness) {

        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, name);
        cv.put(KEY_HOTNESS, hotness);
        return ourDatabase.insert(DATABASE_TABLE, null, cv);
    }



    public String getData() {
        String[] columns = new String[]{KEY_ROWID, KEY_NAME, KEY_HOTNESS};
        Cursor c = ourDatabase.query(DATABASE_TABLE,columns,null,null, null,null,null);
        String result = " ";

        int iRow = c.getColumnIndex(KEY_ROWID);
        int iName = c.getColumnIndex(KEY_NAME);
        int iHot = c.getColumnIndex(KEY_HOTNESS);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            result = result + c.getString(iRow) + " " + c.getString(iName) + " " +
                    c.getString(iHot) + "\n";
        }


        return result;
    }







    public String getName(int l) {
        String[] columns = new String[]{KEY_ROWID, KEY_NAME, KEY_HOTNESS};
        Cursor c = ourDatabase.query(DATABASE_TABLE,columns,KEY_ROWID + "=" + l,null, null,null,null);

         if (c != null){
             c.moveToFirst();
             return c.getString(1);
         }
            return null;
    }

    public String getHotness(int l) {
        String[] columns = new String[]{KEY_ROWID, KEY_NAME, KEY_HOTNESS};
        Cursor c = ourDatabase.query(DATABASE_TABLE,columns,KEY_ROWID + "=" + l,null, null,null,null);


        if (c != null){
            c.moveToFirst();
            Log.i(c.getString(1), c.getString(2));
            return c.getString(2);

        }

            return null;
    }

    public void updateEntry(int iRow, String sqlname, String hotness) {

        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(KEY_NAME, sqlname);
        cvUpdate.put(KEY_HOTNESS, hotness);
        ourDatabase.update(DATABASE_TABLE, cvUpdate, KEY_ROWID + "=" + iRow, null);


    }

    public void deleteEntry(int lRow) {

        ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + lRow, null);
    }

    private static class DbHelper extends SQLiteOpenHelper {
        /**
         * Create a helper object to create, open, and/or manage a database.
         * This method always returns very quickly.  The database is not actually
         * created or opened until one of {@link #getWritableDatabase} or
         * {@link #getReadableDatabase} is called.
         *
         * @param context to use to open or create the database
         * @param name    of the database file, or null for an in-memory database
         * @param factory to use for creating cursor objects, or null for the default
         * @param version number of the database (starting at 1); if the database is older,
         *                {@link #onUpgrade} will be used to upgrade the database; if the database is
         *                newer, {@link #onDowngrade} will be used to downgrade the database
         */
        public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(
                "CREATE TABLE " + DATABASE_TABLE + " ("
                    + KEY_ROWID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_NAME + " TEXT NOT NULL, " +
                        KEY_HOTNESS + " TEXT NOT NULL);"

            );

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXIST " + DATABASE_TABLE);
            onCreate(db);
        }



    }


}
