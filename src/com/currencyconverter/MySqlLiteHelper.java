package com.currencyconverter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqlLiteHelper extends SQLiteOpenHelper {

	private final String SQL_QUERY = 
<<<<<<< HEAD
			"create table currency(name text primary key, country text, dollarvalue real, favorite int, base int)";
=======
			"create table currency(name text primary key, country text, dollarvalue real)";
>>>>>>> 79f43014fecffda9e2e0af81707e7419047372b6
	
	public MySqlLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_QUERY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXIST currency");
		onCreate(db);
	}

	
}
