package com.example.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public final class DatabaseHelper {
	
	public static final String DEBUG_TAG = "MapsDemo";

	public static final int DB_VERSION = 1;
	public static final String DB_NAME = "database.db";

	public static final String ADDRESS_TABLE = "address_table";
	public static final String KEY_ADDRESS = "key_address";
	public static final String STREET = "street";
	public static final String CITY = "city";
	public static final String COUNTRY = "country";
	
	public static final String WASTE_AMOUNT_TABLE = "waste_amount";
	public static final String KEY_WASTE_AMOUNT = "key_waste_amount";
	public static final String WASTE_AMOUNT_VALUE = "waste_amount_value";
	
	public static final String KEY_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
	
	
	public static final String DB_CREATE_ADDRESS_TABLE =
			"CREATE TABLE ADDRESS" + "( " +
			KEY_ADDRESS + " " + KEY_OPTIONS + ", " +
			STREET + " " + CITY + ", " +
			COUNTRY + ", " + KEY_WASTE_AMOUNT + " INTEGER, FOREIGN KEY (" +
				    KEY_WASTE_AMOUNT + " ) REFERENCES " + WASTE_AMOUNT_TABLE
				     + " ( " + KEY_WASTE_AMOUNT + " )" +                
				     ");";
	
	public static final String DROP_ADDRESS_TABLE =
			"DROP TABLE IF EXISTS ADDRESS";
	
	public static final String DB_CREATE_WASTE_AMOUNT_TABLE =
			"CREATE TABLE" + WASTE_AMOUNT_TABLE + "( " +
			KEY_WASTE_AMOUNT + " " + KEY_OPTIONS + ", " +
			WASTE_AMOUNT_VALUE + ");";
	
	public static final String DROP_WASTE_AMOUNT_TABLE =
			"DROP TABLE IF EXISTS ADDRESS";
	
	private SQLiteDatabase db;
    private Context context;
    private DatabaseCreator dbCreator;
    
	private static class DatabaseCreator extends SQLiteOpenHelper {
	    public DatabaseCreator(Context context, String name,
	            CursorFactory factory, int version) {
	        super(context, name, factory, version);
	    }
	 
	    @Override
	    public void onCreate(SQLiteDatabase db) {
	        db.execSQL(DB_CREATE_ADDRESS_TABLE);
	 
	        Log.d(DEBUG_TAG, "Database creating...");
	        Log.d(DEBUG_TAG, "Table " + ADDRESS_TABLE + " ver." + DB_VERSION + " created");
	        
	        db.execSQL(DB_CREATE_WASTE_AMOUNT_TABLE);
	        Log.d(DEBUG_TAG, "Table " + WASTE_AMOUNT_TABLE + " ver." + DB_VERSION + " created");
	        
	    }
	 
	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        db.execSQL(DROP_ADDRESS_TABLE);
	        db.execSQL(DROP_WASTE_AMOUNT_TABLE);
	 
	        Log.d(DEBUG_TAG, "Database updating...");
	        Log.d(DEBUG_TAG, "All data is lost.");
	 
	        onCreate(db);
	    }
	}
	public DatabaseHelper(Context context) {
        this.context = context;
    }
 
    public DatabaseHelper open(){
        dbCreator = new DatabaseCreator(context, DB_NAME, null, DB_VERSION);
        try {
            db = dbCreator.getWritableDatabase();
        } catch (SQLException e) {
            db = dbCreator.getReadableDatabase();
        }
        return this;
    }
    
    public void close() {
            dbCreator.close();
    }
    
    public long insertAddress (Address address){
    	ContentValues addressValue = new ContentValues();
    	addressValue.put(STREET, address.getStreet());
    	addressValue.put(CITY, address.getCity());
    	addressValue.put(COUNTRY, address.getCountry());
    	addressValue.put(KEY_WASTE_AMOUNT, address.getWasteId());
    	
    	return db.insert(ADDRESS_TABLE, null, addressValue);
    }
    
    public long insertWasteAmount (WasteAmount wasteAmount){
    	ContentValues wasteValue = new ContentValues();
    	wasteValue.put(WASTE_AMOUNT_VALUE, wasteAmount.getValue());
    	
    	return db.insert(WASTE_AMOUNT_TABLE, null, wasteValue);
    }
    
    public List<Address> getAllAddresses(){
    	List<Address> addresses = new ArrayList<Address>();
    	String query = "SELECT * FROM " + ADDRESS_TABLE;
    	Cursor cursor = db.rawQuery(query, null);
    	
    	if(cursor.moveToFirst()){
    		do{
    			Address ad = new Address();
    			ad.setId(cursor.getInt(cursor.getColumnIndex(KEY_ADDRESS)));
    			ad.setStreet(cursor.getString(cursor.getColumnIndex(STREET)));
    			ad.setCity(cursor.getString(cursor.getColumnIndex(CITY)));
    			ad.setCountry(cursor.getString(cursor.getColumnIndex(COUNTRY)));
    			ad.setWasteId(cursor.getInt(cursor.getColumnIndex(KEY_WASTE_AMOUNT)));
    			addresses.add(ad);
    		}while(cursor.moveToNext());
    	}
    	return addresses;
    }
    
    public List<WasteAmount> getAllWasteAmount(){
    	List<WasteAmount> waste = new ArrayList<WasteAmount>();
    	String query = "SELECT * FROM " + WASTE_AMOUNT_TABLE;
    	Cursor cursor = db.rawQuery(query, null);
    	
    	if(cursor.moveToFirst()){
    		do{
    			WasteAmount wa = new WasteAmount();
    			wa.setId(cursor.getInt(cursor.getColumnIndex(KEY_WASTE_AMOUNT)));
    			wa.setValue(cursor.getInt(cursor.getColumnIndex(WASTE_AMOUNT_VALUE)));
    			waste.add(wa);
    		}while(cursor.moveToNext());
    	}
    	return waste;
    }
    public WasteAmount getWasteAmountByAddress(Address address){
    	WasteAmount wa = new WasteAmount();
    	String query = "SELECT * FROM " + WASTE_AMOUNT_TABLE + " WHERE KEY_WASTE_AMOUNT = "
    	+ address.getWasteId();
    	
    	Cursor cursor = db.rawQuery(query, null);
    	
    	cursor.moveToFirst();
    	wa.setId(cursor.getInt(cursor.getColumnIndex(KEY_WASTE_AMOUNT)));
    	wa.setValue(cursor.getInt(cursor.getColumnIndex(WASTE_AMOUNT_VALUE)));
    	
    	return wa;
    }
    
    public void deleteAddressesWithWaste(long addressId){
    }
}
