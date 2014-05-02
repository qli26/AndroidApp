package com.qli26.foodorderapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qli26.database.DBManager;
import com.qli26.database.Order;
import com.qli26.foodorderapp.CheckOutFragment.OnHeadlineSelectedListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class OrderFragment extends Fragment {

	OnHeadlineSelectedListener mCallback;

	// Container Activity must implement this interface
	public interface OnHeadlineSelectedListener {
		public void onDeleteDB(boolean b);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			mCallback = (OnHeadlineSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}

	private ListView list;

	private String[] values = new String[] { "cheese_pizza", "chicken_pizza",
			"pepperoni_pizza", "veggies_pizza" };

	private int[] images = new int[] { R.drawable.cheese_pizza,
			R.drawable.chicken_pizza, R.drawable.pepperoni_pizza,
			R.drawable.veggies_pizza };

	private String[] descriptions = new String[] {
			"This is cheese_pizza, the most common one",
			"This is chicken_pizza, the best flavor",
			"This is pepperoni_pizza, the most luxury one",
			"This is veggies_pizza, good for vegitarian" };

	private int[] size = new int[] { 5, 10, 15 };

	private String[] price = new String[] { "", "", "", "" };
	private int[] price_int = new int[] { 0, 0, 0, 0 };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_fragment, container, false);
		list = (ListView) view.findViewById(R.id.ListView01);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setAdapter();

		OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position,
					long id) {
				// Do something in response to the click
				Toast toast = Toast.makeText(getActivity()
						.getApplicationContext(), values[position]
						+ " is selected.", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();

				Intent intent = new Intent();
				intent.setClass(getActivity(), ItemOnList.class);

				Bundle bundle = new Bundle();
				bundle.putString("name", values[position]);
				bundle.putString("description", descriptions[position]);
				/*
				 * bundle.putInt("small", size[0]); bundle.putInt("medium",
				 * size[1]); bundle.putInt("large", size[2]);
				 */
				bundle.putIntArray("price", size);
				intent.putExtras(bundle); // it.putExtra(¡°test¡±, "shuju¡±);
				startActivityForResult(intent, position, bundle); // startActivityForResult(it,REQUEST_CODE);
			}
		};

		list.setOnItemClickListener(mMessageClickedHandler);

		Button visitWeb = (Button) this.getActivity()
				.findViewById(R.id.button1);
		visitWeb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri uri = Uri.parse("https://order.pizzahut.com/home");
				Intent visitWeb = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(visitWeb);
			}

		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == getActivity().RESULT_CANCELED) {
			Toast toast = Toast.makeText(getActivity().getApplicationContext(),
					"Order canceled", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		} else if (resultCode == getActivity().RESULT_OK) {
			String temp = null;
			Bundle bundle = data.getExtras();
			if (bundle != null) {
				Toast toast = Toast.makeText(getActivity()
						.getApplicationContext(),
						/*
						 * requestCode + "\n" +
						 */"Selected " + bundle.getString("name") + "\n$:"
								+ bundle.getInt("price"), Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();

				Integer p = bundle.getInt("price");
				price[requestCode] = "$" + p.toString();
				price_int[requestCode] = p.intValue();
				setAdapter();

				FragmentManager fm = getFragmentManager();
				CheckOutFragment checkoutFragment = (CheckOutFragment) fm
						.findFragmentById(R.id.frag_checkout);
				fm.beginTransaction().show(checkoutFragment).commit();
			}

		}
	}

	public void setAdapter() {
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < values.length; i++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("values", values[i]);
			listItem.put("images", images[i]);
			listItem.put("price", price[i]);
			listItems.add(listItem);
		}
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), listItems,
				R.layout.element, new String[] { "values", "images", "price" },
				new int[] { R.id.ItemTitle, R.id.ItemImage, R.id.ItemPrice });
		list.setAdapter(adapter);
	}

	public void getCheckOutOrReset(Bundle info) {
		// TODO Auto-generated method stub
		boolean checkOut = info.getBoolean("checkOutOrReset");
		if (checkOut) {
			int price_toast = 0;
			ArrayList<Order> orders = (ArrayList<Order>) info
					.getSerializable("orders");

			StringBuilder order_info = new StringBuilder();

			order_info.append("Current order:\n\t");
			for (int i = 0; i < price_int.length; i++) {
				price_toast = price_int[i] + price_toast;

				if (price_int[i] != 0) {
					order_info.append(this.values[i] + " " + this.price[i]
							+ "\n\t");
				}
			}
			String orderId = new String("");
			for (int i = 0; i < orders.size(); i++) {
				if (!orderId.equals(orders.get(i).getMenu_id())) {
					order_info.append("\n");
					orderId = orders.get(i).getMenu_id();
					order_info.append(orders.get(i).getMenu_id() + ":\n\t");
				}

				price_toast = orders.get(i).getPrice() + price_toast;

				order_info.append(orders.get(i).getMenu_item() + " $"
						+ orders.get(i).getPrice() + "\n\t");
			}
			/*
			 * Toast toast = Toast.makeText(
			 * getActivity().getApplicationContext(),
			 * "All saved/current orders have been sent!\n" +
			 * order_info.toString() + "Total cost: $" + price_toast,
			 * Toast.LENGTH_SHORT); toast.setGravity(Gravity.CENTER, 0, 0);
			 * toast.show();
			 */

			showDialog("All saved/current orders have been sent!\n"
					+ order_info.toString() + "\nTotal cost: $" + price_toast);
		} else {
			Toast toast = Toast.makeText(getActivity().getApplicationContext(),
					"Cancel all saved/current orders\nStart a new order!",
					Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			resetOrder();
			this.mCallback.onDeleteDB(true);
		}
	}

	private void showDialog(String msg) {
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setTitle("Order confirmation!");
		builder.setMessage(msg);
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				resetOrder();
				Toast toast = Toast.makeText(getActivity()
						.getApplicationContext(),
						"All saved/current orders have been sent!",
						Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				
				mCallback.onDeleteDB(true);
			}
		});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						FragmentManager fm = getFragmentManager();
						CheckOutFragment checkoutFragment = (CheckOutFragment) fm
								.findFragmentById(R.id.frag_checkout);
						fm.beginTransaction().show(checkoutFragment).commit();
						mCallback.onDeleteDB(false);
					}
				});
		builder.show();
	}

	public void resetOrder() {
		for (int i = 0; i < price.length; i++) {
			price[i] = "";
			price_int[i] = 0;
		}
		setAdapter();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		/*
		 * setAdapter();
		 * 
		 * FragmentManager fm = getFragmentManager(); CheckOutFragment
		 * checkoutFragment = (CheckOutFragment) fm
		 * .findFragmentById(R.id.frag_checkout);
		 * fm.beginTransaction().show(checkoutFragment).commit();
		 */
	}

	public String[] getPrice() {
		return price;
	}

	public void setPrice(String[] price) {
		this.price = price;
	}

	public int[] getPrice_int() {
		return price_int;
	}

	public void setPrice_int(int[] price_int) {
		this.price_int = price_int;
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}
}
