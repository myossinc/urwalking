package com.example.simplecamera;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBAdapter {
	protected static final String TAG = "DataAdapter";
	protected static final String TABLE = "Images";

	private static final int INDEX_NAME = 0;
	private static final int INDEX_LONG = 1;
	private static final int INDEX_LAT = 2;
	private static final int INDEX_PATH = 3;

	private final Context mContext;
	private SQLiteDatabase mDb;
	private DatabaseHandler mDbHelper;

	private ArrayList<DBItem> list;

	public DBAdapter(Context context) {
		this.mContext = context;
		list = new ArrayList<DBItem>();
		mDbHelper = new DatabaseHandler(mContext);
	}

	public DBAdapter createDatabase() throws SQLException {
		try {
			mDbHelper.createDataBase();
		} catch (IOException mIOException) {
			Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
			throw new Error("UnableToCreateDatabase");
		}
		return this;
	}

	public DBAdapter open() throws SQLException {
		try {
			mDbHelper.openDataBase();
			mDbHelper.close();
			mDb = mDbHelper.getReadableDatabase();
		} catch (SQLException mSQLException) {
			Log.e(TAG, "open >>" + mSQLException.toString());
			throw mSQLException;
		}
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	public ArrayList<DBItem> getTestData() {
		try {
			String sql = "SELECT * FROM " + TABLE;
			int i = 1;
			Cursor cursor = mDb.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				
				while (cursor.isAfterLast() == false) {
					System.out.println("int: " + i);
					i++;
					String name = cursor.getString(INDEX_NAME);
					Double lon = Double.parseDouble(cursor.getString(INDEX_LONG));
					Double lat = Double.parseDouble(cursor.getString(INDEX_LAT));
					String path = cursor.getString(INDEX_PATH);

					DBItem item = new DBItem(name, lon, lat, path);
					list.add(item);
					cursor.moveToNext();
				}
			}
			return list;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
}