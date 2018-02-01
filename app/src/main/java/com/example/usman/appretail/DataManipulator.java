package com.example.usman.appretail;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adnan on 04/03/2016.
 */
public class DataManipulator {
    private static final String DATABASE_NAME = "testing.db";
    private static final int DATABASE_VERSION = 10;

    private static Context context;
    static SQLiteDatabase db;
    private SQLiteStatement
            InsUpdateLog,InsLastHittingDate,InsParcoRoute, InsCustomer,
            InsUser,InsImages, InsUserType, InsOrderTakenVisit,InsBusiness;

     //region *** insert init ***
     private static final String InsertImagesToUpload = "insert into uploadimages (user_id,shop_board,shop_front,shop_card,isupload,latitude_board,longitude_board,latitude_shop,longitude_shop,upload_id,images_status,capturing_date) values (?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String InsertCustomer = "insert into customer (customer_id,user_id,country_id,auto_mobile_id,customer_first_name,customer_last_name,customer_email,customer_password,customer_phone,customer_mobile,customer_address1,customer_address2,customer_city,customer_state,customer_website,createdon,modifiedon,admin_users_id_parcosf,visitor_id,customer_shop_name,customer_region_id,customer_description,shop_board,shop_board_lat,shop_board_long,shop_front,shop_front_lat,shop_front_long,shop_card,status,isApproved,customer_route_day) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String InsertUpdateLog = "insert into update_log (sync_id,sync_module,sync_operation,sync_service,sync_pk,createdon,is_update) values (?,?,?,?,?,?,?)";
    private static final String InsertLastHittingDate = "insert into update_log_last_hitting_date (date) values (?)";

    private static final String InsertBusiness = "insert into business (android_id,user_id,business_type,name,lat,lng,primary_phone,secondary_phone,email,area,address,status,shop_image,card_image,date_added,is_upload) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


    private static final String InsertUser = "insert into user (user_id,user_type_id,user_first_name,user_last_name,user_email_address,user_password,user_phone_number,user_mobile,user_status,createdon,modifiedon,admin_users_id_parcosf) values (?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String InsertUserType = "insert into user_type (user_type_id,user_type_title,user_type_permission) values (?,?,?)";

    private static final String InsertOrderTakenVisit = "insert into order_taken_visit(order_taken_visitor_id,order_taken_visit_customer_id,order_taken_visit_lat,order_taken_visit_long,order_taken_visit_shop_card,order_taken_visit_isorder,order_taken_visit_createdon,order_taken_visit_modifiedon,order_taken_visit_admin_users_id_parcosf,order_taken_visit_isupload,order_taken_android_id,is_un_approved_shop) values (?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String InsertRouteMap = "insert into parcoroute (p_user_id,latitude,longitude,isupload,route_created_on) values (?,?,?,?,?)";
    //endregion
    //==
    public DataManipulator(Context context) {
        DataManipulator.context = context;
        OpenHelper openHelper = new OpenHelper(DataManipulator.context);

        DataManipulator.db = openHelper.getWritableDatabase();

        //region *** init ***
        this.InsImages = DataManipulator.db.compileStatement(InsertImagesToUpload);

        this.InsCustomer = DataManipulator.db.compileStatement(InsertCustomer);

        this.InsUser = DataManipulator.db.compileStatement(InsertUser);
        this.InsUserType = DataManipulator.db.compileStatement(InsertUserType);

        this.InsUpdateLog =  DataManipulator.db.compileStatement(InsertUpdateLog);
        this.InsLastHittingDate =  DataManipulator.db.compileStatement(InsertLastHittingDate);
        this.InsParcoRoute =  DataManipulator.db.compileStatement(InsertRouteMap);

        this.InsOrderTakenVisit =  DataManipulator.db.compileStatement(InsertOrderTakenVisit);
        this.InsBusiness =  DataManipulator.db.compileStatement(InsertBusiness);

        //endregion
//==
    }

    private static class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            //region *** create tables ***


            String CREATE_CUSTOMER_TABLE = "CREATE TABLE customer (customer_id INTEGER, user_id TEXT, country_id TEXT, auto_mobile_id TEXT, customer_first_name TEXT, customer_last_name TEXT, customer_email TEXT, customer_password TEXT, customer_phone TEXT, customer_mobile TEXT, customer_address1 TEXT, customer_address2 TEXT, customer_city TEXT, customer_state TEXT, customer_website TEXT, createdon TEXT, modifiedon TEXT,admin_users_id_parcosf INTEGER,visitor_id INTEGER,customer_shop_name TEXT,customer_region_id INTEGER,customer_description TEXT,shop_board TEXT,shop_board_lat TEXT,shop_board_long TEXT,shop_front TEXT,shop_front_lat TEXT,shop_front_long TEXT,shop_card TEXT,status INTEGER,isApproved INTEGER,customer_route_day INTEGER);";
            db.execSQL(CREATE_CUSTOMER_TABLE);


