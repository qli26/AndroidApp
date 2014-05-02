package com.qli26.foodorderapp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.qli26.database.DBManager;
import com.qli26.database.Order;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.SlidingDrawer.OnDrawerScrollListener;
import android.widget.Toast;

public class MainActivity extends Activity implements
		CheckOutFragment.OnHeadlineSelectedListener 
		, OrderFragment.OnHeadlineSelectedListener{
	private OrderFragment orderFragment;
	private CheckOutFragment checkoutfragment;
	private SlideDrawerFragment slidedrawerfragment;
	private Context mContext = null;
	private DBManager dbManager;
	private int order_count;
	private SlidingDrawer mSlidingDrawer;
	private FragmentManager fm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mContext = this;
		dbManager = new DBManager(mContext);
		ArrayList<Order> orders = dbManager.readAll();
		order_count = orders.size();

		// Get references to the Fragments
		fm = getFragmentManager();
		orderFragment = (OrderFragment) fm.findFragmentById(R.id.frag_list);

		checkoutfragment = (CheckOutFragment) fm
				.findFragmentById(R.id.frag_checkout);
		if (order_count == 0) {
			fm.beginTransaction().hide(checkoutfragment).commit();
		} else {
			Toast toast = Toast.makeText(mContext,
					"Previous orders exist, please continue to order",
					Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}

		Button preferenceMenu = (Button) findViewById(R.id.Preference);
		preferenceMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, PreferenceMenu.class);
				Bundle ordersInfo = new Bundle();
				ArrayList<Order> orders = dbManager.readAll();
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < orders.size(); i++) {
					sb.append(orders.get(i).getId() + " "
							+ orders.get(i).getMenu_id() + " "
							+ orders.get(i).getMenu_item() + " "
							+ orders.get(i).getPrice() + " "
							+ orders.get(i).getTime() + "\n");
				}

				ordersInfo.putString("Orders", sb.toString());
				intent.putExtras(ordersInfo);
				startActivity(intent);
			}

		});

		Button saveOrder = (Button) findViewById(R.id.Save);
		saveOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				boolean noOrder = true;
				String[] values = orderFragment.getValues();
				String[] prices = orderFragment.getPrice();
				int[] prices_int = orderFragment.getPrice_int();

				for (int i = 0; i < values.length; i++) {
					if (prices_int[i] != 0) {
						noOrder = false;
						break;
					}
				}
				if (!noOrder) {
					Toast toast = Toast
							.makeText(
									mContext,
									"One order has been saved into database\nStart another order",
									Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				} else {
					Toast toast = Toast.makeText(mContext,
							"No order yet\nPlease select some itmes",
							Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
				order_count++;

				SimpleDateFormat sDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd   hh:mm:ss");
				String time = sDateFormat.format(new java.util.Date());

				for (int i = 0; i < values.length; i++) {
					if (prices_int[i] != 0) {
						Order order = new Order("order_" + order_count,
								values[i], prices_int[i], time);
						dbManager.add(order);
					}
				}
				orderFragment.resetOrder();
			}

		});

		init();
	}

	@SuppressWarnings("deprecation")
	private void init() {
		mSlidingDrawer = (SlidingDrawer) findViewById(R.id.slidingdrawer);
		mSlidingDrawer.setOnDrawerScrollListener(new OnDrawerScrollListener() {
			public void onScrollStarted() {
				System.out.println("-------->  start slide");
			}

			public void onScrollEnded() {
				System.out.println("-------->  stop slide");
			}
		});
		mSlidingDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			public void onDrawerOpened() {
				slidedrawerfragment = (SlideDrawerFragment) fm
						.findFragmentById(R.id.frag_slidedrawer);

				slidedrawerfragment.setmSlidingDrawer(mSlidingDrawer);

				View view1 = findViewById(R.id.slidingdrawer);
				view1.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// Just for consume the event
					}
				});
				System.out.println("-------->  open drawer");
			}
		});

		mSlidingDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {
			public void onDrawerClosed() {
				System.out.println("-------->  close drawer");
				View view1 = findViewById(R.id.slidingdrawer);
				view1.setClickable(false);
				EditText etName = (EditText) findViewById(R.id.et_name);
				EditText etEmail = (EditText) findViewById(R.id.et_email);
				EditText etPhone = (EditText) findViewById(R.id.et_pnumber);
				etName.setText("");
				etEmail.setText("");
				etPhone.setText("");
				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

				imm.hideSoftInputFromWindow(etName.getWindowToken(), 0);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onCheckOut(boolean b) {
		// TODO Auto-generated method stub
		Bundle info = new Bundle();

		// dbManager.deleteAll();
		ArrayList<Order> orders = dbManager.readAll();

		info.putBoolean("checkOutOrReset", b);
		info.putSerializable("orders", (Serializable) orders);
		this.orderFragment.getCheckOutOrReset(info);
		//dbManager.deleteAll();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		orderFragment.setPrice((String[]) savedInstanceState
				.getSerializable("priceString"));
		orderFragment.setPrice_int((int[]) savedInstanceState
				.getSerializable("priceInt"));
		orderFragment.setAdapter();
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		outState.putSerializable("priceString", orderFragment.getPrice());
		outState.putSerializable("priceInt", orderFragment.getPrice_int());
		super.onSaveInstanceState(outState);

	}

	private boolean isExit;
	private Timer tExit;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& mSlidingDrawer.isOpened()) {
			mSlidingDrawer.animateClose();
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isExit == false) {
				isExit = true;
				if (tExit != null) {
					tExit.cancel();
				}

				tExit = new Timer();
				TimerTask task = new TimerTask() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						isExit = false;
					}

				};

				Toast toast = Toast.makeText(mContext,
						"Press again to close the app in 2 seconds",
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				tExit.schedule(task, 2000);
			} else {
				return super.onKeyDown(keyCode, event);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onDeleteDB(boolean b) {
		// TODO Auto-generated method stub
		if(b){
			dbManager.deleteAll();
		}
	}

}
