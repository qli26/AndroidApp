package com.qli26.database;

import java.sql.Date;

import android.text.format.Time;

public class Order {
	int id;
	String menu_id;
	String menu_item;
	int size;
	int price;
	String time;

	public Order() {

	}

	public Order(String menu_id, String menu_item, int price,
			String time) {
		this.menu_id = menu_id;
		this.menu_item = menu_item;
		this.price = price;
		this.time = time;
	}
	
	public Order(int id, String menu_id, String menu_item, int size, int price,
			String time) {
		this.id = id;
		this.menu_id = menu_id;
		this.menu_item = menu_item;
		this.size = size;
		this.price = price;
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMenu_id() {
		return menu_id;
	}

	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}

	public String getMenu_item() {
		return menu_item;
	}

	public void setMenu_item(String menu_item) {
		this.menu_item = menu_item;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	
}