            String CREATE_USER_TABLE = "CREATE TABLE user (user_id INTEGER, user_type_id TEXT, user_first_name TEXT, user_last_name TEXT, user_email_address TEXT, user_password TEXT, user_phone_number TEXT, user_mobile TEXT, user_status TEXT, createdon TEXT, modifiedon TEXT,admin_users_id_parcosf INTEGER);";
            db.execSQL(CREATE_USER_TABLE);

            String CREATE_USER_TYPE_TABLE = "CREATE TABLE user_type (user_type_id INTEGER, user_type_title TEXT, user_type_permission TEXT);";
            db.execSQL(CREATE_USER_TYPE_TABLE);

            String CREATE_BUSINESS_TABLE = "CREATE TABLE business (business_id INTEGER PRIMARY KEY AUTOINCREMENT,android_id TEXT,user_id INTEGER,business_type TEXT, name TEXT,lat TEXT,lng TEXT,primary_phone TEXT,secondary_phone TEXT,email TEXT,area TEXT,address TEXT,status TEXT,shop_image TEXT,card_image TEXT,date_added TEXT,is_upload INTEGER,server_business_id INTEGER);";
            db.execSQL(CREATE_BUSINESS_TABLE);


            String CREATE_UPDATELOG_TABLE = "CREATE TABLE update_log (sync_id INTEGER, sync_module TEXT,sync_operation TEXT, sync_service TEXT,sync_pk INTEGER,createdon TEXT,is_update INTEGER);";
            db.execSQL(CREATE_UPDATELOG_TABLE);

            String CREATE_LASTHITTING_TABLE = "CREATE TABLE update_log_last_hitting_date (date TEXT);";
            db.execSQL(CREATE_LASTHITTING_TABLE);

            String CREATE_ORDER_TAKEN_VISIT_TABLE = "CREATE TABLE order_taken_visit(order_taken_visitor_id INTEGER,order_taken_visit_customer_id INTEGER,order_taken_visit_lat TEXT,order_taken_visit_long TEXT,order_taken_visit_shop_card TEXT,order_taken_visit_isorder INTEGER,order_taken_visit_createdon TEXT,order_taken_visit_modifiedon TEXT,order_taken_visit_admin_users_id_parcosf INTEGER,order_taken_visit_isupload INTEGER,order_taken_android_id TEXT,is_un_approved_shop INTEGER);";
            db.execSQL(CREATE_ORDER_TAKEN_VISIT_TABLE);
            String CREATE_Route_Map_TABLE = "CREATE TABLE parcoroute (p_user_id INTEGER NOT NULL,latitude TEXT,longitude TEXT,isupload INTEGER NOT NULL,route_created_on Text);";
            db.execSQL(CREATE_Route_Map_TABLE);

            String CREATE_IMAGES_TABLE = "CREATE TABLE uploadimages (user_id INTEGER NOT NULL,shop_board TEXT,shop_front TEXT,shop_card TEXT,isupload INTEGER NOT NULL,latitude_board TEXT,longitude_board TEXT,latitude_shop TEXT,longitude_shop TEXT,upload_id TEXT,images_status TEXT,capturing_date TEXT);";
            db.execSQL(CREATE_IMAGES_TABLE);
                        //==
            //endregion
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            //region *** drop tables ***

            db.execSQL("DROP TABLE IF EXISTS business");
            db.execSQL("DROP TABLE IF EXISTS customer");

            db.execSQL("DROP TABLE IF EXISTS user");
            db.execSQL("DROP TABLE IF EXISTS user_type");

            db.execSQL("DROP TABLE IF EXISTS update_log");
            db.execSQL("DROP TABLE IF EXISTS update_log_last_hitting_date");

            db.execSQL("DROP TABLE IF EXISTS order_taken_visit");
            db.execSQL("DROP TABLE IF EXISTS parcoroute" );

