package com.qli26.database;

import java.io.File;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;
import android.util.Log;

public class DBManager {
	private DBHelper helper;
	private SQLiteDatabase dbWrite;

	public DBManager(Context context) {
		helper = new DBHelper(context);
		dbWrite = helper.getWritableDatabase();
	}

	public ArrayList<Order> readAll(){
		ArrayList<Order> orders = new ArrayList<Order>();
		Cursor c = dbWrite.rawQuery("SELECT * FROM orderMenu", null);
		
		//c.moveToFirst();
		while(c.moveToNext()){
			Order order = new Order(c.getInt(c.getColumnIndex("_id")), 
					c.getString(c.getColumnIndex("menu_id")), 
					c.getString(c.getColumnIndex("menu_item")), 
					c.getInt(c.getColumnIndex("size")), 
					c.getInt(c.getColumnIndex("price")),
					c.getString(c.getColumnIndex("time")));
			orders.add(order);
		}
		
		c.close();
		return orders;
	}
	
	public void add(Order order) {
		/*dbWrite.beginTransaction();
		try {
			dbWrite.execSQL(
					"INSERT INTO orderMenu(_id, menu_id, menu_item, size, price, time) VALUES(null,?,?,?,?,?)",
					new Object[] { order.getMenu_id(), order.getMenu_item(), order.getSize(),order.getPrice(), order.getTime() });
			//dbWrite.setTransactionSuccessful();
		} finally {
			dbWrite.endTransaction();
		}*/
		ContentValues cv = new ContentValues();
		cv.put("menu_id", order.getMenu_id());
		cv.put("menu_item", order.getMenu_item());
		cv.put("size", order.getSize());
		cv.put("price", order.getPrice());
		cv.put("time", order.getTime());
		long l = dbWrite.insert("orderMenu", null, cv);
		String msg = Long.toString(l);
		Log.v("Insert:", msg);
	}
	
	public void deleteAll(){
		long l = dbWrite.delete("orderMenu", null, null);
		String msg = Long.toString(l);
		Log.v("delete:", msg);
	}
	
	public void closeDB(){
		dbWrite.close();
	}
}
