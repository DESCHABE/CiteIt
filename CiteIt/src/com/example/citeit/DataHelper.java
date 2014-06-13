package com.example.citeit;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataHelper extends SQLiteOpenHelper {
	
	private static final String TABLE_CITATIONS = "citations";
	private static final String DATABASE_NAME = "myCitesDB";
	
	private SQLiteDatabase database;

	public DataHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
		Log.d("debug", "Datenbankkonstruktor");
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE "+ TABLE_CITATIONS +" (id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "citation TEXT NOT NULL, "
				+ "author TEXT NOT NULL, "
				+ "image TEXT NOT NULL"
				+ ")");
		Log.d("debug", "Tabelle wurde erstellt");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITATIONS);
        // Create tables again
        onCreate(db);
	}
	
	/**
	 * Looks for items at the database.
	 * 
	 * @param findText
	 * @param resource
	 * @return
	 */
	public ArrayList<ZitatTopicWrapper> findAll() {

		database = getReadableDatabase();
		Cursor cursor = database.rawQuery(
				"SELECT * FROM citations;", null);
		ArrayList<ZitatTopicWrapper> foundItems = new ArrayList<ZitatTopicWrapper>();
		cursor.moveToFirst();
		for (int i = 0; i < cursor.getCount(); i++) {
			System.out.println("find item" + i);
			foundItems.add(new ZitatTopicWrapper(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
			cursor.moveToNext();
		}
		return foundItems;

	}
	
	/**
	 * Saves items into the database.
	 * 
	 * @param item
	 * @return
	 */
	public boolean save(ZitatTopicWrapper item) {

		database = getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("citation", item.get_zitatText());
		contentValues.put("author", item.get_zitatAutor());
		contentValues.put("image", item.get_imageUrl());
		try {
			database.insertOrThrow(TABLE_CITATIONS, null, contentValues);
			database.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}
	
	/**
	 * Deletes items at the database.
	 * 
	 * @param item
	 * @return
	 */
	public boolean delete(ZitatTopicWrapper item) {
		
		database = getWritableDatabase();
		String[] whereArgs = { "" + item.get_mid() };
		int deleted = database.delete(TABLE_CITATIONS,
				"citations.id = ?", whereArgs);
		if (deleted >= 1) {
			return true;
		} else {
			return false;
		}
		
	}
}
