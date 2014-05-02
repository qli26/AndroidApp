package com.qli26.rec;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

public class InsertContact {
	public Context mContext;

	public InsertContact(Context context) {
		this.mContext = context;
	}

	public void insertOneContact(Contacter contacter) {
		ContentResolver resolver = mContext.getContentResolver();

		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		ContentValues values = new ContentValues();
		// add an id which is auto created with all property is null
		long id = ContentUris.parseId(resolver.insert(uri, values));

		// add name of contact
		uri = Uri.parse("content://com.android.contacts/data");
		values.put("raw_contact_id", id);
		values.put("data2", contacter.getName());
		values.put("mimetype", "vnd.android.cursor.item/name");
		resolver.insert(uri, values);

		// add number
		values.clear(); // clear the data of last time
		values.put("raw_contact_id", id);
		values.put("data1", contacter.getPnumber());
		values.put("data2", "2");
		values.put("mimetype", "vnd.android.cursor.item/phone_v2");
		resolver.insert(uri, values);

		// add email
		values.clear();
		values.put("raw_contact_id", id);
		values.put("data1", contacter.getEmail());
		values.put("data2", "1");
		values.put("mimetype", "vnd.android.cursor.item/email_v2");

		resolver.insert(uri, values);
	}
}
