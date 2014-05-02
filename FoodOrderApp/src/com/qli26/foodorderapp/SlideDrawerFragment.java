package com.qli26.foodorderapp;

import com.qli26.rec.Contacter;
import com.qli26.rec.InsertContact;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.Contacts.People;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class SlideDrawerFragment extends Fragment {

	private Context mContext;
	private SlidingDrawer mSlidingDrawer;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.slidedrawerfragment, container,
				false);
		mContext = this.getActivity();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Button ok = (Button) this.getView().findViewById(R.id.btn_confirm);
		Button cancel = (Button) this.getView().findViewById(R.id.btn_cancel);

		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText etName = (EditText) getView().findViewById(
						R.id.et_name);
				EditText etEmail = (EditText) getView().findViewById(
						R.id.et_email);
				EditText etPhone = (EditText) getView().findViewById(
						R.id.et_pnumber);

				if ("".equals(etName.getText().toString().trim())) {
					Toast toast = Toast.makeText(mContext,
							"At least input name please!", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				} else {
					Contacter con = new Contacter();
					con.setName(etName.getText().toString());
					con.setEmail(etEmail.getText().toString());
					con.setPnumber(etPhone.getText().toString());
					InsertContact ic = new InsertContact(mContext);
					ic.insertOneContact(con);
					Toast toast = Toast.makeText(mContext,
							"Add contact success", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					showDialog();

					etName.setText("");
					etEmail.setText("");
					etPhone.setText("");
				}
			}
		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast toast = Toast.makeText(mContext, "Abort adding contact",
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();

				mSlidingDrawer.animateClose();
			}

		});
	}

	private void showDialog() {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("Notification!");
		builder.setMessage("Add success, would you want to check?");
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				 Intent intent = new Intent();
				 intent.setAction(Intent.ACTION_VIEW);
				 intent.setData(Contacts.People.CONTENT_URI);
				 startActivity(intent);
			}
		});
		builder.setNegativeButton("No", null);
		builder.show();
	}

	public SlidingDrawer getmSlidingDrawer() {
		return mSlidingDrawer;
	}

	public void setmSlidingDrawer(SlidingDrawer mSlidingDrawer) {
		this.mSlidingDrawer = mSlidingDrawer;
	}

}
