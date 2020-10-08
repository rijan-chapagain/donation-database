package ict376.murdoch.edu.au.donationdatabase;

/**
 * Created by Hamid on 5/10/2017.
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import 	android.util.Pair;

public class DBHelper extends SQLiteOpenHelper {

    // The database name
    public static final String DATABASE_NAME = "DonationsDatabase.db";

    // The table "donators"
    public static final String DONATORS_TABLE_NAME    = "donators";
    public static final String DONATORS_COLUMN_ID     = "id";
    public static final String DONATORS_COLUMN_NAME   = "name";
    public static final String DONATORS_COLUMN_EMAIL  = "email";
    public static final String DONATORS_COLUMN_STREET = "street";
    public static final String DONATORS_COLUMN_CITY   = "place";
    public static final String DONATORS_COLUMN_PHONE  = "phone";

    // The table "amountsDonated"
    public static final String AMOUNTSDONATED_TABLE_NAME   = "amountsDonated";
    public static final String AMOUNTSDONATED_COLUMN_ID    = "id";
    public static final String AMOUNTSDONATED_COLUMN_DONATOR_ID    = "donator_id";
    public static final String AMOUNTSDONATED_COLUMN_DATE  = "date";
    public static final String AMOUNTSDONATED_COLUMN_AMOUNT_DONATED = "amount_donated";



    static int ver = 1;

    public DBHelper(Context context) {
        // Syntax: SQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
        // The third argument is used to allow returning subclasses of Cursor when calling query

        super(context, DATABASE_NAME , null, ver);
    }

    /**
     * onCreate callback is invoked when the database is actually opened,
     * for example by a call to getWritableDatabase().
     * onCreate is run only when the database file did not exist
     * @param db SQLiteDatabase to create
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Creating donators table
        db.execSQL(
                "create table " +  DONATORS_TABLE_NAME + "(" +
                        DONATORS_COLUMN_ID + " integer primary key, " +
                        DONATORS_COLUMN_NAME   + " text, " +
                        DONATORS_COLUMN_PHONE  + " text, " +
                        DONATORS_COLUMN_EMAIL  + " text, " +
                        DONATORS_COLUMN_STREET + " text, " +
                        DONATORS_COLUMN_CITY   + " text)"
        );

        // Creating amountsDonated table FK
        db.execSQL(
                "create table " +  AMOUNTSDONATED_TABLE_NAME + "(" +
                        AMOUNTSDONATED_COLUMN_ID + " integer primary key, " +
                        AMOUNTSDONATED_COLUMN_DATE   + " date, " +
                        AMOUNTSDONATED_COLUMN_AMOUNT_DONATED  + " real," +
                        AMOUNTSDONATED_COLUMN_DONATOR_ID + " integer,  FOREIGN KEY (" +  AMOUNTSDONATED_COLUMN_DONATOR_ID + ") REFERENCES " +
                        DONATORS_TABLE_NAME + "(" +  DONATORS_COLUMN_ID + "))"
        );
    }

    /**
     * In case of upgrade, I will delete the current tables and create a new one
     * onUpgrade() is only called when the database file exists but the stored
     * version number is lower than requested in constructor.
     * The onUpgrade() should update the table schema to the requested version.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + DONATORS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + AMOUNTSDONATED_TABLE_NAME);
        onCreate(db);
    }

    /**
     * Insert a row (a new donator) into the donators table
     * @param name
     * @param phone
     * @param email
     * @param street
     * @param place
     * @return
     */
    public boolean insertDonator  (String name, String phone, String email, String street, String place)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        // Prepare the row to insert
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);

        // Insert the row
        db.insert("donators", null, contentValues);
        return true;
    }

    /**
     * Update the row of ID = id with these new values
     * @param id
     * @param name
     * @param phone
     * @param email
     * @param street
     * @param place
     * @return
     */
    public boolean updateDonator (Integer id, String name, String phone, String email, String street,String place) {

        SQLiteDatabase db = this.getWritableDatabase();

        // Prepare the new values
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);

        // run the update query
        db.update("donators", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    /**
     * Delete the row of ID = id
     * @param id
     * @return
     */
    public Integer deleteDonator (Integer id) {

        SQLiteDatabase db = this.getWritableDatabase();


        db.delete(AMOUNTSDONATED_TABLE_NAME,
                  AMOUNTSDONATED_COLUMN_DONATOR_ID + " = ?" ,  new String[] { Integer.toString(id) });


                // delete donator
        return db.delete(DONATORS_TABLE_NAME,
                         DONATORS_COLUMN_ID + " = ? ", new String[] { Integer.toString(id) });
    }

    /**
     * Get the list of all donators
     * @return
     */
    public ArrayList<Pair<Integer, String>> getAllContacts()
    {
        ArrayList<Pair<Integer, String>> array_list = new ArrayList<Pair<Integer, String>>();

       SQLiteDatabase db = this.getReadableDatabase();
       Cursor res =  db.rawQuery( "select * from donators", null );

        if (res.getCount() > 0) {
            res.moveToFirst();

            while (res.isAfterLast() == false) {
                array_list.add(new Pair(res.getInt(res.getColumnIndex(DONATORS_COLUMN_ID)), res.getString(res.getColumnIndex(DONATORS_COLUMN_NAME))));
                res.moveToNext();
            }

        }
        return array_list;
    }


    /**
     * Retrieving a row given a donator ID
     * Here, we will use the method rawQuery
     * @param id
     * @return
     */
    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from donators where id="+id+"", null );

        /*
        Cursor res =  db.rawQuery( "select " + DONATORS_TABLE_NAME + ".*, sum(" + DONATIONS_TABLE_NAME + "." + DONATIONS_COLUMN_AMOUNT_DONATED + ") " +
                                    "from " +  DONATORS_TABLE_NAME + ", " + DONATIONS_TABLE_NAME + " " +
                                    "where " + DONATORS_TABLE_NAME + "." + DONATORS_COLUMN_ID + "=" + id  + " and " + DONATIONS_TABLE_NAME +  "." + DONATIONS_COLUMN_CONTACT_ID + " = " + id, null );

        */

        // select donators.*, sum(donations.amount_donated) from donators, donations where donators.id = id  and  donations.donator_id = id


        return res;
    }

    /**
     * Count the number of rows in a table
     * @return
     */
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();

        return  (int) DatabaseUtils.queryNumEntries(db, DONATORS_TABLE_NAME);

    }


}