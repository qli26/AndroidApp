package com.qli26.foodorderapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CheckOutFragment extends Fragment {
	OnHeadlineSelectedListener mCallback;     
    
    // Container Activity must implement this interface     
    public interface OnHeadlineSelectedListener {     
        public void onCheckOut(boolean b);     
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
		View view = inflater.inflate(R.layout.checkout, container, false);
		
		return view;
	}
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Button checkOut = (Button) getActivity().findViewById(R.id.Check_Out);
		Button reset = (Button) getActivity().findViewById(R.id.Reset);
		
		reset.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				FragmentManager fm = getFragmentManager();
			    CheckOutFragment checkoutFragment = 
			      (CheckOutFragment)fm.findFragmentById(R.id.frag_checkout);
			    fm.beginTransaction().hide(checkoutFragment).commit();
			}
		});
		
		checkOut.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				mCallback.onCheckOut(true);
				
				FragmentManager fm = getFragmentManager();
			    CheckOutFragment checkoutFragment = 
			      (CheckOutFragment)fm.findFragmentById(R.id.frag_checkout);
			    fm.beginTransaction().hide(checkoutFragment).commit();
			}
			
		});
		
		reset.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				mCallback.onCheckOut(false);
				
				FragmentManager fm = getFragmentManager();
			    CheckOutFragment checkoutFragment = 
			      (CheckOutFragment)fm.findFragmentById(R.id.frag_checkout);
			    fm.beginTransaction().hide(checkoutFragment).commit();
			}
			
		});
	}
	
	
}
