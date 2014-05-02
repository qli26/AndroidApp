package com.qli26.foodorderapp;

import com.qli26.foodorderapp.CheckOutFragment.OnHeadlineSelectedListener;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SizeFragment extends Fragment {
	private int[] price;
	private int price_chosen;
	OnHeadlineSelectedListener mCallback;     
    
    // Container Activity must implement this interface     
    public interface OnHeadlineSelectedListener {     
        public void choosenPrice(int price_chosen);     
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
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.size_choose, container, false);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		TextView tvSmal = (TextView) this.getActivity().findViewById(
				R.id.small_price);
		tvSmal.setText("$" + price[0]);

		TextView tvMedium = (TextView) this.getActivity().findViewById(
				R.id.medium_price);
		tvMedium.setText("$" + price[1]);

		TextView tvLarge = (TextView) this.getActivity().findViewById(
				R.id.large_price);
		tvLarge.setText("$" + price[2]);

		Button small = (Button) this.getView().findViewById(R.id.Small);
		small.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				price_chosen = price[0];
				Toast toast = Toast.makeText(getActivity().getApplicationContext(),
						"Selected small one: $" + price_chosen,
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				mCallback.choosenPrice(price_chosen);
			}

		});

		Button medium = (Button) this.getView().findViewById(R.id.Medium);
		medium.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				price_chosen = price[1];
				Toast toast = Toast.makeText(getActivity().getApplicationContext(),
						"Selected medium one:$" + price_chosen,
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				mCallback.choosenPrice(price_chosen);
			}

		});

		Button large = (Button) this.getView().findViewById(R.id.Large);
		large.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				price_chosen = price[2];
				Toast toast = Toast.makeText(getActivity().getApplicationContext(),
						"Selected large one:$" + price_chosen,
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				mCallback.choosenPrice(price_chosen);
			}

		});

	}

	public int[] getPrice() {
		return price;
	}

	public void setPrice(int[] price) {
		this.price = price;
	}
}