            db.execSQL("DROP TABLE IF EXISTS uploadimages" );
            //==
            onCreate(db);
            //endregion
        }
    }

    //region *** Insert Function of tables ***

    public long InsertBusiness(String android_id , int user_id, String business_type , String name , String lat , String lng , String primary_phone , String secondary_phone , String email , String area , String address , String status , String shop_image , String card_image , String date_added, int is_upload ) {
        this.InsBusiness.bindString(1, String.valueOf(android_id));
        this.InsBusiness.bindString(2, String.valueOf(user_id));
        this.InsBusiness.bindString(3, business_type);
        this.InsBusiness.bindString(4, name);
        this.InsBusiness.bindString(5, String.valueOf(lat));
        this.InsBusiness.bindString(6, lng);
        this.InsBusiness.bindString(7, String.valueOf(primary_phone));
        this.InsBusiness.bindString(8, String.valueOf(secondary_phone));
        this.InsBusiness.bindString(9, String.valueOf(email));
        this.InsBusiness.bindString(10, String.valueOf(area));
        this.InsBusiness.bindString(11, String.valueOf(address));
        this.InsBusiness.bindString(12, String.valueOf(status));
        this.InsBusiness.bindString(13, String.valueOf(shop_image));
        this.InsBusiness.bindString(14, String.valueOf(card_image));
        this.InsBusiness.bindString(15, String.valueOf(date_added));
        this.InsBusiness.bindString(16, String.valueOf(is_upload));
        return this.InsBusiness.executeInsert();
    }

    public long InsertUpdateLog(int sync_id, String sync_module, String sync_operation, String sync_service, int sync_pk, String createdon, int is_update) {
        this.InsUpdateLog.bindString(1, String.valueOf(sync_id));
        this.InsUpdateLog.bindString(2, sync_module);
        this.InsUpdateLog.bindString(3, sync_operation);
        this.InsUpdateLog.bindString(4, sync_service);
        this.InsUpdateLog.bindString(5, String.valueOf(sync_pk));
        this.InsUpdateLog.bindString(6, createdon);
        this.InsUpdateLog.bindString(7, String.valueOf(is_update));
        return this.InsUpdateLog.executeInsert();
    }
    public long InsertLastHittingDate(String date) {
        this.InsLastHittingDate.bindString(1, date);

        return this.InsLastHittingDate.executeInsert();
    }

//this is for sync
    public long InsertCustomer(int customer_id, String user_id, String country_id, String auto_mobile_id, String customer_first_name, String customer_last_name, String customer_email, String customer_password, String customer_phone, String customer_mobile, String customer_address1, String customer_address2, String customer_city, String customer_state, String customer_website, String createdon, String modifiedon, int admin_users_id_parcosf, int visitor_id, String customer_shop_name, int customer_region_id, String customer_description, String shop_board, String shop_board_lat, String shop_board_long, String shop_front, String shop_front_lat, String shop_front_long, String shop_card, int status, int isApproved, int customer_route_day) {
        this.InsCustomer.bindString(1, String.valueOf(customer_id));
        this.InsCustomer.bindString(2, user_id);
        this.InsCustomer.bindString(3, country_id);
        this.InsCustomer.bindString(4, auto_mobile_id);
        this.InsCustomer.bindString(5, customer_first_name);
        this.InsCustomer.bindString(6, customer_last_name);
        this.InsCustomer.bindString(7, customer_email);
        this.InsCustomer.bindString(8, customer_password);
        this.InsCustomer.bindString(9, customer_phone);
        this.InsCustomer.bindString(10, customer_mobile);
        this.InsCustomer.bindString(11, customer_address1);
        this.InsCustomer.bindString(12, customer_address2);
        this.InsCustomer.bindString(13, customer_city);
        this.InsCustomer.bindString(14, customer_state);
        this.InsCustomer.bindString(15, customer_website);
        this.InsCustomer.bindString(16, createdon);
        this.InsCustomer.bindString(17, modifiedon);
        this.InsCustomer.bindString(18, String.valueOf(admin_users_id_parcosf));
        this.InsCustomer.bindString(19, String.valueOf(visitor_id));
        this.InsCustomer.bindString(20, customer_shop_name);
        this.InsCustomer.bindString(21, String.valueOf(customer_region_id));
        this.InsCustomer.bindString(22, customer_description);
        this.InsCustomer.bindString(23, shop_board);
        this.InsCustomer.bindString(24, shop_board_lat);
        this.InsCustomer.bindString(25, shop_board_long);
        this.InsCustomer.bindString(26, shop_front);
        this.InsCustomer.bindString(27, shop_front_lat);
        this.InsCustomer.bindString(28, shop_board_lat);
        this.InsCustomer.bindString(29, shop_card);
        this.InsCustomer.bindString(30, String.valueOf(status));
        this.InsCustomer.bindString(31, String.valueOf(isApproved));
        this.InsCustomer.bindString(32, String.valueOf(customer_route_day));
        return this.InsCustomer.executeInsert();

    }
