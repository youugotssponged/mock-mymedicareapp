package com.example.cw2_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBHelper {

    // Used to hold the currently logged in user ID
    public static long loggedInUserID;

    // Database fields
    public static final String KEY_ROWID = "id";
    public static final String KEY_USERNAME = "loginName";
    public static final String KEY_PASSWORD = "loginPassword";
    public static final String KEY_EMAIL = "loginEmail";
    public static final String KEY_PHONE = "loginPhoneNumber";
    public static final String KEY_GPNAME = "loginGPName";
    public static final String KEY_GPPHONE = "loginGPNumber";
    public static final String KEY_ACTUALNAME = "loginActualName";

    // Table information
    public static final String TAG = "MyDBHelper";
    public static final String DATABASE_NAME = "mymedicaredata";
    public static final String DATABASE_TABLE = "Users";
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_CREATE = "" +
            "create table Users (id integer primary key autoincrement," +
            "loginName text not null, loginPassword text not null," +
            "loginEmail text not null, loginPhoneNumber text not null," +
            "loginGPName text not null, loginGPNumber text not null, " +
            "loginActualName text not null)";

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    // Constructor
    public MyDBHelper(Context ctx){
        this.context = ctx; // assign context
        DBHelper = new DatabaseHelper(context); // create new instance
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            try{
                db.execSQL(DATABASE_CREATE);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            Log.w(TAG, "Upgrading database from version: " + oldVersion + " to " + newVersion + " which will destroy all old data!");
            db.execSQL("DROP TABLE IF EXISTS Users");
            onCreate(db);
        }
    }

    public MyDBHelper open() throws SQLException{
        db = DBHelper.getWritableDatabase();
        return this;
    }

    // Close database
    public void close(){
        DBHelper.close();
    }

    // Insert new record query
    public long insertUser(String username, String password, String email, String phone, String gpName, String gpNumber, String actualName){
        // Package values
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_USERNAME, username);
        initialValues.put(KEY_PASSWORD, password);
        initialValues.put(KEY_EMAIL, email);
        initialValues.put(KEY_PHONE, phone);
        initialValues.put(KEY_GPNAME, gpName);
        initialValues.put(KEY_GPPHONE, gpNumber);
        initialValues.put(KEY_ACTUALNAME, actualName);

        // Run insert query
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    // Update name query
    public long updateUserActualName(String name){
        ContentValues initialValue = new ContentValues();
        initialValue.put(KEY_ACTUALNAME, name);
        return db.update(DATABASE_TABLE, initialValue, KEY_ROWID + "=" + loggedInUserID , null);
    }

    // Update user phone number query
    public long updateUserNumber(String number){
        ContentValues initialValue = new ContentValues();
        initialValue.put(KEY_PHONE, number);
        return db.update(DATABASE_TABLE, initialValue, KEY_ROWID + "=" + loggedInUserID , null);
    }


    // Get all user's query
    public Cursor getAllUsers(){
        Cursor m_Cursor = db.query(DATABASE_TABLE, new String[]{
                KEY_ROWID,
                KEY_USERNAME,
                KEY_PASSWORD,
                KEY_EMAIL,
                KEY_PHONE,
                KEY_GPNAME,
                KEY_GPPHONE,
                KEY_ACTUALNAME
        }, null, null, null, null, null);

        if(m_Cursor != null)
            m_Cursor.moveToFirst();

        return m_Cursor;
    }

    // Get a single user, query
    public Cursor getUser(long rowId) throws SQLException{
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[]{
                        KEY_ROWID,
                        KEY_USERNAME,
                        KEY_PASSWORD,
                        KEY_EMAIL,
                        KEY_PHONE,
                        KEY_GPNAME,
                        KEY_GPPHONE,
                        KEY_ACTUALNAME
                }, KEY_ROWID + "=" + rowId, null, null, null, null, null);

        if(mCursor != null)
            mCursor.moveToFirst();

        return mCursor;
    }
}
