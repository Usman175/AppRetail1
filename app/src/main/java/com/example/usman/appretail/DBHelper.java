package com.example.usman.appretail;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fahad Tanwir on 1/15/2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String TAG = DBHelper.class.getSimpleName();

    //DB info
    public static final String DB_NAME="myapp.db";
    public static final int DB_VERSION = 1;

    //User Details
    public static final String USER_TABLE="users";
    public static final String COLUMN_ID="id";
    public static final String COLUMN_USERNAME="username";
    public static final String COLUMN_PASS= "password";

    //Catagory Info
    public static final String CATAG_TABLE="catagory";
    public static final String COLUMN_CATAG_ID="catag_id";
    public static final String COLUMN_CATAG_NAME="catag_name";

    //Item Info
    public static final String ITEM_TABLE="items";
    public static final String COLUMN_ITEM_ID="item_id";
    public static final String COLUMN_ITEM_NAME="item_name";
    public static final String COLUMN_ITEM_PRICE="item_price";

    public static final String COLUMN_CATAG_REFER="catag_id";


    // User Create table String Query
    public static final String CREATE_TABLE_USERS="CREATE TABLE "+USER_TABLE+"("
            +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +COLUMN_USERNAME+" TEXT,"
            +COLUMN_PASS+" TEXT);";

    //Catagory Create table String Query
    public static final String CREATE_TABLE_CATAG="CREATE TABLE "+CATAG_TABLE+"("
            +COLUMN_CATAG_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +COLUMN_CATAG_NAME+");";

    //Item Create table String Query
    public static final String CREATE_TABLE_ITEM="CREATE TABLE "+ITEM_TABLE+"("
            +COLUMN_ITEM_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +COLUMN_ITEM_NAME+" TEXT,"
            +COLUMN_ITEM_PRICE+" INTEGER);";


    public DBHelper(Context context) {
        super(context,DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USERS);
        sqLiteDatabase.execSQL(CREATE_TABLE_CATAG);
        sqLiteDatabase.execSQL(CREATE_TABLE_ITEM);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+CATAG_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ITEM_TABLE);
        onCreate(sqLiteDatabase);
    }

    //User Login DB Details Part Start Here

    public void addUser(String uid,String pass){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(COLUMN_USERNAME,uid);
        values.put(COLUMN_PASS,pass);

        long id = db.insert(USER_TABLE,null,values);
        db.close();

        Log.d(TAG,"User Successfully added!"+id);

    }

    public boolean getUser(String uid ,String pass){

        String SELECT_QUERY="SELECT * FROM "+USER_TABLE+" WHERE " +
                COLUMN_USERNAME+"='"+uid+"'"+
                " AND "+
                COLUMN_PASS+"='"+pass+"';";

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_QUERY,null);


        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            return true;
        }

        cursor.close();
        db.close();
        return false;
    }
    //User Login DB Details Part Ends Here


    //Catagory Admin DB Details Part Start Here

    public void insertCatag(String etCatag0){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(COLUMN_CATAG_NAME,etCatag0);

        long id = db.insert(CATAG_TABLE,null,values);
        db.close();

    }

    //get all category starts here
    public Cursor getAllCategory(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }
    //end here

    // Insert Items to Database ( FOR ADMIN UI) --- Start Here
    public void insertItem(String etItemNm0,String etItemPrc1,int catRef){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(COLUMN_ITEM_NAME,etItemNm0);
        values.put(COLUMN_ITEM_PRICE,etItemPrc1);
        values.put(COLUMN_CATAG_REFER,catRef);
        long id = db.insert(ITEM_TABLE,null,values);
        db.close();

    }

    public List<String> getAllCatagorySpinner(){

        List<String> CatagSp= new ArrayList<String>();
        String QUERY_CATAG_SPINNER="SELECT "+COLUMN_CATAG_NAME+
                " FROM "+CATAG_TABLE+";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.rawQuery(QUERY_CATAG_SPINNER,null);

    //Looping the catagory names
        if(cursor.moveToFirst()){
            do {
                CatagSp.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return CatagSp;
    }

    public int catagoryRefer(String nm){

        int rv=0;


        String cref="SELECT "+COLUMN_CATAG_ID+" FROM "+CATAG_TABLE+" WHERE "+COLUMN_CATAG_NAME+"=\""+nm+"\";";
        SQLiteDatabase db=getReadableDatabase();

        Cursor cursor=db.rawQuery(cref,null);

        if (cursor.moveToFirst()) {
            do {
                rv =cursor.getInt(cursor.getColumnIndex(COLUMN_CATAG_ID));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return rv;




}}