//this is for services
    public long InsertCustomerforSyn(int customer_id, String user_id, String country_id, String auto_mobile_id, String customer_first_name, String customer_last_name, String customer_email, String customer_password, String customer_phone, String customer_mobile, String customer_address1, String customer_address2, String customer_city, String customer_state, String customer_website, String createdon, String modifiedon , int admin_users_id_parcosf, int visitor_id, String customer_shop_name, int customer_region_id, String customer_description, String shop_board, String shop_board_lat, String shop_board_long, String shop_front, String shop_front_lat, String shop_front_long, String shop_card, int status, int isApproved, int customer_route_day) {
    this.InsCustomer.bindString(1, String.valueOf(customer_id));
    this.InsCustomer.bindString(2, user_id);
    this.InsCustomer.bindString(3, country_id);
    this.InsCustomer.bindString(4, auto_mobile_id);
    this.InsCustomer.bindString(5, customer_first_name);
    this.InsCustomer.bindString(6, customer_last_name);
    this.InsCustomer.bindString(7, customer_email);
    this.InsCustomer.bindString(8, customer_password);
    this.InsCustomer.bindString(9, customer_phone);
    this.InsCustomer.bindString(10, customer_mobile);
    this.InsCustomer.bindString(11, customer_address1);
    this.InsCustomer.bindString(12, customer_address2);
    this.InsCustomer.bindString(13, customer_city);
    this.InsCustomer.bindString(14, customer_state);
    this.InsCustomer.bindString(15, customer_website);
    this.InsCustomer.bindString(16, createdon);
    this.InsCustomer.bindString(17, modifiedon);
        this.InsCustomer.bindString(18, String.valueOf(admin_users_id_parcosf));
        this.InsCustomer.bindString(19, String.valueOf(visitor_id));
        this.InsCustomer.bindString(20, customer_shop_name);
        this.InsCustomer.bindString(21, String.valueOf(customer_region_id));
        this.InsCustomer.bindString(22, customer_description);
        this.InsCustomer.bindString(23, shop_board);
        this.InsCustomer.bindString(24, shop_board_lat);
        this.InsCustomer.bindString(25, shop_board_long);
        this.InsCustomer.bindString(26, shop_front);
        this.InsCustomer.bindString(27, shop_front_lat);
        this.InsCustomer.bindString(28, shop_board_lat);
        this.InsCustomer.bindString(29, shop_card);
        this.InsCustomer.bindString(30, String.valueOf(status));
        this.InsCustomer.bindString(31, String.valueOf(isApproved));
        this.InsCustomer.bindString(32, String.valueOf(customer_route_day));
    return this.InsCustomer.executeInsert();

}



    public long InsertUser(int user_id, String user_type_id, String user_first_name, String user_last_name, String user_email_address, String user_password, String user_phone_number, String user_mobile, String user_status, String createdon, String modifiedon, int admin_users_id_parcosf) {
        this.InsUser.bindString(1, String.valueOf(user_id));
        this.InsUser.bindString(2, user_type_id);
        this.InsUser.bindString(3, user_first_name);
        this.InsUser.bindString(4, user_last_name);
        this.InsUser.bindString(5, user_email_address);
        this.InsUser.bindString(6, user_password);
        this.InsUser.bindString(7, user_phone_number);
        this.InsUser.bindString(8, user_mobile);
        this.InsUser.bindString(9, user_status);
        this.InsUser.bindString(10, createdon);
        this.InsUser.bindString(11, modifiedon);
        this.InsUser.bindString(12, String.valueOf(admin_users_id_parcosf));
        return this.InsUser.executeInsert();
    }
    public long InsertUserforsync(int user_id, String user_type_id, String user_first_name, String user_last_name, String user_email_address, String user_password, String user_phone_number, String user_mobile, String user_status, String createdon, String modifiedon) {
        this.InsUser.bindString(1, String.valueOf(user_id));
        this.InsUser.bindString(2, user_type_id);
        this.InsUser.bindString(3, user_first_name);
        this.InsUser.bindString(4, user_last_name);
        this.InsUser.bindString(5, user_email_address);
        this.InsUser.bindString(6, user_password);
        this.InsUser.bindString(7, user_phone_number);
        this.InsUser.bindString(8, user_mobile);
        this.InsUser.bindString(9, user_status);
        this.InsUser.bindString(10, createdon);
        this.InsUser.bindString(11, modifiedon);
        return this.InsUser.executeInsert();
    }

    public long InsertUserType(int user_type_id, String user_type_title, String user_type_permission) {
        this.InsUserType.bindString(1, String.valueOf(user_type_id));
        this.InsUserType.bindString(2, user_type_title);
        this.InsUserType.bindString(3, user_type_permission);
        return this.InsUserType.executeInsert();
    }




    public long InsertImagesToUpload(int p_id, String shop_board, String shop_front , String shop_card, int isupload , String latitude_board , String longitude_board, String latitude_shop, String longitude_shop, String upload_id, String created_on) {

    this.InsImages.bindString(1, String.valueOf(p_id));

    this.InsImages.bindString(2,shop_board);
    this.InsImages.bindString(3,shop_front);
    this.InsImages.bindString(4,shop_card);
    this.InsImages.bindString(5, String.valueOf(isupload));
    this.InsImages.bindString(6,latitude_board);
    this.InsImages.bindString(7,longitude_board);
    this.InsImages.bindString(8,latitude_shop);
    this.InsImages.bindString(9,longitude_shop);
    this.InsImages.bindString(10,upload_id);
    this.InsImages.bindString(11,"1");
    this.InsImages.bindString(12,created_on);

    return this.InsImages.executeInsert();
    }
    public List<String[]> SelectImagestoupload()
    {
        List<String[]> list = new ArrayList<String[]>();

        //Cursor cursor = db.rawQuery("SELECT * FROM question where company_id = '"+ CompanyId +"' and user_id = '"+ UserId +"' and status = '1'" , null);
        Cursor cursor = db.rawQuery("SELECT * FROM business where is_upload = '0' limit 1" , null);
        int x=0;
        if (cursor.moveToFirst()) {
            do {
                String[] b1=new String[]{cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(11),cursor.getString(12),cursor.getString(13),cursor.getString(14),cursor.getString(15)};
                list.add(b1);

                x=x+1;
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        cursor.close();

        return list;
    }

    public int Parco_images_Upload(int server_business_id,String server_upload_id)
    {
        ContentValues values=new ContentValues();
        values.put("is_upload", 1);
        values.put("server_business_id", server_business_id);
       // values.put("upload_id", server_upload_id);

        int id=db.update("business",values,"android_id= '" + String.valueOf(server_upload_id) + "'",null);

        return id;
    }
    public int Parco_images_Not_Upload(int user_id)
    {
        ContentValues values=new ContentValues();
        values.put("images_status",0);

        int id=db.update("uploadimages",values,"upload_id= '" + String.valueOf(user_id) + "'",null);

        return id;
    }
    public List<String[]> SelectUser()
    {
        List<String[]> list = new ArrayList<String[]>();

        Cursor cursor = db.rawQuery("SELECT * FROM user" , null);

        int x=0;
        if (cursor.moveToFirst()) {
            do {
                String[] b1=new String[]{cursor.getString(0)};
                list.add(b1);

                x=x+1;
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        cursor.close();

        return list;
    }
    public void DeleteAllImages()
    {
        db.delete("uploadimages", null, null);
    }

    public long InsertOrderrTakenVisit(int order_taken_visitor_id, int order_taken_visit_customer_id, String order_taken_visit_lat, String order_taken_visit_long, String order_taken_visit_shop_card, int order_taken_visit_isorder, String order_taken_visit_createdon, String order_taken_visit_modifiedon, int order_taken_visit_admin_user_id_parco_sf, int isupload, String order_taken_android_id, int is_un_approved_shop) {
        this.InsOrderTakenVisit.bindString(1, String.valueOf(order_taken_visitor_id));
        this.InsOrderTakenVisit.bindString(2, String.valueOf(order_taken_visit_customer_id));
        this.InsOrderTakenVisit.bindString(3,order_taken_visit_lat);
        this.InsOrderTakenVisit.bindString(4,order_taken_visit_long);
        this.InsOrderTakenVisit.bindString(5,order_taken_visit_shop_card);
        this.InsOrderTakenVisit.bindString(6, String.valueOf(order_taken_visit_isorder));
        this.InsOrderTakenVisit.bindString(7,order_taken_visit_createdon);
        this.InsOrderTakenVisit.bindString(8,order_taken_visit_modifiedon);
        this.InsOrderTakenVisit.bindString(9, String.valueOf(order_taken_visit_admin_user_id_parco_sf));
        this.InsOrderTakenVisit.bindString(10, String.valueOf(isupload));
        this.InsOrderTakenVisit.bindString(11, String.valueOf(order_taken_android_id));
        this.InsOrderTakenVisit.bindString(12, String.valueOf(is_un_approved_shop));
        return this.InsOrderTakenVisit.executeInsert();
    }
    //=============Insert GPS cordinates===//
    public long InsertRouteMapGps(int user_id, String latitude, String longitude, int isupload, String date) {

        this.InsParcoRoute.bindString(1, String.valueOf(user_id));
        this.InsParcoRoute.bindString(2,latitude);
        this.InsParcoRoute.bindString(3,longitude);
        this.InsParcoRoute.bindString(4, String.valueOf(isupload));
        this.InsParcoRoute.bindString(5,date);
        return this.InsParcoRoute.executeInsert();
    }
    //==

    //endregion

    //region *** Delete Function of tables ***

    //Delete All Values From All Tables

    public void DeleteAllUpdateLog() {
        db.delete("update_log", null, null);
    }
    public void DeleteAllUpdateLog_LastHittingdate() {
        db.delete("update_log_last_hitting_date", null, null);
    }
    public void DeleteAllCustomer() {
        db.delete("customer", null, null);
    }

    public void DeleteUserById(int id) {
        db.delete("user where user_id = '" + id + "'", null, null);
    }
    public void DeleteCustomerById(int id) {
        db.delete("customer where customer_id = '" + id + "'", null, null);
    }

    public void DeleteAllUser() {
        db.delete("user", null, null);
    }

    public void DeleteAllUserType() {
        db.delete("user_type", null, null);
    }

    public void DeleteAllBusiness() {
        db.delete("business", null, null);
    }
    public void DeleteAllRoute() {
        int isupload=1;
        db.delete("parcoroute where isupload = '"+isupload+"'", null, null);
    }

    public void DeleteOrderTakeVisit() {
        db.delete("order_taken_visit", null, null);
    }

    // Delete Row Based on ids for order_test



    //endregion

    //region *** login function ***
    // for login
    public String loginCheck(String name, String password) {
        String userId = "";
        int id=1;
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE user_email_address = '" + name + "' AND user_password = '" + password + "'", null);
        //  Cursor cursor = db.rawQuery("SELECT * FROM user WHERE user_email_address = '"+name+"'",null );
        //Cursor cursor = db.rawQuery("SELECT * FROM user",null );
        // String b1=cursor.getString(0);
        //int x=0;
        if (cursor.moveToFirst()) {
            do {
                //String[] b1=new String[]{cursor.getString(0),cursor.getString(1)};
                //String[] b1=new String[]{cursor.getString(0)};
                //lst.add(b1);
                userId = cursor.getString(0);

                //x=x+1;
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        cursor.close();

        return userId;
    }

    //tsq work
    public String[] login_details(String user_id) {
        String userId = "";
        int id=2;
        String[] b1 = new String[1];
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE user_id = '" + user_id + "'", null);
        //  Cursor cursor = db.rawQuery("SELECT * FROM user WHERE user_email_address = '"+name+"'",null );
        //Cursor cursor = db.rawQuery("SELECT * FROM user",null );
        // String b1=cursor.getString(0);
        //int x=0;
        if (cursor.moveToFirst()) {
            do {
                //String[] b1=new String[]{cursor.getString(0),cursor.getString(1)};
                 b1=new String[]{cursor.getString(0),cursor.getString(11)};
                //lst.add(b1);
                //userId = cursor.getString(0);

                //x=x+1;
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        cursor.close();

        return b1;
    }
    //endregion

    //region *** update functions ***


    //============Updating created order========//


    //========Update parcoroute table====//
    public int SetIsUpload(String route_created_on)
    {
        ContentValues values=new ContentValues();
        values.put("isupload",1);

        int id=db.update("parcoroute",values,"route_created_on= '" + String.valueOf(route_created_on) + "'",null);

        return id;
    }

    public int SetIsUploadzero(int user_id)
    {
        ContentValues values=new ContentValues();
        values.put("isupload",0);

        int id=db.update("parcoroute",values,"p_user_id= '" + String.valueOf(user_id) + "'",null);

        return id;
    }
    // for order_product upload

    //tsq
    //=====updating foc===//

    public int update_updateLog(int sync_id) {
        ContentValues values = new ContentValues();
        values.put("is_update", 1);

        int id = db.update("update_log", values, "sync_id= '" + String.valueOf(sync_id) + "'", null);

        return id;
    }

    //UPDATE ORDER TAKEN VISIT TABLE
    public int update_order_taken() {
        ContentValues values = new ContentValues();
        values.put("order_taken_visit_isupload", 1);
        int id = db.update("order_taken_visit", values, "order_taken_visit_isupload= 0 and order_taken_visit_isorder =0", null);

        return id;
    }
    public int update_order_taken_order( String order_taken_android_id) {
        ContentValues values = new ContentValues();
        values.put("order_taken_visit_isupload", 1);

        int id = db.update("order_taken_visit", values, "order_taken_visit_isupload= 0 and order_taken_visit_isorder =1 and order_taken_android_id = '"+order_taken_android_id+"'", null);

        return id;
    }


    //endregion


    // Getting Desired Contacts of Customer

    public static String paid_amount ="";
    public static String order_status ="";
    public List<String> getAllClient(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  name FROM business";

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
       // db.close();

        // returning lables
        return labels;
    }

    //getting shops by user id ===//tsq work
    public ArrayList<String[]> getAssignShops(int parco_sf_id) {
        Cursor cursor = db.rawQuery("SELECT customer_first_name,customer_shop_name,customer_mobile,customer_id FROM customer WHERE admin_users_id_parcosf='"+parco_sf_id+"'", null);
        ArrayList<String[]> product = new ArrayList<String[]>();
        if (cursor.moveToFirst()) {
            do {
//if(product.size()==0){
//    product.add("Select Product");//
//}
                String[] b=new String[]{cursor.getString(cursor.getColumnIndex("customer_first_name")),cursor.getString(cursor.getColumnIndex("customer_shop_name")),cursor.getString(cursor.getColumnIndex("customer_mobile")),cursor.getString(cursor.getColumnIndex("customer_id"))};
                product.add(b);

            } while (cursor.moveToNext());
        }
        return product;
    }
    //Getting shops ofToday route
    public ArrayList<String[]> GetCurrentDayShops(int parco_sf_id, int current_route_day) {
        Cursor cursor = db.rawQuery("SELECT customer_first_name,customer_shop_name,customer_mobile,customer_id FROM customer WHERE admin_users_id_parcosf='"+parco_sf_id+"' AND customer_route_day='"+current_route_day+"'", null);
        ArrayList<String[]> product = new ArrayList<String[]>();
        if (cursor.moveToFirst()) {
            do {
//if(product.size()==0){
//    product.add("Select Product");//
//}
                String[] b=new String[]{cursor.getString(cursor.getColumnIndex("customer_first_name")),cursor.getString(cursor.getColumnIndex("customer_shop_name")),cursor.getString(cursor.getColumnIndex("customer_mobile")),cursor.getString(cursor.getColumnIndex("customer_id"))};
                product.add(b);

            } while (cursor.moveToNext());
        }
        return product;
    }
    public ArrayList<String[]> getAssignShops_Search(int parco_sf_id, String shop_name) {
        Cursor cursor = db.rawQuery("SELECT customer_first_name,customer_shop_name,customer_mobile,customer_id FROM customer WHERE admin_users_id_parcosf='"+parco_sf_id+"' and customer_shop_name LIKE '%"+shop_name+"%' COLLATE NOCASE", null);
        ArrayList<String[]> product = new ArrayList<String[]>();
        if (cursor.moveToFirst()) {
            do {
//if(product.size()==0){
//    product.add("Select Product");//
//}
                String[] b=new String[]{cursor.getString(cursor.getColumnIndex("customer_first_name")),cursor.getString(cursor.getColumnIndex("customer_shop_name")),cursor.getString(cursor.getColumnIndex("customer_mobile")),cursor.getString(cursor.getColumnIndex("customer_id"))};
                product.add(b);

            } while (cursor.moveToNext());
        }
        return product;
    }
    //tsq work
    public ArrayList<String[]> GetCustomer_data(int parco_sf_id) {
        Cursor cursor = db.rawQuery("SELECT * FROM customer WHERE admin_users_id_parcosf='"+parco_sf_id+"'", null);
        ArrayList<String[]> product = new ArrayList<String[]>();
        if (cursor.moveToFirst()) {
            do {
//if(product.size()==0){
//    product.add("Select Product");//
//}
                String[] b=new String[]{cursor.getString(cursor.getColumnIndex("visitor_id")),cursor.getString(cursor.getColumnIndex("customer_id")),cursor.getString(cursor.getColumnIndex("shop_board_lat")),cursor.getString(cursor.getColumnIndex("shop_board_long")),cursor.getString(cursor.getColumnIndex("shop_card"))};
                product.add(b);

            } while (cursor.moveToNext());
        }
        return product;
    }
    public ArrayList<String[]> get_user_data() {
        Cursor cursor = db.rawQuery("SELECT * FROM user", null);
        ArrayList<String[]> product = new ArrayList<String[]>();
        if (cursor.moveToFirst()) {
            do {
//if(product.size()==0){
//    product.add("Select Product");//
//}
                String[] b=new String[]{cursor.getString(cursor.getColumnIndex("visitor_id"))};
                product.add(b);

            } while (cursor.moveToNext());
        }
        return product;
    }
    //Get customer Data by customer_id;
    public ArrayList<String[]> GetCustomer_databyCustomerId(int parco_sf_id, int customer_id) {
        Cursor cursor = db.rawQuery("SELECT * FROM customer WHERE admin_users_id_parcosf='"+parco_sf_id+"' AND customer_id='"+customer_id+"'", null);
        ArrayList<String[]> product = new ArrayList<String[]>();
        if (cursor.moveToFirst()) {
            do {
//if(product.size()==0){
//    product.add("Select Product");//
//}
                String[] b=new String[]{cursor.getString(cursor.getColumnIndex("visitor_id")),cursor.getString(cursor.getColumnIndex("customer_id")),cursor.getString(cursor.getColumnIndex("shop_board_lat")),cursor.getString(cursor.getColumnIndex("shop_board_long")),cursor.getString(cursor.getColumnIndex("shop_card"))};
                product.add(b);

            } while (cursor.moveToNext());
        }
        return product;
    }
    //Getting Customer data against customer_id
    public ArrayList<String[]> GetCustomer_dataForOrder(int customer_id) {
        Cursor cursor = db.rawQuery("SELECT * FROM customer WHERE customer_id='"+customer_id+"'", null);
        ArrayList<String[]> product = new ArrayList<String[]>();
        if (cursor.moveToFirst()) {
            do {
//if(product.size()==0){
//    product.add("Select Product");//
//}
                String[] b=new String[]{cursor.getString(cursor.getColumnIndex("customer_shop_name")),cursor.getString(cursor.getColumnIndex("customer_mobile")),cursor.getString(cursor.getColumnIndex("customer_address1"))};
                product.add(b);

            } while (cursor.moveToNext());
        }
        return product;
    }
    public ArrayList<String[]> GetOrderTakenVisitForApprovedShop() {
        int id=0;
        Cursor cursor = db.rawQuery("SELECT * FROM order_taken_visit WHERE order_taken_visit_isupload='"+id+"' and order_taken_visit_isorder = '"+id+"' and is_un_approved_shop = '"+id+"'", null);
        ArrayList<String[]> product = new ArrayList<String[]>();
        if (cursor.moveToFirst()) {
            do {
//if(product.size()==0){
//    product.add("Select Product");//
//}
                String[] b=new String[]{cursor.getString(cursor.getColumnIndex("order_taken_visitor_id")),cursor.getString(cursor.getColumnIndex("order_taken_visit_customer_id")),cursor.getString(cursor.getColumnIndex("order_taken_visit_lat")),cursor.getString(cursor.getColumnIndex("order_taken_visit_long")),cursor.getString(cursor.getColumnIndex("order_taken_visit_shop_card")),cursor.getString(cursor.getColumnIndex("order_taken_visit_isorder")),cursor.getString(cursor.getColumnIndex("order_taken_visit_createdon")),cursor.getString(cursor.getColumnIndex("order_taken_visit_modifiedon")),cursor.getString(cursor.getColumnIndex("order_taken_visit_admin_users_id_parcosf")),cursor.getString(cursor.getColumnIndex("order_taken_android_id"))};
                product.add(b);

            } while (cursor.moveToNext());
        }
        return product;
    }
    public ArrayList<String[]> GetOrderTakenVisit_Order(String order_taken_android_id) {
        int id=0;
        int id_=1;
        Cursor cursor = db.rawQuery("SELECT * FROM order_taken_visit WHERE order_taken_visit_isupload='"+id+"' and order_taken_visit_isorder = '"+id_+"' and order_taken_android_id = '"+order_taken_android_id+"'", null);
        ArrayList<String[]> product = new ArrayList<String[]>();
        if (cursor.moveToFirst()) {
            do {
//if(product.size()==0){
//    product.add("Select Product");//
//}
                String[] b=new String[]{cursor.getString(cursor.getColumnIndex("order_taken_visitor_id")),cursor.getString(cursor.getColumnIndex("order_taken_visit_customer_id")),cursor.getString(cursor.getColumnIndex("order_taken_visit_lat")),cursor.getString(cursor.getColumnIndex("order_taken_visit_long")),cursor.getString(cursor.getColumnIndex("order_taken_visit_shop_card")),cursor.getString(cursor.getColumnIndex("order_taken_visit_isorder")),cursor.getString(cursor.getColumnIndex("order_taken_visit_createdon")),cursor.getString(cursor.getColumnIndex("order_taken_visit_modifiedon")),cursor.getString(cursor.getColumnIndex("order_taken_visit_admin_users_id_parcosf")),cursor.getString(cursor.getColumnIndex("order_taken_android_id"))};
                product.add(b);

            } while (cursor.moveToNext());
        }
        return product;
    }

    //==tsq
    //==========Getting GPs Cordinate for upload====//
    public List<String[]> SelectRouteMapGps()
    {
        List<String[]> list = new ArrayList<String[]>();
        int id=0;
        Cursor cursor = db.rawQuery("SELECT * FROM parcoroute where isupload='"+id+"' limit 100" , null);

        int x=0;
        if (cursor.moveToFirst()) {
            do {
                String[] b1=new String[]{cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4)};
                list.add(b1);

                x=x+1;
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        cursor.close();

        return list;
    }
    public List<String[]> SelectAllUser()
    {
        List<String[]> list = new ArrayList<String[]>();
        int id=0;
        Cursor cursor = db.rawQuery("SELECT * FROM user" , null);

        int x=0;
        if (cursor.moveToFirst()) {
            do {
                String[] b1=new String[]{cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5)};
                list.add(b1);

                x=x+1;
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        cursor.close();

        return list;
    }

    public String getInsertLastHittingDate() {
        String date = "";
        Cursor cursor = db.rawQuery("SELECT date FROM update_log_last_hitting_date ", null);
        if (cursor.moveToFirst()) {
            do {

                date = cursor.getString(cursor.getColumnIndex("date"));

            } while (cursor.moveToNext());
        }
        return date;
    }

    public ArrayList<String> select_all_user() {
        Cursor cursor = db.rawQuery("SELECT * FROM user", null);
        ArrayList<String> order_status = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                order_status.add(cursor.getString(cursor.getColumnIndex("user_id")));
            } while (cursor.moveToNext());
        }
        return order_status;
    }

    public List<String[]> SelectUpdateLog() {
        List<String[]> list = new ArrayList<String[]>();

        Cursor cursor = db.rawQuery("SELECT * FROM update_log WHERE is_update = '0' limit 1", null);

        int x = 0;
        if (cursor.moveToFirst()) {
            do {

                //String[] b1=new String[]{cursor.getString(0),cursor.getString(1)};
                String[] b1 = new String[]{

                        cursor.getString(cursor.getColumnIndex("sync_id")),
                        cursor.getString(cursor.getColumnIndex("sync_module")),
                        cursor.getString(cursor.getColumnIndex("sync_operation")),
                        cursor.getString(cursor.getColumnIndex("sync_service")),
                        cursor.getString(cursor.getColumnIndex("sync_pk")),
                        cursor.getString(cursor.getColumnIndex("createdon")),
                        cursor.getString(cursor.getColumnIndex("is_update"))

                };
                list.add(b1);

                x = x + 1;
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed()) {
            //cursor.close();
        }


        return list;
    }

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        OpenHelper openHelper = new OpenHelper(DataManipulator.context);
        SQLiteDatabase sqlDB = openHelper.getWritableDatabase();
        String[] columns = new String[] { "mesage" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }


    }

}
